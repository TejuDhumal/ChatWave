import { Route, Routes } from 'react-router-dom';

import HomePage from './pages/HomePage';
import AboutPage from './pages/AboutPage';
import ContactPage from './pages/ContactPage';
import SignupPage from './pages/SignupPage';
import SigninPage from './pages/SigninPage';
import ForgotPasswordPage from './pages/ForgotPasswordPage';
import DashboardPage from './pages/DashboardPage';

const App = () => {
  return (
    <div>
      <Routes>
        <Route path='/' element={<HomePage />}></Route>
        <Route path='/about' element={<AboutPage />}></Route>
        <Route path='/contact' element={<ContactPage />}></Route>
        <Route path='/signup' element={<SignupPage />}></Route>
        <Route path='/signin' element={<SigninPage />}></Route>
        <Route path='/forgot-password' element={<ForgotPasswordPage />}></Route>
        <Route path='/dashboard' element={<DashboardPage />}></Route>
      </Routes>
    </div>
  )
}

export default App