import "../css/search.css";
import React, { useState } from "react";

export function Search({ contract, onUserSelect }) {
  
  const [searchTerm, setSearchTerm] = useState("");        // 사용자가 입력한 검색어를 저장하는 상태
  const [searchResults, setSearchResults] = useState([]);  // 검색 결과를 저장하는 상태
  const [isSearched, setIsSearched] = useState(false);     // 검색 버튼을 클릭했는지 여부를 저장하는 상태

  // 검색 버튼 클릭 시 호출되는 함수
  const handleSearch = async () => {
    setIsSearched(true);  // 검색 버튼 클릭 표시
    const normalizedSearchTerm = searchTerm.trim().toLowerCase();

    // 입력 값이 없거나 공백인 경우 처리
    if (!normalizedSearchTerm) {
      setSearchResults([]);
      return;
    }

    try {
      // 스마트 컨트랙트에서 모든 검색 결과를 가져오기
      const searchResult = await contract.methods.linearSearch(normalizedSearchTerm).call();
      const searchUsers = searchResult[0]; // 검색된 닉네임 배열
      const searchUserAddresses = searchResult[1]; // 검색된 사용자 주소 배열
  
      if (!searchUsers || searchUsers.length === 0) {
        setSearchResults([]);
        return;
      }

      // 검색 결과 처리
      const results = [];
      for (let i = 0; i < searchUsers.length; i++) {
        const address = searchUserAddresses[i];
        const blocks = await contract.methods.getIndexOf(address).call();

        // 최신 블록의 닉네임 가져오기
        let latestBlockId = blocks[blocks.length - 1];  // 가장 최근 블록의 ID를 가져옴
        const latestNickName = await contract.methods.getInfo(address, latestBlockId).call();

        results.push({
          nickName: latestNickName[0], // 최신 닉네임
          userAddress: address,
          gameName: normalizedSearchTerm, // 검색된 게임 이름
        });
      }

      setSearchResults(results); // 검색 결과 저장
    } catch (error) {
      console.error("검색 중 오류 발생:", error.message, error.stack);
      setSearchResults([]); // 오류 발생 시 결과를 빈 배열로 처리
    }
  };

  // 엔터키 입력 처리
  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleSearch();
    }
  };

  return (
    <div>
      <div className="main-container-search">
        <div className="input-button-container">
          <input
            type="text"
            placeholder="검색어를 입력하세요"
            className="search-bar"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}  // 입력 값 변경 시 상태 업데이트
            onKeyDown={handleKeyDown}  // 엔터키 감지
          />
          <button className="btn-search" onClick={handleSearch}></button>
        </div>

        {/* 검색 결과 출력 */}
        {isSearched && (
           <>
           {searchResults.length > 0 ? (
             <table className="search-result">
               {searchResults.map((result) => (
                 <tr key={result.userAddress}>
                   <td>
                     닉네임:{" "}
                       <span className="search-user" onClick={() => {
                         console.log("선택된 사용자:", result);  // 로그 추가
                         onUserSelect(result);
                       }} >
                     {result.nickName}</span>
                     </td> <td >사용자 주소: {result.userAddress.slice(-4)}</td>
                 </tr>
               ))}
             </table>
           ) : (
             <p>'{searchTerm}' 키워드 검색 결과가 없습니다.</p>
           )}
         </>
        )}
      </div>
    </div>
  );
}

export default Search;
