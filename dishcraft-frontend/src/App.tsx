import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import FindRecipe from "./pages/FindRecipe";
import Explore from "./pages/Explore";
import SignIn from "./pages/SignIn";
import Register from "./pages/Register";
import MyPage from "./pages/MyPage";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/find" element={<FindRecipe />} />
        <Route path="/explore" element={<Explore />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/register" element={<Register />} />
        <Route path="/mypage" element={<MyPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
