import { Route, Routes } from "react-router-dom";

import HomePage from "./pages/HomePage";
import AboutPage from "./pages/AboutPage";
import SignupPage from "./pages/SignupPage";
import SigninPage from "./pages/SigninPage";
import ForgotPasswordPage from "./pages/ForgotPasswordPage";
import DashboardPage from "./pages/DashboardPage";
import ReactivateAccountPage from "./pages/ReactivateAccountPage";
import CreateGroupRoom from "./components/CreateGroupRoom";
const App = () => {
  return (
    <div>
      <Routes>
        <Route path="/group-room" element={<CreateGroupRoom />} />
        <Route path="/" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/signin" element={<SigninPage />} />
        <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        <Route path="/reactivate-account" element={<ReactivateAccountPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
      </Routes>
    </div>
  );
};

export default App;
