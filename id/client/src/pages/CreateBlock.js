import React, { useState, useEffect } from "react";
import "../css/createblock.css";
import Web3 from "web3";

const CreateBlock = ({ contract, account, blocks, setBlocks }) => {
  const [nickName, setNickName] = useState("");
  const [gameName, setGameName] = useState("");
  const [serverName, setServerName] = useState("");
  const [className, setClassName] = useState("");
  const [remarks, setRemarks] = useState("");

  // 가장 최근 블록 찾기 (현재 유저의 마지막 블록)
  useEffect(() => {
    if (blocks.length > 0) {
      const lastBlock = blocks[0]; // blocks 배열에서 제일 최신 블록을 가져옵니다 (가장 최근 추가된 블록)
      setNickName(lastBlock.nickName);  // 닉네임을 placeholder로 설정
      setGameName(lastBlock.gameName);  // 게임명 placeholder
      setServerName(lastBlock.serverName);  // 서버명 placeholder
      setClassName(lastBlock.className);  // 클래스 placeholder
    }
  }, [blocks]);  // blocks가 변경될 때마다 실행

  // 텍스트 영역 바이트 수를 체크하는 함수
  const CheckByte = (event, maxByte) => {
    const text_val = event.target.value;
    const text_len = text_val.length;

    let totalByte = 0;
    for (let i = 0; i < text_len; i++) {
      const each_char = text_val.charAt(i);
      const uni_char = escape(each_char);
      if (uni_char.length > 4) {
        // 한글 : 2Byte
        totalByte += 2;
      } else {
        // 영문, 숫자, 특수문자 : 1Byte
        totalByte += 1;
      }
      if (totalByte > maxByte) {
        alert(`최대 ${maxByte}Byte까지만 입력 가능합니다.`);
        event.target.value = text_val.substring(0, i);
        return;
      }
    }
  };

  const validateAndInsertInfo = async () => {
    if (!gameName.trim() || !serverName.trim() || !className.trim()) {
      alert("게임명, 서버명, 클래스의 입력은 필수입니다!!");
      return;
    }

    const finalNickName = nickName.trim() === "" ? "*Unknown*" : nickName.trim();
    const finalGameName = gameName.trim().replace(/\s/g, ""); 
    const finalServerName = serverName.trim().replace(/\s/g, ""); 
    const finalClassName = className.trim().replace(/\s/g, ""); 
    const finalRemarks = remarks.trim() === "" ? "*내용없음*" : remarks;

    try {
      const gasPrice = Web3.utils.toWei("20", "gwei");
      await contract.methods
        .insertInfo(finalNickName, finalGameName, finalServerName, finalClassName, finalRemarks)
        .send({ from: account, gas: 5000000, gasPrice });

      const newBlock = {
        id: blocks.length + 1,
        nickName: finalNickName,
        gameName: finalGameName,
        serverName: finalServerName,
        className: finalClassName,
        remarks: finalRemarks,
        date: new Date().toLocaleString(),
      };

      setBlocks([newBlock, ...blocks]);

      setNickName("");
      setGameName("");
      setServerName("");
      setClassName("");
      setRemarks("");

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
                  onKeyUp={(e) => CheckByte(e, 10)} 
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
                  onKeyUp={(e) => CheckByte(e, 100)} 
                  placeholder="100byte 제한"
                />
              </td>
              <td rowSpan="4">
                <div className="d-grid gap-2">
                <button
                  className="btn btn-enter"
                  type="button"
                  onClick={validateAndInsertInfo}/>
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
