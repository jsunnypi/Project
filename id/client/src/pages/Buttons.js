import { useNavigate } from 'react-router-dom';
import '../css/buttons.css'; // CSS 파일 가져오기

export function ChatBTN({nickName}) {
  console.log("nickName!!!!", nickName);  // 값이 잘 전달되는지 확인
  return (
    <div className="flo-btn-chat">
      <form action="http://192.168.0.16:8000" method="POST">
        <input type="hidden" name="user" value={JSON.stringify(nickName)} />
        <button className="flo flo-chat" type="submit"></button>
      </form>
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
