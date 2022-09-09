import React, { useEffect } from 'react';
import { ReactKeycloakProvider } from '@react-keycloak/web'
import Keycloak from 'keycloak-js'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

import Home from './components/Home'
import Login from './components/login'

import './App.css';
import PrivateRoute from './components/PrivateRoute';
import { config } from './Constants'

function App() {
  const keycloak = new Keycloak({
    url: `${config.keycloak.KEYCLOAK_BASE_URL}`,
    realm: `${config.keycloak.REALM}`,
    clientId: `${config.keycloak.CLIENTID}`
  })

  useEffect(() => {
    console.log(keycloak.token)
  }, [])

  return (
    <ReactKeycloakProvider
      authClient={keycloak}
    // initOptions={initOptions}
    // LoadingComponent={loadingComponent}
    // onEvent={(event, error) => handleOnEvent(event, error)}
    >
      <Router>
        <Routes>
          <Route path='/' element={<PrivateRoute />} >
            <Route exact path='/' element={<Home />} />
          </Route>
          {/* <Route exact path='/register' element={<Register/>}/> */}
          <Route exact path='/login' element={<Login />} />
        </Routes>
      </Router>
    </ReactKeycloakProvider>
  );
}

export default App;
