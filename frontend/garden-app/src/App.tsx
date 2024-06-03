import React, {useEffect, useState} from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import {GlobalStyles} from "./components/styled/Global.style";
import SignUp from "./components/pages/RegisterComponent"
import SignIn from "./components/pages/LoginComponent";
import MainFormPage from "./components/pages/MainFormPage";
import RecommendedPlantsPage from "./components/pages/RecommendedPlantsPage";
import PlantDetailsPage from "./components/pages/PlantDetailsPage";
import { useSelector } from "react-redux";
import {RootState} from "./auth/store";
import ButtonAppBar from "./components/ButtonAppBar";
import AlarmsDetailsPage from "./components/pages/AlarmsDetailsPage";


const AppContent: React.FC = () => {

    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsLoggedIn(token != null);
    }, []);

    return (
        <>
            {isLoggedIn && <ButtonAppBar setIsLoggedIn={setIsLoggedIn} />}

        <Routes>
        <Route path="/" element={<SignIn setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/mainPage" element={<MainFormPage/>}/>
          <Route path="/register" element={<SignUp/>}/>
          <Route path="/recommended" element={<RecommendedPlantsPage/>}/>
          <Route path="/plant-details" element={<PlantDetailsPage/>}/>
          <Route path="/alarms" element={<AlarmsDetailsPage/>}/>
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

      </div>

  );
}

export default App;
