import React from "react";
import "../css/history.css";

export function History({ blocks }) {
  return (
    <div className="con-history">
      {blocks.length > 0 ? (
        blocks.map((block, index) => (
          <div key={index}>
            <table className="his-con-table">
              <tbody>
                <tr className="his-title-date">
                  <td className="his-title">
                    <span className="his-title-data">
                      {block.nickName}님의 {block.gameName} / {block.serverName}{" "}
                      / {block.className} 접속 기록
                    </span>
                  </td>
                  <td className="entry-date">{block.date}</td>{" "}
                  {/* 날짜는 그대로 출력 */}
                </tr>
                <tr>
                  <td colSpan="2">
                    <textarea
                      disabled
                      rows="auto"
                      className="show-comment"
                      value={block.remarks}
                    />
                    <hr />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        ))
      ) : (
        <p>No blocks available.</p> // 블록이 없을 경우 안내 메시지 표시
      )}
    </div>
  );
}

export default History;
