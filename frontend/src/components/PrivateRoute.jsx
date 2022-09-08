import React from 'react'
//import AuthService from './Services/AuthService'
import { Navigate, Route } from 'react-router-dom'

const isAuthenticated = false //AuthService.isLoggedIn()

const PrivateRoute = ({ }) => {
  return isAuthenticated ? true : <Navigate to="/signin" />;
};

export default PrivateRoute