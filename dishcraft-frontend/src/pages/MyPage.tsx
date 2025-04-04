import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const MyPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
      navigate("/signin"); // 🔒 redirect to signin page if no token found in localStorage
    }
  }, [navigate]);

  return (
    <div className="p-10">
      <h1 className="text-2xl font-bold">👤 My Page</h1>
      <p>only logged in user can see this page</p>
    </div>
  );
};

export default MyPage;
