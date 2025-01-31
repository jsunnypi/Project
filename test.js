import React, { useState } from "react";
import '../css/createblock.css';


const CreateBlock = ({ contract, account, getInfo, blocks, setBlocks }) => {
  const [nickName, setNickName] = useState("");
  const [gameName, setGameName] = useState("");
  const [serverName, setServerName] = useState("");
  const [className, setClassName] = useState("");
  const [remarks, setRemarks] = useState("");

  const insertInfo = async () => {
    console.log(nickName, gameName, serverName, className, remarks);
    try {
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
                  value={remarks}
                  rows="7"
                  cols="70"
                  onChange={(e) => setRemarks(e.target.value)}
                  placeholder="100byte 제한"
                />
              </td>
              <td rowSpan="4">
                <div className="d-grid gap-2">
                  <button
                    className="btn btn-enter"
                    type="button"
                    onClick={insertInfo}
                  >
                    저장
                  </button>
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
