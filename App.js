import React, { useState, useEffect } from "react";
import Web3 from "web3";
import Header from "./pages/Header";
import Footer from "./pages/Footer";
import { History } from "./pages/History";
import { ChatBTN, HelpBTN } from "./pages/Buttons";
import "./App.css";
import { Search } from "./pages/Search";
import IdProject from "./IdProject.json";
import CreateBlock from "./pages/CreateBlock";

const App = () => {
  const [account, setAccount] = useState("");
  const [contract, setContract] = useState(null);
  const [blocks, setBlocks] = useState([]); // 블록 상태 관리
  const [nickName, setNickName] = useState("");
  const [selectedUser, setSelectedUser] = useState(null); // 선택된 사용자

  useEffect(() => {
    const initWeb3 = async () => {
      if (window.ethereum) {
        const web3 = new Web3(window.ethereum);
        try {
          await window.ethereum.request({ method: "eth_requestAccounts" });
          const accounts = await web3.eth.getAccounts();
          if (accounts.length > 0) {
            const account = accounts[0];
            setAccount(account);
            const networkId = await web3.eth.net.getId();
            const deployedNetwork = IdProject.networks[networkId];

            const instance = new web3.eth.Contract(
              IdProject.abi,
              deployedNetwork.address
            );
            setContract(instance);

            // 계약에서 직접 데이터 불러오기
            fetchBlocks(instance, account);
          } else {
            console.error("No accounts found.");
          }
        } catch (error) {
          console.error("Error initializing web3:", error);
        }
      } else {
        console.error("Ethereum object not found. Please install MetaMask.");
      }
    };
    initWeb3();

    // 새로고침 시 로컬 스토리지에서 blocks 데이터 불러오기
    const savedBlocks = localStorage.getItem("blocks");
    if (savedBlocks) {
      setBlocks(JSON.parse(savedBlocks));
    }
  }, []);

  // 블록 정보 가져오기 (스마트 계약에서 실시간으로 블록 데이터 불러오기)
  const fetchBlocks = async (contract, account) => {
    try {
      if (contract && account) {
        // blockCount는 Bigint로 반환될 수 있으므로, 이를 Bigint로 처리
        const blockCount = await contract.methods.blockCount().call();
        console.log("blockCount: ", blockCount);

        if (blockCount === 0n) {
          console.log("No blocks available");
          return;
        }

        const fetchedBlocks = [];

        // i는 Bigint로 처리 (1n은 Bigint 값)
        for (let i = 1n; i <= blockCount; i++) {
          const block = await contract.methods.getInfo(account, i).call();
          console.log(`Block ${i} : `, block);

          fetchedBlocks.push({
            blockId : i.toString(), //Bigint를 string으로 변환하여 저장
            nickName: block[0],
            gameName: block[1],
            serverName: block[2],
            className: block[3],
            remarks: block[4],
            date: new Date(Number(block[5]) * 1000).toLocaleString(), // 타임 스탬프 변환
          });
        }

        setBlocks(fetchedBlocks); // 블록 상태 업데이트
      }
    } catch(error) {
      console.error("블록 정보 불러오기 중 오류 발생: ", error);
    }
  };

  // 선택된 사용자 상태 업데이트
  const handleUserSelect = (user) => {
    setSelectedUser(user);
  };

  // 사용자 선택 취소 (검색 화면으로 돌아가기)
  const handleBackToSearch = () =>{
    setSelectedUser(null);
  };



  // blocks 상태 업데이트 시 로컬 스토리지에 저장
  useEffect(() => {
    if (blocks.length > 0) {
      localStorage.setItem("blocks", JSON.stringify(blocks));
    }
  }, [blocks]);

  useEffect(() => {
    // 페이지가 처음 로드될 때 localStorage에서 nickName을 가져옵니다.
    const storedNickName = localStorage.getItem("nickName");

    if (storedNickName) {
      setNickName(storedNickName);
    } else {
      // localStorage에 nickName이 없으면 기본값을 사용하거나 사용자에게 설정하게 할 수 있습니다.
      setNickName(""); // 기본 닉네임 설정
      localStorage.setItem("nickName", ""); // localStorage에 저장
    }
  }, []);

  // 정보 가져오기 (블록 리스트 가져오기)
  const getInfo = async () => {
    try {
      if (contract && account) {
        const blockCount = await contract.methods.blockCount().call();
        const gotInfos = [];
        for (let i = 1; i <= blockCount; i++) {
          const block = await contract.methods.getInfo(account, i).call();
          gotInfos.push(block);
        }
        setBlocks(gotInfos);
      }
    } catch (error) {
      console.error("Error getting info:", error);
    }
  };

  return (
    <div className="main-container">
      <Header />
      <h1 style={{ textAlign: 'center', margin: 0, padding: 0, marginBottom: '15px', marginTop:'15px' }}> Welcome, {nickName}!  </h1> 
      {/* 다른 컴포넌트에서 nickName을 사용 */}
      {!selectedUser ? (
              // 검색 화면
              <Search contract={contract} onUserSelect={handleUserSelect} />
            ) : (
              // 선택된 사용자 히스토리 화면
              <div className="user-history">
                <button onClick={handleBackToSearch} className="back-button">
                  돌아가기
                </button>
                <h2>{selectedUser.nickName}님의 기록</h2>
                <History user={selectedUser} blocks={blocks} />
              </div>
            )}
            {!selectedUser && (
              <>
      <CreateBlock
        contract={contract}
        account={account}
        blocks={blocks}
        setBlocks={setBlocks}
        getInfo={getInfo} // getInfo 함수 전달
        fetchBlocks={fetchBlocks}
      />
      <History
        blocks={blocks} // blocks 상태 전달
      />
      </>
            )}
      <Footer />
      <div className="button-container">
        <ChatBTN />
        <HelpBTN />
      </div>
    </div>
  );
};

export default App;
