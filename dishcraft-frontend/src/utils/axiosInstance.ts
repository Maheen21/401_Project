import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8080", // ✅ api base url
});

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwtToken");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`; // ✅ add token to headers
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default instance;
