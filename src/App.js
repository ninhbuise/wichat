import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

import Signin from './components/signin'

import './App.css';


function App() {
  return (
    <Router>
      <Routes>
        <Route exact path='/signin' element={<Signin />} />
      </Routes>
    </Router>
  );
}

export default App;
