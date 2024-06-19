import { IoIosSend } from "react-icons/io";
import HeroImg from "../assets/images/hero-img.jpg";
import { Link } from "react-router-dom";

const Hero = () => {
  return (
    <div className="container max-w-[1280px] mx-auto px-4 md:px-10 bg-white py-6">
      <main className="flex flex-col md:flex-row pt-10 md:pt-0 items-center justify-between">
        <div className="w-full md:w-[60%] text-center md:text-left">
          <h1 className="text-4xl md:text-5xl font-bold text-[#1271ff] mb-2">
            Chat Wave 🌊
          </h1>
          <h2 className="text-2xl md:text-3xl text-slate-500 mb-6">
            Wave of Instant Communication
          </h2>
          <p className="text-gray-600 mb-10">
            Chat Wave is an innovative real-time chat application that brings a
            seamless communication experience to your fingertips. Designed with
            a focus on user-friendliness and efficiency, Chat Wave offers a
            comprehensive suite of features that cater to all your messaging
            needs.
          </p>
          <div className="flex justify-center md:justify-normal">
            <Link to='/signup' type="button" className="bg-blue-600 text-white px-6 py-3 rounded-full flex justify-center items-center hover:scale-105 hover:bg-blue-700">
              Start Chatting
              <IoIosSend className="text-2xl ml-1" />
            </Link>
          </div>
        </div>
        <div className="hidden md:block md:w-[35%]">
          <img src={HeroImg} alt="Chat Wave App" />
        </div>
      </main>
    </div>
  );
};

export default Hero;
