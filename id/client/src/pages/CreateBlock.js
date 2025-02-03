import React, { useState } from "react";
import "../css/createblock.css";
import Web3 from "web3"; 

const CreateBlock = ({ contract, account, getInfo, blocks, setBlocks }) => {
  const [nickName, setNickName] = useState("");
  const [gameName, setGameName] = useState("");
  const [serverName, setServerName] = useState("");
  const [className, setClassName] = useState("");
  const [remarks, setRemarks] = useState("");

  // 텍스트 영역 바이트 수를 체크하는 함수
  const CheckByte = (event) => {
    const maxByte = 100; // 최대 100바이트
    const text_val = event.target.value; // 입력한 문자
    const text_len = text_val.length; // 입력한 문자 수

    let totalByte = 0;
    for (let i = 0; i < text_len; i++) {
      const each_char = text_val.charAt(i);
      const uni_char = escape(each_char); // 유니코드 형식으로 변환
      if (uni_char.length > 4) {
        // 한글 : 2Byte
        totalByte += 2;
      } else {
        // 영문, 숫자, 특수문자 : 1Byte
        totalByte += 1;
      }
      if (totalByte > maxByte) {
        alert("최대 100Byte까지만 입력 가능합니다.");
        event.target.value = text_val.substring(0, i); // 초과된 부분 자르기
        setRemarks(event.target.value.substring(0, i));
        return;
      }
    }
  };

  const insertInfo = async () => {
    console.log(nickName, gameName, serverName, className, remarks);
    try {
      // 닉네임 저장
      localStorage.setItem("nickName", nickName);

      // 스마트 계약 호출
      await contract.methods
        .insertInfo(nickName, gameName, serverName, className, remarks)
        .send({ from: account, gas: 3000000 });

      // 새로 추가된 데이터를 blocks에 반영
      const newBlock = {
        id: blocks.length + 1, // 고유 ID
        nickName,
        gameName,
        serverName,
        className,
        remarks,
        date: new Date().toLocaleString(), // 여기에 타임스탬프를 추가
      };

      // blocks 상태 업데이트
      setBlocks([...blocks, newBlock]);

      // 입력 필드 초기화
      setNickName("");
      setGameName("");
      setServerName("");
      setClassName("");
      setRemarks("");

      alert("정보가 저장되었습니다!");

      // **페이지 새로고침 추가**
      window.location.reload();
    } catch (error) {
      console.error("Error inserting info:", error);
    }
  };

  return (
    <div className="block-create">
      <div className="contents-box">
        <table className="main-contents">
          <tbody>
            <tr>
              <td className="con-title">
                <span className="input-group-text">닉네임</span>
              </td>
              <td className="con-text">
              <input
                  type="text"
                  className="form-control"
                  value={nickName}
                  onChange={(e) => setNickName(e.target.value)}
                />
              </td>
              <td rowSpan="4">
                <span className="input-group-text">Note</span>
                <textarea
                  className="comment-box"
                  rows="7"
                  cols="70"
                  value={remarks}
                  onChange={(e) => setRemarks(e.target.value)}
                  onKeyUp={CheckByte}
                  placeholder="100byte 제한"
                />
              </td>
              <td rowSpan="4">
                <div className="d-grid gap-2">
                  <button
                    className="btn btn-enter"
                    type="button"
                    onClick={insertInfo}
                  ></button>
                </div>
              </td>
            </tr>

            <tr>
              <td className="con-title">
                <span className="input-group-text">게임명</span>
              </td>
              <td className="con-text">
                <input
                  type="text"
                  className="form-control"
                  value={gameName}
                  onChange={(e) => setGameName(e.target.value)}
                  placeholder="ex)로스트아크"
                />
              </td>
            </tr>
            <tr>
              <td className="con-title">
                <span className="input-group-text">서버명</span>
              </td>
              <td className="con-text">
                <input
                  type="text"
                  className="form-control"
                  value={serverName}
                  onChange={(e) => setServerName(e.target.value)}
                  placeholder="ex)루페온"
                />
              </td>
            </tr>
            <tr>
              <td className="con-title">
                <span className="input-group-text">클래스</span>
              </td>
              <td className="con-text">
                <input
                  type="text"
                  className="form-control"
                  value={className}
                  onChange={(e) => setClassName(e.target.value)}
                  placeholder="ex)슬레이어"
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default CreateBlock;
