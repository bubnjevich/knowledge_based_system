import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import {GlobalStyles} from "./components/styled/Global.style";
import SignUp from "./components/pages/RegisterComponent"
import SignIn from "./components/pages/LoginComponent";
import MainFormPage from "./components/pages/MainFormPage";
import RecommendedPlantsPage from "./components/pages/RecommendedPlantsPage";
import PlantDetailsPage from "./components/pages/PlantDetailsPage";
const AppContent: React.FC = () => {

    return (
        <>
      <Routes>
          <Route path="/" element={<SignIn/>}/>
          <Route path="/mainPage" element={<MainFormPage/>}/>
          <Route path="/register" element={<SignUp/>}/>
          <Route path="/recommended" element={<RecommendedPlantsPage/>}/>
          <Route path="/plant-details" element={<PlantDetailsPage/>}/>
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
