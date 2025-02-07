// History.js
import React from "react";
import "../css/history.css";

// History.js
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
                      <span className="highlight">{block.gameName} : </span>
                      <span className="highlight">
                        {block.serverName}({block.className})
                      </span>{" "}
                      의
                      <span className="highlight"> {block.nickName}</span> 님
                    </span>
                  </td>
                  <td className="entry-date">{block.date}</td>
                </tr>
                <tr>
                  <td colSpan="2">
                    <textarea
                      disabled
                      rows="auto"
                      className="show-comment"
                      value={block.remarks}
                    />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        ))
      ) : (
        <p> 생성된 블록이 없습니다. 블록을 생성해 주세요 </p>
      )}
    </div>
  );
}


export default History;
