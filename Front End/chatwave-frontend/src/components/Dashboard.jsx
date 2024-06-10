import { AiOutlineSearch } from "react-icons/ai";
import { MdOutlineGroupAdd } from "react-icons/md";
import {
  BsEmojiSmile,
  BsFilter,
  BsMicFill,
  BsThreeDotsVertical,
} from "react-icons/bs";
import ChatCard from "./ChatCard";
import { useState } from "react";
import MessageCard from "./MessageCard";
import "./Dashboard.css";
import { ImAttachment } from "react-icons/im";
import Profile from "./Profile";
import CreateGroup from "./CreateGroup";

const Dashboard = () => {
  const [query, setQuery] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [content, setContent] = useState("");
  const [isProfile, setIsProfile] = useState(false);
  const [isGroup, setIsGroup] = useState(false);

  const handleSearch = () => {};

  const handleClickonChatCard = () => {
    setCurrentChat(true);
  };

  const handleCreateNewMessage = () => {};

  // const handleNavigate = () => {
  //   // navigate("/profile")
  //   setIsProfile(true);
  // };

  const handleProfileCloseOpen = () => {
    setIsProfile(!isProfile);
  };

  const handleCreateGroupCloseOpen = () => {
    setIsGroup(!isGroup);
  };

  return (
    <div className="relative">
      <div className="py-14 bg-[#1271ff] w-full"></div>
      <div className="flex bg-[#f0f2f5] h-[90vh] w-[96vw] absolute top-[5vh] left-[2vw] rounded-lg">
        <div className="left w-[30%] h-full bg-[#e8e9ec] rounded-lg">
          {/* profile */}
          {isProfile ? (
            <div className="w-full h-full">
              <Profile handleProfileCloseOpen={handleProfileCloseOpen} />
            </div>
          ) : isGroup ? (
            // create group
            <div className="w-full h-full">
              <CreateGroup handleCreateGroupCloseOpen={handleCreateGroupCloseOpen} />
            </div>
          ) : (
            // home
            <div className="w-full h-full">
              <div className="flex justify-between items-center p-3">
                <div
                  className="flex items-center space-x-3"
                  onClick={handleProfileCloseOpen}
                >
                  <img
                    className="rounded-full w-10 h-10 cursor-pointer"
                    src="https://cdn.pixabay.com/photo/2023/04/09/10/32/little-pied-cormorant-7911240_960_720.jpg"
                    alt="profile picture"
                  />
                  <p>username</p>
                </div>
                <div className="space-x-3 text-2xl flex">
                  <MdOutlineGroupAdd className="cursor-pointer" onClick={handleCreateGroupCloseOpen} />
                </div>
              </div>
              <div className="relative flex justify-center items-center bg-white py-4 px-3">
                <input
                  className="border-none outline-none bg-slate-200 rounded-md w-[93%] pl-10 py-2"
                  type="text"
                  placeholder="Search or Start new Chat"
                  onChange={(e) => {
                    setQuery(e.target.value);
                    handleSearch(e.target.value);
                  }}
                  value={query}
                />
                <AiOutlineSearch className="left-9 top-7 absolute" />
              </div>
              {/* all users */}
              <div className="bg-white overflow-y-scroll h-full px-4">
                {query &&
                  [1, 1, 1, 1, 1].map((item) => (
                    <div key={item} onClick={handleClickonChatCard}>
                      <hr />
                      <ChatCard />
                    </div>
                  ))}
              </div>
            </div>
          )}
        </div>
        {!currentChat ? (
          // default window
          <div className="w-[70%] flex flex-col items-center justify-center h-full">
            <div className="max-w-[70%] text-center">
              <img
                src="https://www.bleepstatic.com/content/posts/2020/07/14/WelcomeChat.png"
                alt="welcome chat"
              />
              <h1 className="text-4xl text-gray-600">Chat Wave</h1>
              <p className="my-9">Wave of Instant Communication</p>
            </div>
          </div>
        ) : (
          // message window
          <div className="w-[70%] relative">
            {/* message header */}
            <div className="header absolute top-0 w-full bg-[#f0f2f5]">
              <div className="flex justify-between">
                <div className="p-3 space-x-4 flex items-center">
                  <img
                    className="w-10 h-10 rounded-full "
                    src="https://cdn.pixabay.com/photo/2024/05/31/06/44/apple-tree-8799899_1280.jpg"
                    alt="user profile pic"
                  />
                  <p>username</p>
                </div>
                <div className="p-3 space-x-4 flex items-center">
                  <AiOutlineSearch />
                  <BsThreeDotsVertical />
                </div>
              </div>
            </div>
            {/* message section */}
            <div className="px-10 h-[85vh] overflow-y-scroll bg-slate-200">
              <div className="space-y-1 flex flex-col justify-center mt-20 py-2">
                {[1, 1, 1, 1, 1].map((item, i) => (
                  <MessageCard
                    key={item}
                    isReqUser={i % 2 == 0}
                    content={"message"}
                  />
                ))}
              </div>
            </div>
            {/* message typing footer */}
            <div className="footer bg-[#f0f2f5] absolute bottom-0 w-full py-3 text-2xl ">
              <div className="flex justify-between items-center px-5 relative">
                <BsEmojiSmile className="cursor-pointer" />
                <ImAttachment className="cursor-pointer" />
                <input
                  className="py-2 outline-none border-none bg-white pl-4 rounded-md w-[85%]"
                  type="text"
                  onChange={(e) => setContent(e.target.value)}
                  placeholder="Type message..."
                  value={content}
                  onKeyPress={(e) => {
                    if (e.key === "Enter") {
                      handleCreateNewMessage();
                      setContent("");
                    }
                  }}
                />
                <BsMicFill />
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
