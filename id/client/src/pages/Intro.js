import "../css/intro.css";
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Intro = () => {
  const [showTutorial, setShowTutorial] = useState(false);
  const navigate = useNavigate(); 
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleTutorialClick = () => {
    setShowTutorial(prevState => !prevState);  // 튜토리얼 이미지 표시/숨기기
  };

  const handleStartClick = async () => {
    if (isLoggedIn) {
      navigate("/app");  // 로그인 되어 있으면 바로 /app으로 이동
    } else {
      if (window.ethereum) {
        try {
          setIsLoggedIn(true);  // 로그인 요청 시작 시 로딩 상태로 변경

          // MetaMask에 연결된 계정이 있는지 확인
          const accounts = await window.ethereum.request({ method: 'eth_accounts' });

          if (accounts.length > 0) {
            navigate("/app");  // 로그인 되어 있으면 바로 /app으로 이동
          } else {
            alert("MetaMask에 로그인되어 있지 않습니다. 로그인 후 다시 시도해주세요");
            const newAccounts = await window.ethereum.request({ method: 'eth_requestAccounts' });
            
            if (newAccounts.length > 0) {
              navigate("/app");  // 로그인 후 /app으로 이동
            } else {
              alert("로그인 실패. 다시 시도해주세요.");
            }
          }
        } catch (error) {
          console.error("로그인 오류:", error);
        } finally {
          setIsLoggedIn(false);  // 로딩 상태 종료
        }
      } else {
        alert("MetaMask가 설치되어 있지 않습니다.");
      }
    }
  };

  return (
    <div className="main-container-int">
      <div className="main-img">
        <img src="/main_bg.png" alt="main" />
      </div>
      <div className="btn-group" role="group" aria-label="Basic mixed styles example">
        <button
          type="button"
          className="btn btn-tutorial"
          onClick={handleTutorialClick} />  
          
          <button type="button" className="btn btn-start"
            onClick={handleStartClick}
            disabled={isLoggedIn}  // 로그인 중일 때 버튼 비활성화
          />

      {showTutorial && (
        <div className="tutorial-overlay">
          <div className="tutorial-content">
            <img src="/tutorial.png" alt="Tutorial" />
            <button className="btn-close" onClick={handleTutorialClick}>X</button>
          </div>
        </div>
      )}
    </div>
    </div>
  );
};

export default Intro;
