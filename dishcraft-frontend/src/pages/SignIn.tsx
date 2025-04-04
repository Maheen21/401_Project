import React, { useState } from "react";
import axios from "../utils/axiosInstance";
import { useNavigate } from "react-router-dom";
import PrimaryButton from "../components/PrimaryButton";

const SignIn = () => {
  const navigate = useNavigate();
  const [usernameOrEmail, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
        const response = await axios.post("/api/auth/login", {
            usernameOrEmail,
          password,
        });
    
        const { token, role } = response.data;
    
        // save token and role to localStorage
        localStorage.setItem("jwtToken", token);
        localStorage.setItem("userRole", role); // can store userole for role-based access
        navigate("/mypage");
        window.location.reload();
    } catch (err) {
      setErrorMsg("login failed. please check your email and password.");
      console.error("Login error:", err);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-20 p-6 border rounded-md shadow">
      <h1 className="text-2xl font-bold mb-4 text-center">🔐 login</h1>

      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      <input
        type="text"
        placeholder="Username"
        value={usernameOrEmail}
        onChange={(e) => setUsername(e.target.value)}
        required
        className="border px-4 py-2 rounded-md"
        />
        <input
          type="password"
          placeholder="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="border px-4 py-2 rounded-md"
        />

        {errorMsg && <p className="text-red-500">{errorMsg}</p>}

        <PrimaryButton type="submit">login</PrimaryButton>
      </form>
    </div>
  );
};

export default SignIn;
