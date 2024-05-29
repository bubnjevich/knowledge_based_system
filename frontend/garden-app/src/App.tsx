import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import {GlobalStyles} from "./components/styled/Global.style";
import SignUp from "./components/pages/RegisterComponent"
const AppContent: React.FC = () => {

    return (
        <>
      <Routes>
          <Route path="/register" element={<SignUp/>}></Route>
      </Routes>
        </>
  )
}

const App = () => {
  return (
      <div className="App">
        <GlobalStyles/>
        <Router>
          <AppContent/>
        </Router>
        <footer>
          <p>Designed by Freepik</p>
        </footer>
      </div>

  );
}

export default App;
