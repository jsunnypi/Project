import { useNavigate } from 'react-router-dom';
import '../css/buttons.css'; // CSS 파일 가져오기

export function ChatBTN() {
 // const navigate = useNavigate();

  // 로컬 스토리지에서 저장된 nickName을 가져오기
  const handleClick = () => {
    const nickName = localStorage.getItem('nickName'); // 저장된 nickName을 가져오기
    console.log("nickname:" + nickName); // 확인용 로그

    window.location.href = `http://localhost:8000?nickName=${nickName}`;
  };

  return (
    <div className="flo-btn-chat">
      <button className="flo flo-chat" onClick={handleClick}>
   
      </button>
    </div>
  );
}

export function HelpBTN() {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/help');
  };

  return (
    <div className="flo-btn-help">
      <button className="flo flo-help" onClick={handleClick}>
      </button>
    </div>
  );
}
