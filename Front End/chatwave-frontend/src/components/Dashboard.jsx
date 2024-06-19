import React, { useState, useEffect, useRef } from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { MdOutlineGroupAdd, MdOutlineVideoCall } from "react-icons/md";
import { HiOutlineGif } from "react-icons/hi2";
import { IoMdSend, IoMdPower } from "react-icons/io";
import {
  BsArrowLeft,
  BsEmojiSmile,
  BsThreeDotsVertical,
  BsPencil,
  BsCheck2,
  BsTrash,
  BsArrowRight,
} from "react-icons/bs";
import { ImAttachment } from "react-icons/im";
import {
  Popover,
  Button,
  Box,
  ClickAwayListener,
  Snackbar,
  Alert,
  Modal,
} from "@mui/material";
import Picker from "emoji-picker-react";
import { REGISTER, LOGIN, REQ_USER } from "../redux/Auth/ActionType";
import { useDispatch, useSelector } from "react-redux";
import { currentUser, searchUser } from "../redux/Auth/Action";
import {
  createSingleChat,
  getAllChat,
  updateGroup,
  addGroupMembers,
  removeGroupMember,
} from "../redux/Chat/Action";
import {
  createNewMessage,
  getAllMessage,
  editMessage,
} from "../redux/Message/Action";
import { setFieldToNull } from "../redux/Empty/Action";
import { useNavigate } from "react-router-dom";
import ChatCard from "./ChatCard";
import MessageCard from "./MessageCard";
import Profile from "./Profile";
import CreateGroup from "./CreateGroup";
import SimpleSnackbar from "./SimpleSnackbar";
import GiphySearch from "./GiphySearch";
import SelectedMember from "./SelectedMember";
import "./Dashboard.css";
import Logo from "../assets/images/logo.png";
import ChatBg from "../assets/images/chatwave-bg.jpg";
import { BASE_URL } from "../config/Api";
import axios from "axios";

