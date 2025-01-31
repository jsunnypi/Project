import { Routes, Route} from 'react-router-dom';

import App from "../App";
import Intro from "./Intro";
import { Search } from './Search';

const AppRoutes = () => {
    return (
      <Routes>
        <Route path="/" element={<Intro />} />
        <Route path="/app" element={<App />} />
        <Route path="/search" element={<Search />} />
      </Routes>
    );
  }
  
  export default AppRoutes;