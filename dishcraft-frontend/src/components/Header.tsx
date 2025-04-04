import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import PrimaryButton from "./PrimaryButton";

const Header = () => {
  const location = useLocation();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");
    setIsLoggedIn(false);
    window.location.reload(); //  navigate("/") or to "/home" if using react-router
  };
  
  return (
    <header className="flex items-center justify-between px-6 py-4 shadow-md bg-white">
      {/* logo */}
      <Link to="/" className="text-xl font-bold">
        🍽️ DishCraft
      </Link>

    {/* manu + button */}
    <div className="flex items-center gap-6">
        {/* nav manu */}
        <nav className="flex gap-6">
        <Link
            to="/find"
            className={`font-medium ${
            location.pathname === "/find"
                ? "border-b-2 border-black text-black"
                : "text-gray-600 hover:text-black"
            }`}
        >
            Find a Recipe
        </Link>
        <Link
            to="/explore"
            className={`font-medium ${
            location.pathname === "/explore"
                ? "border-b-2 border-black text-black"
                : "text-gray-600 hover:text-black"
            }`}
        >
            Explore a Recipe
        </Link>
        </nav>

        {/* buttons */}
        <div className="flex gap-3">
        {isLoggedIn ? (
            <>
            <Link to="/mypage">
                <PrimaryButton>My Page</PrimaryButton>
            </Link>
            <PrimaryButton variant="outline" onClick={handleLogout}>
                Logout
            </PrimaryButton>
            </>
        ) : (
            <>
            <Link to="/signin">
                <PrimaryButton variant="outline">Sign in</PrimaryButton>
            </Link>
            <Link to="/register">
                <PrimaryButton>Register</PrimaryButton>
            </Link>
            </>
        )}
        </div>
    </div>
    </header>
  );
};

export default Header;
