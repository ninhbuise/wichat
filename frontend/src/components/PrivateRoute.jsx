import React from 'react'
import { useKeycloak } from '@react-keycloak/web'
import { Navigate, Outlet } from 'react-router-dom';

const PrivateRoute = () => {
  const { keycloak } = useKeycloak()
  return keycloak?.authenticated ? <Outlet /> : <Navigate to="/login" />
}

export default PrivateRoute