const Dashboard = () => {
  const [query, setQuery] = useState("");
  const [currentChat, setCurrentChat] = useState(null);
  const [content, setContent] = useState("");
  const [isProfile, setIsProfile] = useState(false);
  const [isCreateGroup, setIsCreateGroup] = useState(false);
  const [isChatSearch, setIsChatSearch] = useState(false);
  const [chatSearchQuery, setChatSerachQuery] = useState("");
  const [anchorEl, setAnchorEl] = useState(null);
  const [showPopover, setShowPopover] = useState(false);
  const [showEmojiPicker, setShowEmojiPicker] = useState(false);
  const [showGiphySearch, setShowGiphySearch] = useState(false);
  const [showSnackbar, setShowSnackbar] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [open, setOpen] = useState(false);
  const [isLogin, setIsLogin] = useState(false);
  const [isEditMessage, setIsEditMessage] = useState(false);
  const [messageId, setMessageId] = useState(null);
  const [groupName, setGroupName] = useState("");
  const [editGroupName, setEditGroupName] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false); // State to control modal
  const [addUserQuery, setAddUserQuery] = useState("");
  const [groupMember, setGroupMember] = useState(new Set()); // State to manage selected group members

  const messageRef = useRef();
  const inputRef = useRef(null);
  const fileInputRef = useRef(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { auth, chat, message } = useSelector((store) => store);

  const token = localStorage.getItem("token");

  const handleClose = () => setOpen(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    dispatch(setFieldToNull(REGISTER));
    dispatch(setFieldToNull(LOGIN));
    dispatch(setFieldToNull(REQ_USER));
    handleClose();
    navigate("/signin");
  };

  useEffect(() => {
    if (token) {
      dispatch(currentUser(token));
      setIsLogin(true);
    }
  }, [token, auth.updatedUser]);

  useEffect(() => {
    if (!auth.reqUser) navigate("/signin");
  }, [auth.reqUser]);

  useEffect(() => {
    if (currentChat) {
      dispatch(getAllMessage({ chatId: currentChat.id, token }));
    }
  }, [currentChat, token]);

  useEffect(() => {
    if (message.messages) {
      setMessages(message.messages);
    }
  }, [message.messages]);

  useEffect(() => {
    if (messageRef.current) {
      messageRef.current.scrollIntoView();
    }
  }, [messages]);

  useEffect(() => {
    const interval = setInterval(() => {
      if (currentChat) {
        dispatch(getAllMessage({ chatId: currentChat.id, token }));
      }
    }, 5000); // Poll every 5 seconds

    return () => clearInterval(interval);
  }, [currentChat, token]);

  const handleCurrentChat = (item) => {
    setCurrentChat(item);
    if (messageRef.current) {
      messageRef.current.scrollIntoView({
        behavior: "smooth",
      });
    }
  };

  const createNewChat = (userId) => {
    const data = { token, userId };
    if (token) dispatch(createSingleChat(data));
  };

  useEffect(() => {
    if (token) dispatch(getAllChat(token));
  }, [token, chat.singleChat, chat.createdGroup]);

  const handleCreateNewMessage = () => {
    if (!content) return; // Ensure content is not empty
    const data = { token, chatId: currentChat?.id, content };
    dispatch(createNewMessage(data)).then(() => {
      setMessages((prevMessages) => [
        ...prevMessages,
        {
          id: new Date().getTime(), // Temporary ID until the real one is returned
          content,
          user: { id: auth.reqUser.id },
          chat: { id: currentChat.id },
        },
      ]);
      setContent(""); // Clear the input field
      // Fetch the latest messages to get the correct ID from the server
      dispatch(getAllMessage({ chatId: currentChat.id, token }));
    });
  };

  const handleSearch = (keyword) => {
    dispatch(searchUser({ userId: auth.reqUser?.id, keyword, token }));
  };

  const handleChatHistorySearch = () => {};

  const handleProfileCloseOpen = () => {
    setIsProfile(!isProfile);
  };

  const handleCreateGroupCloseOpen = () => {
    setIsCreateGroup(!isCreateGroup);
  };

  const handleEditMessage = (content, messageId) => {
    setContent(content);
    setMessageId(messageId);
    setIsEditMessage(true);
  };

  const handleEditMessageSend = () => {
    const data = { token, content, messageId };
    dispatch(editMessage(data)).then(() => {
      setIsEditMessage(false);
      setMessageId(null);
      dispatch(getAllMessage({ chatId: currentChat.id, token }));
    });
  };

  const handleThreeDotsClick = (event) => {
    setAnchorEl(event.currentTarget);
    setShowPopover(true);
  };

  const handleBlockUser = () => {
    setAnchorEl(null);
  };

  const handleViewUserClose = () => {
    setAnchorEl(null);
    setShowPopover(false);
  };

  const onEmojiClick = (emojiObject) => {
    const { selectionStart, selectionEnd } = inputRef.current;
    const newText =
      content.slice(0, selectionStart) +
      emojiObject.emoji +
      content.slice(selectionEnd);
    setContent(newText);
    setTimeout(() => {
      inputRef.current.selectionStart =
        selectionStart + emojiObject.emoji.length;
      inputRef.current.selectionEnd = selectionStart + emojiObject.emoji.length;
    }, 0);
  };

  const handleFileChange = async (e) => {
    const files = Array.from(e.target.files);
    const invalidFiles = files.filter((file) => file.size > 20 * 1024 * 1024); // 20mb limit per file

    if (invalidFiles.length > 0) {
      setSnackbarMessage("File size should not exceed 20 MB limit!");
      setShowSnackbar(true);
    } else {
      console.log("Files selected:", files);
      const uploadedFiles = [];

      for (const file of files) {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("upload_preset", "chatwave");
        formData.append("cloud_name", "ds6dfcnny");

        try {
          const response = await axios.post(
            "https://api.cloudinary.com/v1_1/ds6dfcnny/image/upload",
            formData
          );
          const contentUrl = response.data.secure_url;
          console.log("contenturl", contentUrl);
          const data2 = {
            token,
            chatId: currentChat?.id,
            content: contentUrl,
            isAttachment: true,
          };
          dispatch(createNewMessage(data2));

          // Store the uploaded file's URL
          uploadedFiles.push({
            id: new Date().getTime(), // Temporary ID
            content: contentUrl,
            user: { id: auth.reqUser.id },
            chat: { id: currentChat.id },
            isAttachment: true,
          });
        } catch (error) {
          console.error("Error uploading attachment: ", error);
        }
      }

      // Update the state with uploaded files
      setMessages((prevMessages) => [...prevMessages, ...uploadedFiles]);
    }
  };

  const handleGiphyOpen = () => setShowGiphySearch(true);
  const handleGiphyClose = () => setShowGiphySearch(false);
  const handleSelectGif = (url) => {
    setContent(url);
    setShowGiphySearch(false);
  };

  const handleGroupNameEdit = () => {
    setEditGroupName(true);
  };

  const handleGroupNameEditCheck = () => {
    dispatch(
      updateGroup({
        token,
        currentChatId: currentChat.id,
        groupName,
        groupImage: currentChat.chat_image,
      })
    );
    setSnackbarMessage("Group name updated successfully!");
    setEditGroupName(false);
    setOpen(true);
  };

  const handleImageUpload = async (pics) => {
    const data = new FormData();
    data.append("file", pics);
    data.append("upload_preset", "chatwave");
    data.append("cloud_name", "ds6dfcnny");

    try {
      const response = await axios.post(
        "https://api.cloudinary.com/v1_1/ds6dfcnny/image/upload",
        data
      );
      const imageUrl = response.data.secure_url;
      setSnackbarMessage("Group image updated successfully");
      setOpen(true);
      console.log("imgurl", imageUrl);
      const newData = {
        currentChatId: currentChat.id,
        token: localStorage.getItem("token"),
        groupName: currentChat.chat_name,
        groupImage: imageUrl,
      };
      dispatch(updateGroup(newData));
    } catch (error) {
      console.error("Error updating group image: ", error);
      setSnackbarMessage(`Error updating group image`);
      setOpen(true);
    }
  };

  const handleAddUserToGroup = () => {
    const newMembers = Array.from(groupMember, (user) => user.id);
    const data = {
      token,
      currentChatId: currentChat.id,
      userIdsList: newMembers,
    };
    console.log(newMembers);
    dispatch(addGroupMembers(data));
    setIsModalOpen(false);
    setGroupMember(new Set());
    setSnackbarMessage("Users added to group successfully!");
    setOpen(true);
  };

  const handleRemoveMember = (item) => {
    const updatedGroupMembers = new Set(groupMember);
    updatedGroupMembers.delete(item);
    setGroupMember(updatedGroupMembers);
  };

  const handleAddUsersClick = () => {
    setIsModalOpen(true);
  };

  console.log("token -------:", token);

  const handleRemoveGroupUser = (removeUserId) => {
    console.log("token -------:", token);
    dispatch(
      removeGroupMember({
        token,
        currentChatId: currentChat.id,
        userId: removeUserId,
      })
    );
    setSnackbarMessage("User Removed successfully!");
    setOpen(true);
    console.log("token -------:", token);
  };

  const handleExitGroup = () => {};

  const popOpen = Boolean(anchorEl);
  const id = popOpen ? "simple-popover" : undefined;

  console.log("lookout ------- ", currentChat);

  return (
    <div className="relative">
      <div className="py-14 bg-[#1271ff] w-full"></div>

      <div className="flex bg-[#f0f2f5] h-[90vh] w-[96vw] absolute top-[5vh] left-[2vw] rounded-lg shadow-blue-700 shadow-lg">
        <div className="w-[30%] h-full bg-[#e8e9ec] rounded-tl-lg rounded-bl-lg overflow-y-scroll">
          {isProfile ? (
            <div className="w-full h-full rounded-tl-lg rounded-bl-lg">
              <Profile handleProfileCloseOpen={handleProfileCloseOpen} />
            </div>
          ) : isCreateGroup ? (
            <div className="w-full h-full">
              <CreateGroup
                handleCreateGroupCloseOpen={handleCreateGroupCloseOpen}
              />
            </div>
          ) : (
            <div className="w-full h-full">
              <div className="flex justify-between items-center p-3">
                <div className="flex items-center space-x-3">
                  <img
                    className="rounded-full w-10 h-10 cursor-pointer"
                    src={
                      auth.reqUser?.profile_picture ||
                      "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                    }
                    alt="profile picture"
                    onClick={handleProfileCloseOpen}
                  />
                  <p>{auth.reqUser?.username}</p>
                </div>
                <div className="space-x-6 text-2xl flex">
                  <MdOutlineVideoCall
                    className="cursor-pointer text-2xl"
                    onClick={() => navigate("/group-room")}
                  />
                  <MdOutlineGroupAdd
                    className="cursor-pointer"
                    onClick={handleCreateGroupCloseOpen}
                  />
                  <IoMdPower
                    className="cursor-pointer text-red-600"
                    onClick={handleLogout}
                  />
                </div>
              </div>

              <div className="relative flex justify-center items-center bg-white py-4 px-3">
                <input
                  className="border-none outline-none bg-slate-200 rounded-md w-[93%] pl-10 py-2"
                  type="text"
                  placeholder="Search Users..."
                  onChange={(e) => {
                    setQuery(e.target.value);
                    handleSearch(e.target.value);
                  }}
                  value={query}
                />
                <AiOutlineSearch className="left-9 top-7 absolute" />
              </div>

              <div className="bg-white overflow-y-scroll h-full px-4">
                {query &&
                  auth.searchUser?.map((item) => (
                    <div
                      key={item.id}
                      onClick={() => {
                        createNewChat(item?.id);
                        setQuery("");
                      }}
                    >
                      <hr />
                      <ChatCard
                        isChat={false}
                        name={item.full_name}
                        userImg={
                          item.profile_picture ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                        }
                      />
                    </div>
                  ))}

                {!chat.chats?.error &&
                  chat?.chats?.map((item) => (
                    <div onClick={() => handleCurrentChat(item)} key={item.id}>
                      <hr />
                      {item.is_group ? (
                        <ChatCard
                          name={item.chat_name}
                          userImg={
                            item.chat_image ||
                            "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                        />
                      ) : (
                        <ChatCard
                          isChat={true}
                          name={
                            auth.reqUser?.id !== item.users[0]?.id
                              ? item.users[0].full_name
                              : item.users[1].full_name
                          }
                          userImg={
                            auth.reqUser.id !== item.users[0].id
                              ? item.users[0].profile_picture ||
                                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                              : item.users[1].profile_picture ||
                                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                          notification={notifications.length}
                          isNotification={
                            notifications[0]?.chat?.id === item.id
                          }
                          message={
                            (item.id ===
                              messages[messages.length - 1]?.chat?.id &&
                              messages[messages.length - 1]?.content) ||
                            (item.id === notifications[0]?.chat?.id &&
                              notifications[0]?.content)
                          }
                        />
                      )}
                    </div>
                  ))}
              </div>
            </div>
          )}
        </div>

        {!currentChat ? (
          <div className="w-[70%] flex flex-col items-center justify-center h-full">
            <div className="max-w-[70%] text-center">
              <img
                src={Logo}
                alt="chat wave logo"
                className="w-16 h-16 mx-auto my-2"
              />
              <h1 className="text-3xl font-semibold text-gray-600">
                Chat Wave
              </h1>
              <p className="my-2 text-gray-800">
                Welcome to Chat Wave, where every ripple in the ocean of
                communication brings us closer together!
              </p>
              <p className="text-gray-800">
                Select a conversation and start riding the wave of instant
                communication!
              </p>
            </div>
          </div>
        ) : (
          <div className="w-[70%] h-full relative">
            <div className="h-[10%] absolute top-0 rounded-tr-lg rounded-br-lg w-full bg-[#f0f2f5]">
              <div className="flex justify-between px-2">
                <div className="p-3 space-x-4 flex items-center">
                  <img
                    className="w-10 h-10 rounded-full"
                    src={
                      currentChat?.is_group
                        ? currentChat?.chat_image ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                        : auth.reqUser?.id !== currentChat?.users[0].id
                        ? currentChat?.users[0].profile_picture ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                        : currentChat?.users[1].profile_picture ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                    }
                    alt="user profile pic"
                  />
                  <p>
                    {currentChat?.is_group
                      ? currentChat?.chat_name
                      : auth.reqUser?.id !== currentChat?.users[0].id
                      ? currentChat?.users[0].full_name
                      : currentChat?.users[1].full_name}
                  </p>
                </div>
                <div className="p-3 space-x-5 flex items-center">
                  {isChatSearch ? (
                    <div className="relative flex justify-center items-center px-3">
                      <input
                        className="border-none outline-none bg-slate-200 rounded-md pl-20 py-1.5"
                        type="text"
                        placeholder="Search Chat..."
                        onChange={(e) => {
                          setChatSerachQuery(e.target.value);
                          handleChatHistorySearch(e.target.value);
                        }}
                        value={chatSearchQuery}
                      />
                      <BsArrowLeft
                        className="absolute left-6 cursor-pointer text-lg font-bold"
                        onClick={() => setIsChatSearch(!isChatSearch)}
                      />
                      <AiOutlineSearch className="left-14 absolute" />
                    </div>
                  ) : (
                    <AiOutlineSearch
                      className="cursor-pointer text-lg"
                      onClick={() => setIsChatSearch(!isChatSearch)}
                    />
                  )}
                  <BsThreeDotsVertical
                    className="cursor-pointer text-lg"
                    onClick={handleThreeDotsClick}
                  />
                  <Popover
                    id={id}
                    open={showPopover}
                    anchorEl={anchorEl}
                    onClose={handleViewUserClose}
                    anchorOrigin={{
                      vertical: "top",
                      horizontal: "right",
                    }}
                    transformOrigin={{
                      vertical: "top",
                      horizontal: "right",
                    }}
                  >
                    {currentChat.is_group ? (
                      <div className="w-64 h-full p-4 flex flex-col items-center space-y-4">
                        <label htmlFor="imgInput">
                          <img
                            className="w-40 h-40 rounded-full cursor-pointer"
                            src={
                              currentChat.chat_image ||
                              "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                            }
                            alt="group profile pic"
                          />
                        </label>
                        <input
                          type="file"
                          id="imgInput"
                          className="hidden"
                          onChange={(e) => {
                            if (!e.target.files) return;
                            handleImageUpload(e.target.files[0]);
                          }}
                          disabled={
                            !currentChat.admins.some(
                              (admin) => admin.id === auth.reqUser.id
                            )
                          }
                        />
                        <div className="bg-white px-3 my-6">
                          {editGroupName ? (
                            <div className="w-full flex justify-between items-center">
                              <input
                                className="w-[80%] outline-none border-b-2 border-blue-700 p-2"
                                type="text"
                                placeholder="Enter group name..."
                                onChange={(e) => setGroupName(e.target.value)}
                                value={groupName || currentChat.chat_name}
                              />
                              <BsCheck2 onClick={handleGroupNameEditCheck} />
                            </div>
                          ) : (
                            <div className="w-full flex justify-center items-center space-x-2">
                              <p className="py-3">
                                {groupName || currentChat.chat_name}
                              </p>
                              {currentChat.admins.some(
                                (admin) => admin.id === auth.reqUser.id
                              ) && <BsPencil onClick={handleGroupNameEdit} />}
                            </div>
                          )}
                        </div>
                        <div className="self-start flex  space-x-4 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Admins</p>
                          {currentChat.admins.map((admin) => (
                            <div key={admin.id}>
                              <p
                                key={admin.id}
                                className="font-semibold text-slate-700"
                              >
                                {admin.full_name}
                              </p>
                            </div>
                          ))}
                        </div>
                        <div className="self-start flex space-x-7 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Users</p>
                          <div>
                            {currentChat.users
                              .filter(
                                (user) =>
                                  !currentChat.admins.some(
                                    (admin) => admin.id === user.id
                                  )
                              )
                              .map((user) => (
                                <div
                                  key={user.id}
                                  className="flex justify-between items-center space-x-4"
                                >
                                  <p className="font-semibold text-slate-700">
                                    {user.full_name}
                                  </p>
                                  {currentChat.admins.some(
                                    (admin) => admin.id === auth.reqUser.id
                                  ) && (
                                    <BsTrash
                                      className="text-red-600"
                                      onClick={() =>
                                        handleRemoveGroupUser(user.id)
                                      }
                                    />
                                  )}
                                </div>
                              ))}
                          </div>
                        </div>
                        {currentChat.admins.some(
                          (admin) => admin.id === auth.reqUser.id
                        ) && (
                          <Button
                            sx={{ bgcolor: "#1271ff" }}
                            variant="contained"
                            onClick={handleAddUsersClick}
                          >
                            Add User
                          </Button>
                        )}
                        {/* <Button
                          color={"error"}
                          variant="contained"
                          onClick={handleExitGroup}
                        >
                          Exit Group
                        </Button> */}
                      </div>
                    ) : (
                      <div className="w-64 h-full p-4 flex flex-col items-center space-y-4">
                        <img
                          className="w-40 h-40 rounded-full"
                          src={
                            currentChat.users[0].id !== auth.reqUser.id
                              ? currentChat.users[0].profile_picture ||
                                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                              : currentChat.users[1].profile_picture ||
                                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                          alt="user profile pic"
                        />
                        <p className="text-lg font-semibold">
                          {currentChat.users[0].id !== auth.reqUser.id
                            ? currentChat.users[0].username
                            : currentChat.users[1].username}
                        </p>
                        <div className="self-start flex  space-x-4 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Name</p>
                          <p className="font-semibold text-slate-700">
                            {currentChat.users[0].id !== auth.reqUser.id
                              ? currentChat.users[0].full_name
                              : currentChat.users[1].full_name}
                          </p>
                        </div>
                        <div className="self-start flex space-x-9 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Bio</p>
                          <p className="font-semibold text-slate-700">
                            {currentChat.users[0].id !== auth.reqUser.id
                              ? currentChat.users[0].bio
                              : currentChat.users[1].bio}
                          </p>
                        </div>
                        {/* <Button
                          variant="contained"
                          color="error"
                          onClick={handleBlockUser}
                        >
                          Block
                        </Button> */}
                      </div>
                    )}
                  </Popover>
                </div>
              </div>
            </div>

            <div className="px-10 pt-2 pb-8 mt-16 mb-[4.5rem] h-[76.5%] overflow-y-scroll bg-[url('./src/assets/images/chatwave-bg.jpg')] bg-center bg-contain">
              <div className="space-y-1 flex flex-col justify-center mt-2">
                {messages.length > 0 &&
                  messages?.map((item) => (
                    <MessageCard
                      messageRef={messageRef}
                      key={item.id}
                      token={token}
                      messageId={item.id}
                      isReqUserMessage={item.user?.id !== auth.reqUser.id}
                      content={item.content}
                      messageDate={item.timeStamp?.substring(0, 10)}
                      messageTime={
                        item.timeStamp?.charAt(12) === ":"
                          ? item.timeStamp?.substring(11, 15)
                          : item.timeStamp?.substring(11, 16)
                      }
                      isAttachment={item.isAttachment}
                      handleEditMessage={handleEditMessage}
                    />
                  ))}
                <div ref={messageRef} />
              </div>
            </div>

            <div className="footer h-[13.5%] bg-[#f0f2f5] absolute bottom-0 rounded-br-lg w-full py-3 text-2xl">
              <div className="flex items-center space-x-2 px-5 relative">
                <BsEmojiSmile
                  className="cursor-pointer"
                  onClick={() => setShowEmojiPicker(!showEmojiPicker)}
                />
                {showEmojiPicker && (
                  <ClickAwayListener
                    onClickAway={() => setShowEmojiPicker(false)}
                  >
                    <Box className="absolute bottom-14 left-0 z-10">
                      <Picker onEmojiClick={onEmojiClick} />
                    </Box>
                  </ClickAwayListener>
                )}
                <ImAttachment
                  className="cursor-pointer"
                  onClick={() => fileInputRef.current.click()}
                />
                <input
                  ref={fileInputRef}
                  type="file"
                  multiple
                  className="hidden"
                  onChange={handleFileChange}
                />
                <button className="cursor-pointer" onClick={handleGiphyOpen}>
                  <HiOutlineGif size={28} />
                </button>
                <input
                  ref={inputRef}
                  className="py-2 outline-none border-none bg-white pl-4 rounded-md flex-grow mr-2"
                  type="text"
                  onChange={(e) => setContent(e.target.value)}
                  placeholder="Type message..."
                  value={content}
                  onKeyPress={(e) => {
                    if (e.key === "Enter") {
                      if (isEditMessage) handleEditMessageSend();
                      else handleCreateNewMessage();
                      setContent("");
                    }
                  }}
                />
                <button
                  className="cursor-pointer rounded-full p-2"
                  style={{ backgroundColor: "#1271ff" }}
                  onClick={() => {
                    if (isEditMessage) handleEditMessageSend();
                    else handleCreateNewMessage();
                    setContent("");
                  }}
                >
                  <IoMdSend color="white" size={24} />
                </button>
                <Snackbar
                  open={showSnackbar}
                  autoHideDuration={6000}
                  onClose={() => setShowSnackbar(false)}
                >
                  <Alert
                    onClose={() => setShowSnackbar(false)}
                    severity="error"
                  >
                    {snackbarMessage}
                  </Alert>
                </Snackbar>
              </div>
            </div>
          </div>
        )}
      </div>
      <SimpleSnackbar
        // message={`Welcome ${auth.reqUser?.full_name}`}
        message={snackbarMessage}
        open={open}
        handleClose={handleClose}
        type={"success"}
      />
      <GiphySearch
        open={showGiphySearch}
        handleClose={handleGiphyClose}
        handleSelectGif={handleSelectGif}
      />
      {/* Modal for adding users to group */}
      <Modal
        open={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        aria-labelledby="add-users-modal"
        aria-describedby="modal-to-add-users-to-group"
        className="flex justify-center items-center"
      >
        <Box
          sx={{ width: "25vw", height: "70vh", overflowY: "auto", top: 100 }}
          className="bg-white rounded-lg shadow-lg flex flex-col justify-between"
        >
          <div>
            <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5 h-[10vh]">
              <BsArrowLeft
                className="cursor-pointer text-2xl font-bold"
                onClick={() => setIsModalOpen(false)} // Close modal on arrow click
              />
              <p className="font-semibold text-xl">Add Users to Group</p>
            </div>

            <div className="flex flex-col space-y-4 items-center bg-white py-4 px-3">
              <div className="flex flex-wrap space-x-1 space-y-1">
                {groupMember.size > 0 &&
                  Array.from(groupMember).map((item) => (
                    <SelectedMember
                      key={item.id}
                      handleRemoveMember={() => handleRemoveMember(item)}
                      member={item}
                    />
                  ))}
              </div>

              <input
                className="outline-none border-b border-[#888888] p-2 w-[93%]"
                type="text"
                placeholder="Search users..."
                onChange={(e) => {
                  handleSearch(e.target.value);
                  setAddUserQuery(e.target.value);
                }}
                value={addUserQuery}
              />
            </div>

            <div className="bg-white overflow-y-auto pl-4">
              {addUserQuery &&
                auth.searchUser?.map((item) => (
                  <div
                    key={item?.id}
                    onClick={() => {
                      groupMember.add(item);
                      setGroupMember(new Set(groupMember)); // Ensure re-render by creating new Set
                      setQuery("");
                    }}
                  >
                    <hr />
                    <ChatCard
                      isChat={false}
                      name={item.full_name}
                      userImg={
                        item.profile_picture ||
                        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                      }
                    />
                  </div>
                ))}
            </div>
          </div>

          <div className="bottom-10 py-4 bg-slate-200 flex items-center justify-center">
            <div
              className="bg-blue-600 rounded-full p-4 cursor-pointer"
              onClick={() => {
                handleAddUserToGroup(); // Call the function to add users and close the modal
              }}
            >
              <BsArrowRight className="text-white font-bold text-3xl" />
            </div>
          </div>
        </Box>
      </Modal>
    </div>
  );
};

export default Dashboard;
