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
import Stats from "./pages/Stats";

const App = () => {
  const [account, setAccount] = useState("");
  const [contract, setContract] = useState(null);
  const [blocks, setBlocks] = useState([]); // 블록 상태 관리
  const [selectedUser, setSelectedUser] = useState(null); // 선택된 사용자
  const [searchedBlock, setSearchedBlocks] = useState([]); // 검색된 블록
  const [nickName, setNickName] = useState("");

  // 메타마스크 및 스마트 계약 초기화
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
            console.log(networkId);
            const deployedNetwork = IdProject.networks[networkId];
            console.log(deployedNetwork.address);
            const instance = new web3.eth.Contract(
              IdProject.abi,
              deployedNetwork.address
            );

            setContract(instance);
            fetchBlocks(instance, account); // 계약에서 직접 데이터 불러오기
          } else {
            console.error("MetaMask 계정을 찾을 수 없습니다.");
          }
        } catch (error) {
          console.error("Web3 초기화 중 오류 발생:", error);
          alert(
            "MetaMask와의 연결에 문제가 발생했습니다. MetaMask가 설치되어 있고, 계정이 연결되었는지 확인해 주세요."
          );
        }
      } else {
        console.error("Ethereum object not found. Please install MetaMask.");
        alert("MetaMask가 설치되어 있지 않습니다. MetaMask를 설치해 주세요.");
      }
    };
    initWeb3();
    
    // 새로고침 시 로컬 스토리지에서 blocks 데이터 불러오기
    const savedBlocks = localStorage.getItem("blocks");
    if (savedBlocks) {
      setBlocks(JSON.parse(savedBlocks));
    }
  }, []);

  // blocks 상태 업데이트 시 로컬 스토리지에 저장
  useEffect(() => {
    if (blocks.length > 0) {
      localStorage.setItem("blocks", JSON.stringify(blocks));
    }
  }, [blocks]);

  // 블록 정보 가져오기 (스마트 계약에서 실시간으로 블록 데이터 불러오기)
  const fetchBlocks = async (contract, account) => {
    try {
      if (contract && account) {
        // blockCount는 BigInt로 반환될 수 있으므로, 이를 BigInt로 처리합니다.
        const blockCount = await contract.methods.getIndexOf(account).call();

        if (blockCount.length === 0n) {
          // blockCount가 BigInt이므로 비교할 때도 BigInt로 비교
          console.log("No blocks available.");
          return;
        }

        const fetchedBlockResults = [];

        // i는 BigInt로 처리합니다. (1n은 BigInt 값)
        for (let i = 0; i < blockCount.length; i++) {
          const block = await contract.methods
            .getInfo(account, blockCount[i])
            .call();
          console.log(`Block ${i}:`, block);

          fetchedBlockResults.push({
            // blockId: blockCount[i].toString(), // BigInt를 string으로 변환하여 저장
            nickName: block[0],
            gameName: block[1],
            serverName: block[2],
            className: block[3],
            remarks: block[4],
            date: new Date(Number(block[5]) * 1000).toLocaleString(), // 타임스탬프 변환
          });
          if (i + 1 === blockCount.length) setNickName(block[0]);
        }

        setBlocks(fetchedBlockResults.reverse()); // 블록 상태 업데이트
      }
    } catch (error) {
      console.error("블록 정보 불러오기 중 오류 발생:", error);
    }
  };

  
  // 선택된 사용자 상태 업데이트
  const handleUserSelect = (user) => {
    setSelectedUser(user);
    fetchSearchedBlocks(user.userAddress);
  };
  

  // 사용자가 선택한 블록 정보 가져오기
  const fetchSearchedBlocks = async (account) => {
    try {
      if (contract && account) {
        // blockCount는 BigInt로 반환될 수 있으므로, 이를 BigInt로 처리합니다.
        const blockCount = await contract.methods.getIndexOf(account).call();
        if (blockCount.length === 0n) {
          // blockCount가 BigInt이므로 비교할 때도 BigInt로 비교
          console.log("No blocks available.");
          return;
        }

        const fetchedBlockResults = [];

        // i는 BigInt로 처리합니다. (1n은 BigInt 값)
        for (let i = 0; i < blockCount.length; i++) {
          const block = await contract.methods
            .getInfo(account, blockCount[i])
            .call();
          console.log(`Block ${i}:`, block);

          fetchedBlockResults.push({
            // blockId: blockCount[i].toString(), // BigInt를 string으로 변환하여 저장
            nickName: block[0],
            gameName: block[1],
            serverName: block[2],
            className: block[3],
            remarks: block[4],
            date: new Date(Number(block[5]) * 1000).toLocaleString(), // 타임스탬프 변환
          });
        }

        setSearchedBlocks(fetchedBlockResults.reverse()); // 블록 상태 업데이트
      }
    } catch (error) {
      console.error("블록 정보 불러오기 중 오류 발생:", error);
    }
  };

  // 최근 유저 
  const recentUserSelect = (address, nickName) => {
    setSelectedUser({ address, nickName });
    fetchSearchedBlocks(address);
  };
  

  // 사용자 선택 취소 (검색 화면으로 돌아가기)
  const handleBackToSearch = () => {
    setSelectedUser(null);
  };

  return (
    <div className="main-container">
      <Header />
      <h1
        style={{
          textAlign: "center",
          margin: 0,
          padding: 0,
          marginBottom: "15px",
          marginTop: "15px",
        }}
      >
        {" "}
        Welcome, {nickName}!{" "}
      </h1>
      {!selectedUser ? (
        // 검색 화면
        <Search contract={contract} onUserSelect={handleUserSelect} />
      ) : (
        // 선택된 사용자 히스토리 화면
        <div className="user-history">
          <h2>{selectedUser.nickName}님의 기록 <button onClick={handleBackToSearch} className="back-button"></button></h2>
          <History blocks={searchedBlock} />
        </div>
      )}
      {!selectedUser && (
        <>
          {/* 블록 생성 및 전체 블록 히스토리 */}
          <CreateBlock
            contract={contract}
            account={account}
            blocks={blocks}
            setBlocks={setBlocks}
            fetchBlocks={fetchBlocks}
          />
          <History blocks={blocks} />
        </>
      )}
      <Footer />
      <div className="button-container">
        
        <Stats onSelectUser={recentUserSelect} />
          
        <ChatBTN nickName={nickName} />
        <HelpBTN />
      </div>
    </div>
  );
};

export default App;
