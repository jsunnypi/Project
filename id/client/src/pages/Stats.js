import { useEffect, useState } from "react";
import Web3 from "web3";
import IdProject from "../IdProject.json"; // 스마트 계약 ABI 파일
import "../css/stats.css";

export function Stats({ onSelectUser }) {
  const [totalUsers, setTotalUsers] = useState(0);
  const [totalBlocks, setTotalBlocks] = useState(0);
  const [recentNicknames, setRecentNicknames] = useState([]);
  const [recentAddresses, setRecentAddresses] = useState([]);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        if (!window.ethereum) {
          console.error("MetaMask not detected");
          return;
        }

        const web3 = new Web3(window.ethereum);
        await window.ethereum.request({ method: "eth_requestAccounts" });
        const networkId = await web3.eth.net.getId();
        const deployedNetwork = IdProject.networks[networkId];
        const contract = new web3.eth.Contract(
          IdProject.abi,
          deployedNetwork.address
        );

        // 스마트 계약에서 데이터 가져오기
        const userCount = await contract.methods.getTotalUserCount().call();
        const blockCount = await contract.methods.getTotalBlockCount().call();
        const recentUserList = await contract.methods.getRecentUser().call();
        console.log("최근활동유저!!!!", recentUserList);

        setTotalUsers(userCount);
        setTotalBlocks(blockCount);

        // recentUserList[0] = 닉네임, recentUserList[1] = 계정주소
        setRecentNicknames(recentUserList[0]);
        setRecentAddresses(recentUserList[1]);
      } catch (error) {
        console.error("Error fetching stats:", error);
      }
    };

    fetchStats();
  }, []);

  const handleUserSelect = (address, nickName) => {
    onSelectUser(address, nickName);
  };

  return (
    <>
      <table className="total-table">
        <tbody>
          <tr>
            <td className="stats-title">총 누적 유저</td>
            <td className="total-ctn">{totalUsers}</td>
          </tr>
          <tr>
            <td className="stats-title">총 누적 블록</td>
            <td className="total-ctn">{totalBlocks}</td>
          </tr>
        </tbody>
      </table>
      <div className="list-form">
        <p>최근 활동 유저</p>
        <div className="stats-list">
          {recentNicknames.length > 0 ? (
            <div className="user-list">
              {recentNicknames.map((nickName, index) => (
                <div
                  key={index}
                  className="user-item"
                  onClick={() =>
                    handleUserSelect(recentAddresses[index], nickName)
                  }
                >
                  <span>{nickName}</span>
                </div>
              ))}
            </div>
          ) : (
            "데이터 없음"
          )}
        </div>
      </div>
    </>
  );
}

export default Stats;
