import { FaUser, FaHistory } from "react-icons/fa";
import { IoChatbubbles } from "react-icons/io5";
import { IoMdNotifications } from "react-icons/io";

import Logo from "../assets/images/logo.png";

const About = () => {
  const about = [
    {
      icon: <FaUser />,
      title: "Seamless User Experience",
      description:
        "Chat Wave offers an intuitive and user-friendly interface, making it easy for users to register and engage in conversations.",
    },
    {
      icon: <IoChatbubbles />,
      title: "Real-Time Connectivity",
      description:
        "With state-of-the-art messaging services, Chat Wave ensures that messages are delivered instantly.",
    },
    {
      icon: <IoMdNotifications />,
      title: "Reliable Notifications",
      description:
        "Reliable Notifications: Users will never miss important messages thanks to Chat Wave’s reliable notification service.",
    },
    {
      icon: <FaHistory />,
      title: "Chat History",
      description:
        "Allows users to access their past conversations, ensuring that no detail is ever lost and memories are preserved.",
    },
  ];

  return (
    <div className="container max-w-[1280px] mx-auto px-4 md:px-10 bg-white pt-6 pb-16 ">
      <main>
        <h1 className="text-4xl md:text-5xl font-bold text-[#1271ff] underline text-center pt-4 pb-4 md:pb-12">
          About
        </h1>
        <div className="flex flex-col md:flex-row pt-10 md:pt-0 items-center justify-between space-x-4">
          <div className="hidden md:block md:w-[30%]">
            <img className="w-60 float-right" src={Logo} alt="Chat Wave App" />
          </div>
          <div className="w-full md:w-[65%] text-center md:text-left space-y-8 sm:space-y-4 pr-4 md:pr-12 lg:pr-16 xl:pr-28">
            {about.map((item) => (
              <div key={item.title} className="flex flex-col sm:flex-row items-center md:text-justify space-x-2">
                <div className="text-xl pt-1 text-blue-900 hover:text-blue-700 sm:pr-1">{item.icon}</div>
                <p className="text-lg text-slate-700 hover:text-slate-900">
                  <span className="text-[#1271ff] font-bold hover:text-blue-700 block sm:inline">
                    {item.title}:
                  </span>{" "}
                  &nbsp;
                  {item.description}
                </p>
              </div>
            ))}
          </div>
        </div>
      </main>
    </div>
  );
};

export default About;
