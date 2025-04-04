import React, { useState } from "react";
import axios from "../utils/axiosInstance";
import { useNavigate } from "react-router-dom";
import PrimaryButton from "../components/PrimaryButton";

const Register = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
        await axios.post("/api/auth/register", {
            id: Number.MAX_SAFE_INTEGER,
            username,
            password,
            email,
            role: "USER",
        });

      setSuccess(true);
      setTimeout(() => {
        navigate("/signin"); // Redirect to the sign-in page after 1.5 seconds
        window.location.reload(); // Reload the page to reflect the new state
      }, 1500);
    } catch (err) {
      setErrorMsg("failed to register. please check your username, email, and password.");
      console.error("Registration error:", err);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-20 p-6 border rounded-md shadow">
      <h1 className="text-2xl font-bold mb-4 text-center">📝 Register</h1>

      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          className="border px-4 py-2 rounded-md"
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="border px-4 py-2 rounded-md"
        />

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          className="border px-4 py-2 rounded-md"
        />

        {errorMsg && <p className="text-red-500">{errorMsg}</p>}
        {success && <p className="text-green-600">🎉 Registration Successed! Redirect to log-in page</p>}

        <PrimaryButton type="submit">Register</PrimaryButton>
      </form>
    </div>
  );
};

export default Register;
