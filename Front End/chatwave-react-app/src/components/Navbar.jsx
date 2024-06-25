/* eslint-disable react/prop-types */
import { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { FaBars, FaTimes } from "react-icons/fa";
import Logo from "../../public/logo.png";

const Navbar = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const location = useLocation();
  const currentPath = location.pathname;

  const links = [
    {
      path: "/",
      name: "Home",
    },
    {
      path: "/about",
      name: "About",
    },
  ];

  return (
    <header>
      <div className={`max-w-[1440px] mx-auto px-4 sm:px-10 bg-white border-b`}>
        <nav className="py-4">
          <div className="flex items-center justify-between">
            <Link
              to="/"
              className="flex items-center space-x-2 text-2xl font-bold text-[#1271ff] mr-4 hover:text-blue-700"
            >
              <img className="w-10 h-10" src={Logo} alt="Logo" />
              <span>Chat Wave</span>
            </Link>
            <div className="md:hidden">
              <button
                className="text-[#1271ff] text-2xl"
                onClick={() => setIsMenuOpen(!isMenuOpen)}
              >
                {isMenuOpen ? <FaTimes /> : <FaBars />}
              </button>
            </div>
            <div
              className={`flex-col md:flex-row md:flex space-x-0 md:space-x-6 justify-between items-center absolute md:relative bg-white w-full md:w-auto left-0 md:left-auto top-16 md:top-auto ${
                isMenuOpen ? "flex" : "hidden"
              } border-b-2 border-[#1271ff] md:border-none pb-4 md:pb-0`}
            >
              {links.map((link) => (
                <Link
                  key={link.name}
                  to={link.path}
                  className={`block py-2 md:py-0 ${
                    currentPath == link.path
                      ? "underline underline-offset-4 text-blue-700 scale-105"
                      : "text-[#1271ff] hover:text-blue-700 hover:scale-105"
                  }`}
                >
                  {link.name}
                </Link>
              ))}
              <Link
                to="/signup"
                type="button"
                className="text-[#1271ff] px-3 py-1.5 rounded-full border-2 border-[#1271ff] hover:border-blue-700 hover:scale-105 hover:bg-[#effeff] block my-2 md:my-0"
              >
                Sign up
              </Link>
              <Link
                to="/signin"
                type="button"
                className="bg-[#1271ff] text-white px-4 py-1.5 rounded-full border-2 border-[#1271ff] hover:bg-blue-700 hover:scale-105 block my-2 md:my-0"
              >
                Sign in
              </Link>
            </div>
          </div>
        </nav>
        <hr
          className={`${
            isMenuOpen ? "block" : "hidden"
          } border-b-4 border-black`}
        />
      </div>
    </header>
  );
};

export default Navbar;
