import '../css/header.css'
import { useNavigate } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();
    const handleIntroClick = () => {
        navigate("/");
      };

    return (
        <div id="header">            
                <div><img id="logo" src="/logo0101.png" alt="Logo" onClick={handleIntroClick} /></div>
            <hr />
        </div>
    )
}
export default Header;