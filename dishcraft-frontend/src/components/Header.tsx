import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Menu, X } from "lucide-react";
import PrimaryButton from "./PrimaryButton";

const Header = () => {
  const location = useLocation();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsLoggedIn(!!token);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");
    setIsLoggedIn(false);
    window.location.reload();
  };

  return (
    <header className="bg-white shadow-md">
      <div className="max-w-screen-xl mx-auto px-6 py-4 flex items-center justify-between">
        <Link to="/" className="text-xl font-bold">
          🍽️ DishCraft
        </Link>

        {/* Desktop Menu */}
        <div className="hidden lg:flex items-center gap-6">
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

        {/* Mobile Menu Toggle */}
        <div className="lg:hidden">
          <button onClick={() => setMobileOpen(!mobileOpen)}>
            {mobileOpen ? <X size={28} /> : <Menu size={28} />}
          </button>
        </div>
      </div>

      {/* Mobile Menu Content */}
      {mobileOpen && (
        <div className="lg:hidden px-6 pb-4 flex flex-col gap-4 ">
          <nav className="flex flex-col gap-2 ">
            <Link
              to="/find"
              onClick={() => setMobileOpen(false)}
              className={`font-medium ${
                location.pathname === "/find"
                  ? "text-black"
                  : "text-gray-600 hover:text-black hover:bg-gray-100 hover:pl-2 rounded-md transition-all duration-200"
              }`}
            >
              Find a Recipe
            </Link>
            <Link
              to="/explore"
              onClick={() => setMobileOpen(false)}
              className={`font-medium ${
                location.pathname === "/explore"
                  ? "text-black"
                  : "text-gray-600 hover:text-black hover:bg-gray-100 hover:pl-2 rounded-md transition-all duration-200"
              }`}
            >
              Explore a Recipe
            </Link>
          </nav>

          <div className="flex flex-col gap-2 mt-2">
            {isLoggedIn ? (
              <>
                <Link to="/mypage" onClick={() => setMobileOpen(false)}>
                  <PrimaryButton className="w-full">My Page</PrimaryButton>
                </Link>
                <PrimaryButton
                  variant="outline"
                  className="w-full"
                  onClick={() => {
                    setMobileOpen(false);
                    handleLogout();
                  }}
                >
                  Logout
                </PrimaryButton>
              </>
            ) : (
              <>
                <Link to="/signin" onClick={() => setMobileOpen(false)}>
                  <PrimaryButton variant="outline" className="w-full">
                    Sign in
                  </PrimaryButton>
                </Link>
                <Link to="/register" onClick={() => setMobileOpen(false)}>
                  <PrimaryButton className="w-full">Register</PrimaryButton>
                </Link>
              </>
            )}
          </div>
        </div>
      )}
    </header>
  );
};

export default Header;
