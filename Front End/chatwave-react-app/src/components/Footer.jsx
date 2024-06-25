import Logo from "../../public/logo.png";

const Footer = () => {
  return (
    <footer>
      <div className="container max-w-[1440px] mx-auto px-10 py-4 bg-blue-200">
        <div className="flex justify-center items-center space-x-2">
          <img className="w-7 h-7" src={Logo} alt="" />
          <p className="font-bold text-[#1271ff]">Chat Wave</p>
        </div>
        <div className="flex flex-col sm:flex-row justify-center items-center sm:space-x-4 mt-4 text-slate-700">
          <p>Chat Wave &copy; 2024</p>
          <p className="hidden sm:block">|</p>
          <p className="cursor-pointer hover:text-slate-900">
            Terms & Privacy Policy
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
