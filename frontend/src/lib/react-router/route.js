
import { createBrowserRouter, redirect } from "react-router-dom";
import ErrorPage from "../../components/Error/error-page";
import WelcomePage from "../../components/Home";
import Login from "../../components/login";


  const isAuthenticated = async() => {
    let token =  localStorage.getItem('jwtToken');

    if (!token) {
        //so sanh token
        return redirect("/login");
    }

    return !!token; 
  };


export const router = createBrowserRouter([
    {
      path: "/",
      loader: isAuthenticated,
      element: (
            <WelcomePage />
      ),
      errorElement: <ErrorPage/>
    },
    {
      path: "/login",
      element: <Login/>
    },
  ]);