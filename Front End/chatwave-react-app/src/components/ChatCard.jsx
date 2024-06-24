// ChatCard.jsx

import { AiOutlineDown } from "react-icons/ai";

/* eslint-disable react/prop-types */
const ChatCard = ({
  name,
  userImg,
  sentTime,
  isChat,
  message,
  isNotification,
}) => {
  return (
    <div className="flex items-center justify-center py-2 group cursor-pointer">
      <div className="w-fit">
        <img
          className="h-14 w-14 rounded-full"
          src={userImg}
          alt="user profile photo"
        />
      </div>
      <div className="pl-5 w-[80%]">
        <div className="flex justify-between items-center">
          <p className="text-lg">{name}</p>
          {isChat && <p className="text-sm">{sentTime}</p>}
        </div>
        <div className="flex justify-between items-center">
          <p
            className={`${
              isChat && message?.length > 0 ? "visible" : "invisible"
            }`}
          >
            {message?.length > 15 ? message.slice(0, 15) + "..." : message}
          </p>
          <div className="flex space-x-2 items-center">
            {isNotification && (
              <div className="bg-blue-600 text-xs py-1 px-1 text-white rounded-full">
                <p className="bg-white text-xs py-1 px-1 text-white rounded-full"></p>
              </div>
            )}
            <div className="flex justify-end flex-1">
              {isChat && <AiOutlineDown className="hidden group-hover:block" />}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatCard;
