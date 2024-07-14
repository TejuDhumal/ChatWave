import React, { useState, useEffect, useRef } from "react";
import { AiOutlineSearch } from "react-icons/ai";
import { MdOutlineGroupAdd } from "react-icons/md";
import { AiOutlineVideoCameraAdd } from "react-icons/ai";
import { HiOutlineGif } from "react-icons/hi2";
import { IoMdSend, IoMdArrowBack } from "react-icons/io";
import { TbLogout } from "react-icons/tb";
import { VscRobot } from "react-icons/vsc";
import {
  BsArrowLeft,
  BsEmojiSmile,
  BsThreeDotsVertical,
  BsPencil,
  BsCheck2,
  BsTrash,
  BsArrowRight,
  BsThreeDots,
} from "react-icons/bs";
import { RiImageAddFill } from "react-icons/ri";
import {
  Popover,
  Button,
  Box,
  ClickAwayListener,
  Snackbar,
  Alert,
  Modal,
  Tooltip,
  Menu,
  MenuItem,
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
  blockUser,
  unblockUser,
  exitGroup,
} from "../redux/Chat/Action";
import {
  createNewMessage,
  getAllMessage,
  editMessage,
  deleteMessage,
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
import Logo from "/public/logo.png";
import { BASE_URL } from "../config/Api";
import axios from "axios";
import io from "socket.io-client";

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
  const [updateFlag, setUpdateFlag] = useState(false); // To trigger re-fetch of all chats
  const [currentChatData, setCurrentChatData] = useState(currentChat); // To refresh current chat
  const [lastRenderedDate, setLastRenderedDate] = useState(null);
  const [isLeftPaneVisible, setIsLeftPaneVisible] = useState(true); // State to control left pane visibility
  const [menuAnchorEl, setMenuAnchorEl] = useState(null); // State for triple dots menu

  const messageRef = useRef();
  const inputRef = useRef(null);
  const fileInputRef = useRef(null);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { auth, chat, message } = useSelector((store) => store);
  const [showChatBubble, setShowChatBubble] = useState(false);
  const token = localStorage.getItem("token");
  const socket = useRef();

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
    fetchNotifications();
  }, [currentChat, token]);

  useEffect(() => {
    if (Array.isArray(message.messages)) {
      setMessages(message.messages);
    }
  }, [message.messages]);

  useEffect(() => {
    if (messageRef.current) {
      messageRef.current.scrollIntoView();
    }
  }, [messages]);

  // Set up socket.io connection
  useEffect(() => {
    console.log("Initializing WebSocket connection...");
    socket.current = io(`${BASE_URL}`, {
      path: "/socket.io",
      transports: ["websocket"],
      query: {
        token: token,
      },
    });

    socket.current.on("connect", () => {
      console.log("Connected to WebSocket server");
    });

    socket.current.on("new message", (newMessage) => {
      if (newMessage.chat.id === currentChat?.id) {
        setMessages((prevMessages) => {
          // Avoid duplicate messages by checking if the message already exists
          if (!prevMessages.some((msg) => msg.id === newMessage.id)) {
            return [...prevMessages, newMessage];
          }
          return prevMessages;
        });
      }
    });

    // Listen for user blocked event
    socket.current.on("user blocked", (data) => {
      if (data.chatId === currentChat?.id) {
        // Update the current chat data to reflect the block status
        fetchUpdatedCurrentChat(data.chatId);
      }
    });

    // Listen for user blocked event
    socket.current.on("user blocked", (data) => {
      if (data.chatId === currentChat?.id) {
        // Update the current chat data to reflect the block status
        fetchUpdatedCurrentChat(data.chatId);
        if (data.blockedBy !== auth.reqUser.id) {
          // Update state to show blocked message if the current user is the receiver
          setSnackbarMessage("You have been blocked.");
          setOpen(true);
        }
      }
    });

    // Listen for user unblocked event
    socket.current.on("user unblocked", (data) => {
      if (data.chatId === currentChat?.id) {
        // Update the current chat data to reflect the unblock status
        fetchUpdatedCurrentChat(data.chatId);
        if (data.unblockedBy !== auth.reqUser.id) {
          // Update state to remove blocked message if the current user is the receiver
          setSnackbarMessage("You have been unblocked.");
          setOpen(true);
        }
      }
    });

    socket.current.on("connect_error", (error) => {
      console.error("Connection Error: ", error);
    });

    socket.current.on("error", (error) => {
      console.error("Socket Error: ", error);
    });

    return () => {
      console.log("Disconnecting WebSocket...");
      socket.current.off("user blocked");
      socket.current.off("user unblocked");
      socket.current.disconnect();
    };
  }, [currentChat]);

  const sendMessage = (message) => {
    console.log("Sending message:", message);
    socket.current.emit("new message", message);
  };

  useEffect(() => {
    if (currentChat) {
      console.log("Joining chat:", currentChat.id);
      socket.current.emit("join chat", currentChat.id);

      socket.current.on("message received", (newMessage) => {
        console.log("Message received:", newMessage);
        if (newMessage.chat.id === currentChat.id) {
          setMessages((prevMessages) => [...prevMessages, newMessage]);
        }
      });
    }

    return () => {
      if (currentChat) {
        console.log("Leaving chat:", currentChat.id);
        socket.current.emit("leave chat", currentChat.id);
        socket.current.removeAllListeners("message received");
      }
    };
  }, [currentChat]);

  // Polling for new messages and notifications every 5 seconds
  useEffect(() => {
    const pollMessagesAndNotifications = () => {
      if (currentChat) {
        dispatch(getAllMessage({ chatId: currentChat.id, token }));
        fetchUpdatedCurrentChat(currentChat.id);
      }

      // Fetch notifications only if the current user is the receiver in the chat
      fetchNotifications();
    };

    const interval = setInterval(pollMessagesAndNotifications, 5000);

    // Cleanup interval on component unmount
    return () => clearInterval(interval);
  }, [currentChat, token]);

  useEffect(() => {
    // Reset lastRenderedDate when messages change
    setLastRenderedDate(null);
  }, [messages]);

  const handleCurrentChat = (item) => {
    setCurrentChat(item);
    setCurrentChatData(item);
    if (messageRef.current) {
      messageRef.current.scrollIntoView();
    }
    // Mark notifications as read for this chat
    markNotificationsAsRead(item.id);
    setIsLeftPaneVisible(false); // Hide the left pane when a chat is selected
  };

  const createNewChat = (userId) => {
    const data = { token, userId };
    if (token) dispatch(createSingleChat(data));
  };

  useEffect(() => {
    if (token) dispatch(getAllChat(token));
  }, [token, chat.singleChat, chat.createdGroup, updateFlag]);

  const handleCreateNewMessage = (url) => {
    const messageContent = url || content;
    if (!messageContent) return; // Ensure content is not empty

    setContent(""); // Clear the input field immediately

    const data = { token, chatId: currentChat?.id, content: messageContent };

    // Send the message to the server
    dispatch(createNewMessage(data)).then(() => {
      // Dispatch getAllMessage to refresh the chat from the server
      dispatch(getAllMessage({ chatId: currentChat.id, token }));

      // Emit the new message through the socket (the server will broadcast it back)
      socket.current.emit("new message", {
        id: new Date().getTime(), // Temporary ID
        content: messageContent,
        user: { id: auth.reqUser.id },
        chat: { id: currentChat.id },
      });

      // Prepare notifications for group chat or direct message
      if (currentChat && currentChat.users) {
        currentChat.users.forEach((user) => {
          if (user.id !== auth.reqUser.id) {
            // Post a notification to the API for each user except the current user
            axios
              .post(
                `${BASE_URL}/notifications/send`,
                {
                  userId: user.id,
                  message: `New message from ${auth.reqUser.username}`,
                  chatId: currentChat.id,
                },
                { headers: { Authorization: token } }
              )
              .catch((error) => {
                console.error(
                  `Failed to send notification to user ${user.id}:`,
                  error
                );
              });
          }
        });
      }
    });
  };

  const handleSearch = (keyword) => {
    dispatch(searchUser({ userId: auth.reqUser?.id, keyword, token }));
  };

  const handleProfileCloseOpen = () => {
    setIsProfile(!isProfile);
  };

  const handleCreateGroupCloseOpen = () => {
    setIsCreateGroup(!isCreateGroup);
  };

  const handleEditMessage = (messageContent, messageId) => {
    // Remove "Edit: " prefix if it exists
    const cleanContent = messageContent.startsWith("Edit: ")
      ? messageContent.replace(/^Edit:\s*/, "")
      : messageContent;

    setContent(cleanContent); // Set the cleaned content
    setMessageId(messageId); // Set the message ID
    setIsEditMessage(true); // Enter edit mode

    // Optionally, focus the input to allow immediate editing
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };

  const handleEditMessageSend = () => {
    // Add "Edit: " prefix only if it's not already present
    const prefixedContent = content.startsWith("Edit: ")
      ? content
      : "Edit: " + content;

    const data = { token, content: prefixedContent, messageId };

    dispatch(editMessage(data)).then(() => {
      setIsEditMessage(false); // Exit edit mode
      setMessageId(null); // Clear the message ID
      dispatch(getAllMessage({ chatId: currentChat.id, token })); // Refresh messages
    });

    setContent(""); // Clear the input field
  };

  // Function to delete a message
  const handleDeleteMessage = (messageId) => {
    dispatch(deleteMessage({ token, messageId })).then(() => {
      // Trigger polling immediately after deleting a message
      dispatch(getAllMessage({ chatId: currentChat.id, token }));
    });
  };

  const handleThreeDotsClick = (event) => {
    setAnchorEl(event.currentTarget);
    setShowPopover(true);
  };

  const handleBlockUser = () => {
    setShowPopover(false);
    const data = {
      token,
      currentChatId: currentChat.id,
    };
    dispatch(blockUser(data)).then((res) => {
      setSnackbarMessage("User Blocked Successfully!");
      setOpen(true);
      // Emit block event to the WebSocket
      socket.current.emit("user blocked", {
        chatId: currentChat.id,
        blockedBy: auth.reqUser.id,
      });
      // Fetch the latest chat data to update the state
      fetchUpdatedCurrentChat(currentChat.id);
    });
  };

  const handleUnblockUser = () => {
    const data = {
      token,
      currentChatId: currentChat.id,
    };
    dispatch(unblockUser(data)).then((res) => {
      setSnackbarMessage("User Unblocked Successfully!");
      setOpen(true);
      // Emit unblock event to the WebSocket
      socket.current.emit("user unblocked", {
        chatId: currentChat.id,
        unblockedBy: auth.reqUser.id,
      });
      // Fetch the latest chat data to update the state
      fetchUpdatedCurrentChat(currentChat.id);
    });
  };

  // Helper function to fetch updated chat data
  const fetchUpdatedCurrentChat = (chatId) => {
    axios
      .get(`${BASE_URL}/api/chats/${chatId}`, {
        headers: { Authorization: token },
      })
      .then((response) => {
        setCurrentChatData(response.data);
      })
      .catch((error) => {
        console.error("Error fetching updated chat data", error);
      });
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

      // Emit the uploaded files to the socket server
      uploadedFiles.forEach((file) => {
        socket.current.emit("new message", file);
      });
    }
  };

  const handleGiphyOpen = () => setShowGiphySearch(true);
  const handleGiphyClose = () => setShowGiphySearch(false);
  const handleSelectGif = (url) => {
    handleCreateNewMessage(url);
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
      setSnackbarMessage("Error updating group image");
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

  const handleRemoveGroupUser = (removeUserId) => {
    dispatch(
      removeGroupMember({
        token,
        currentChatId: currentChat.id,
        userId: removeUserId,
      })
    );
    setSnackbarMessage("User Removed successfully!");
    setOpen(true);
  };

  const handleExitGroup = () => {
    setShowPopover(false);
    const data = {
      token,
      currentChatId: currentChat.id,
    };
    dispatch(exitGroup(data)).then(() => {
      setSnackbarMessage("Exited Group Successfully!");
      setOpen(true);
      setUpdateFlag(!updateFlag); // Toggle updateFlag to trigger re-fetch of all chats
      setCurrentChat(null); // Clear the current chat to reflect that the user has exited
      setCurrentChatData(null); // Clear currentChatData
    });
  };

  const fetchNotifications = async () => {
    if (!token) return;

    try {
      const response = await axios.get(`${BASE_URL}/notifications/retrieve`, {
        headers: { Authorization: token },
      });

      // Assuming the response contains all notifications
      const allNotifications = response.data;

      // Filter notifications to show only those meant for the current user
      const userNotifications = allNotifications.filter(
        (notification) => notification.userId === auth.reqUser.id // Only keep notifications where the receiver is the current user
      );

      setNotifications((prevNotifications) => {
        // Append new notifications to the existing state
        const newNotifications = userNotifications.filter(
          (notif) =>
            !prevNotifications.some((prevNotif) => prevNotif.id === notif.id) // Avoid duplicates
        );
        return [...prevNotifications, ...newNotifications];
      });
    } catch (error) {
      console.error("Error fetching notifications", error);
    }
  };

  const markNotificationsAsRead = (chatId) => {
    setNotifications((prevNotifications) =>
      prevNotifications.filter(
        (notif) =>
          !(notif.chatId === chatId && notif.userId === auth.reqUser.id)
      )
    );
  };

  const handleMenuClick = (event) => {
    setMenuAnchorEl(event.currentTarget);
  };

  const handleMenuClose = () => {
    setMenuAnchorEl(null);
  };

  const handleMenuSelect = (option) => {
    switch (option) {
      case "emoji":
        setShowEmojiPicker(!showEmojiPicker);
        break;
      case "image":
        fileInputRef.current.click();
        break;
      case "gif":
        handleGiphyOpen();
        break;
      default:
        break;
    }
    setMenuAnchorEl(null); // Close menu after selection
  };

  const popOpen = Boolean(anchorEl);
  const id = popOpen ? "simple-popover" : undefined;

  useEffect(() => {
    if (showChatBubble) {
      const script1 = document.createElement("script");
      script1.innerHTML = `
        window.embeddedChatbotConfig = {
          chatbotId: "mxDBEDjLAyTOXgc2uQWqC",
          domain: "www.chatbase.co"
        }
      `;
      document.body.appendChild(script1);

      const script2 = document.createElement("script");
      script2.src = "https://www.chatbase.co/embed.min.js";
      script2.setAttribute("chatbotId", "mxDBEDjLAyTOXgc2uQWqC");
      script2.setAttribute("domain", "www.chatbase.co");
      script2.defer = true;
      document.body.appendChild(script2);

      return () => {
        document.body.removeChild(script1);
        document.body.removeChild(script2);
      };
    }
  }, [showChatBubble]);

  return (
    <div className="relative h-[100vh]">
      <div className="py-14 bg-[#1271ff] w-full"></div>

      <div className="flex flex-col md:flex-row bg-[#f0f2f5] h-[90vh] w-[96vw] absolute top-[5vh] left-[2vw] rounded-lg shadow-blue-700 shadow-lg">
        {/* Left Part */}
        <div
          className={`left ${
            isLeftPaneVisible ? "block" : "hidden"
          } md:w-[30%] md:block h-full bg-[#e8e9ec] rounded-lg rounded-tl-lg rounded-bl-lg overflow-y-scroll transition-all duration-300 ease-in-out transform md:translate-x-0 ${
            isLeftPaneVisible ? "translate-x-0" : "-translate-x-full"
          }`}
        >
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
                  <Tooltip title="View Profile" placement="bottom">
                    <img
                      className="rounded-full w-10 h-10 cursor-pointer"
                      src={
                        auth.reqUser?.profile_picture ||
                        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                      }
                      alt="profile picture"
                      onClick={handleProfileCloseOpen}
                    />
                  </Tooltip>
                  <p className="hidden md:block">{auth.reqUser?.full_name}</p>
                </div>
                <div className="space-x-3 text-2xl flex">
                  <Tooltip title="Start a Video Discussion" placement="bottom">
                    <div
                      onClick={() => navigate("/group-room")}
                      className="cursor-pointer text-2xl"
                    >
                      <AiOutlineVideoCameraAdd />
                    </div>
                  </Tooltip>
                  <Tooltip title="Toggle Chatbot" placement="bottom">
                    <div
                      onClick={() => setShowChatBubble(!showChatBubble)}
                      className="cursor-pointer"
                    >
                      <VscRobot />
                    </div>
                  </Tooltip>
                  <Tooltip title="Create Group" placement="bottom">
                    <div
                      onClick={handleCreateGroupCloseOpen}
                      className="cursor-pointer"
                    >
                      <MdOutlineGroupAdd />
                    </div>
                  </Tooltip>
                  <Tooltip title="Logout" placement="bottom">
                    <div className="cursor-pointer text-red-600">
                      <TbLogout onClick={handleLogout} />
                    </div>
                  </Tooltip>
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
                  chat?.chats?.map((item) => {
                    const chatNotifications = notifications.filter(
                      (notif) => notif.chatId === item.id
                    );
                    return (
                      <div
                        onClick={() => handleCurrentChat(item)}
                        key={item.id}
                      >
                        <hr />
                        {item.is_group ? (
                          <ChatCard
                            name={item.chat_name}
                            userImg={
                              item.chat_image ||
                              "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                            }
                            isNotification={chatNotifications.length > 0}
                            message={
                              (item.id === messages[0]?.chat?.id &&
                                messages[0]?.content) ||
                              (item.id === chatNotifications[0]?.chatId &&
                                chatNotifications[0]?.message)
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
                            isNotification={chatNotifications.length > 0}
                            message={
                              (item.id === messages[0]?.chat?.id &&
                                messages[0]?.content) ||
                              (item.id === chatNotifications[0]?.chatId &&
                                chatNotifications[0]?.message)
                            }
                          />
                        )}
                      </div>
                    );
                  })}
              </div>
            </div>
          )}
        </div>

        {/* Right Part */}
        <div
          className={`right w-full md:w-[70%] flex flex-col items-center justify-center h-full ${
            isLeftPaneVisible ? "hidden" : "block"
          } md:block`}
        >
          {!currentChatData ? (
            <div className="flex flex-col items-center justify-center h-full">
              <div className="text-center px-4">
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
            <div className="w-full h-full flex flex-col">
              {/* Header Section */}
              <div className="mt-3 -mb-[3.25rem] rounded-tr-lg rounded-br-lg w-full bg-[#f0f2f5] flex justify-between items-center px-4">
                <div className="flex items-center">
                  {/* Back button for mobile view */}
                  <Tooltip title="Back to chats" placement="bottom">
                    <div>
                      <IoMdArrowBack
                        className="md:hidden cursor-pointer text-2xl mr-4"
                        onClick={() => setIsLeftPaneVisible(true)}
                      />
                    </div>
                  </Tooltip>

                  <img
                    className="w-10 h-10 rounded-full"
                    src={
                      currentChatData?.is_group
                        ? currentChatData?.chat_image ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                        : auth.reqUser?.id !== currentChatData?.users[0].id
                        ? currentChatData?.users[0].profile_picture ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                        : currentChatData?.users[1].profile_picture ||
                          "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                    }
                    alt="user profile pic"
                  />
                  <p className="ml-2 text-sm md:text-base">
                    {currentChatData?.is_group
                      ? currentChatData?.chat_name
                      : auth.reqUser?.id !== currentChatData?.users[0].id
                      ? currentChatData?.users[0].full_name
                      : currentChatData?.users[1].full_name}
                  </p>
                </div>
                <div className="space-x-5 flex items-center">
                  <Tooltip title="View Details" placement="bottom">
                    <div>
                      <BsThreeDotsVertical
                        className="cursor-pointer text-lg"
                        onClick={handleThreeDotsClick}
                      />
                    </div>
                  </Tooltip>
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
                    {currentChatData.is_group ? (
                      <div className="w-64 h-full p-4 flex flex-col items-center space-y-4">
                        <label htmlFor="imgInput">
                          <img
                            className="w-32 h-32 md:w-40 md:h-40 rounded-full cursor-pointer"
                            src={
                              currentChatData.chat_image ||
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
                            !currentChatData.admins.some(
                              (admin) => admin.id === auth.reqUser.id
                            )
                          }
                        />
                        <div className="bg-white px-3 my-6 w-full">
                          {editGroupName ? (
                            <div className="w-full flex justify-between items-center">
                              <input
                                className="w-[80%] outline-none border-b-2 border-blue-700 p-2"
                                type="text"
                                placeholder="Enter group name..."
                                onChange={(e) => setGroupName(e.target.value)}
                                value={groupName || currentChatData.chat_name}
                              />
                              <BsCheck2 onClick={handleGroupNameEditCheck} />
                            </div>
                          ) : (
                            <div className="w-full flex justify-center items-center space-x-2">
                              <p className="py-3 text-sm md:text-base">
                                {groupName || currentChatData.chat_name}
                              </p>
                              {currentChatData.admins.some(
                                (admin) => admin.id === auth.reqUser.id
                              ) && <BsPencil onClick={handleGroupNameEdit} />}
                            </div>
                          )}
                        </div>
                        <div className="self-start flex space-x-4 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Admins</p>
                          {currentChatData.admins.map((admin) => (
                            <div key={admin.id}>
                              <p
                                key={admin.id}
                                className="font-semibold text-slate-700 text-sm md:text-base"
                              >
                                {admin.full_name}
                              </p>
                            </div>
                          ))}
                        </div>
                        <div className="self-start flex space-x-7 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Users</p>
                          <div>
                            {currentChatData.users
                              .filter(
                                (user) =>
                                  !currentChatData.admins.some(
                                    (admin) => admin.id === user.id
                                  )
                              )
                              .map((user) => (
                                <div
                                  key={user.id}
                                  className="flex justify-between items-center space-x-4"
                                >
                                  <p className="font-semibold text-slate-700 text-sm md:text-base">
                                    {user.full_name}
                                  </p>
                                  {currentChatData.admins.some(
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
                        {currentChatData.admins.some(
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
                        <Button
                          color={"error"}
                          variant="contained"
                          onClick={handleExitGroup}
                        >
                          Exit Group
                        </Button>
                      </div>
                    ) : (
                      <div className="w-64 h-full p-4 flex flex-col items-center space-y-4">
                        <img
                          className="w-32 h-32 md:w-40 md:h-40 rounded-full"
                          src={
                            currentChatData.users[0].id !== auth.reqUser.id
                              ? currentChatData.users[0].profile_picture ||
                                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                              : currentChatData.users[1].profile_picture ||
                                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                          }
                          alt="user profile pic"
                        />
                        <p className="text-lg font-semibold md:text-base">
                          {currentChatData.users[0].id !== auth.reqUser.id
                            ? currentChatData.users[0].username
                            : currentChatData.users[1].username}
                        </p>
                        <div className="self-start flex space-x-4 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Name</p>
                          <p className="font-semibold text-slate-700 text-sm md:text-base">
                            {currentChatData.users[0].id !== auth.reqUser.id
                              ? currentChatData.users[0].full_name
                              : currentChatData.users[1].full_name}
                          </p>
                        </div>
                        <div className="self-start flex space-x-9 bg-gray-200 py-2 w-full px-2">
                          <p className="font-semibold text-blue-700">Bio</p>
                          <p className="font-semibold text-slate-700 text-sm md:text-base">
                            {currentChatData.users[0].id !== auth.reqUser.id
                              ? currentChatData.users[0].bio
                              : currentChatData.users[1].bio}
                          </p>
                        </div>
                        {!currentChatData.blocked && (
                          <Button
                            variant="contained"
                            color="error"
                            onClick={handleBlockUser}
                          >
                            Block
                          </Button>
                        )}
                      </div>
                    )}
                  </Popover>
                </div>
              </div>

              {/* Messages Section */}
              <div className="px-4 md:px-10 pt-2 pb-8 mt-16 md:h-[100%] overflow-y-scroll bg-[url('/chatwave-bg.jpg')] bg-center bg-contain">
                <div className="space-y-1 flex flex-col justify-center mt-2">
                  {messages.length > 0 &&
                    [...messages].reverse().map((item, index) => {
                      const currentMessageDate = item.timeStamp?.substring(
                        0,
                        10
                      );
                      const previousMessageDate =
                        index > 0
                          ? messages[
                              messages.length - index
                            ]?.timeStamp?.substring(0, 10)
                          : null;

                      const showDateHeader =
                        index === 0 ||
                        currentMessageDate !== previousMessageDate;

                      return (
                        <MessageCard
                          key={item.id}
                          messageRef={messageRef}
                          token={token}
                          messageId={item.id}
                          isReqUserMessage={item.user?.id !== auth.reqUser.id}
                          content={item.content}
                          messageDate={currentMessageDate}
                          messageTime={
                            item.timeStamp?.charAt(12) === ":"
                              ? item.timeStamp?.substring(11, 15)
                              : item.timeStamp?.substring(11, 16)
                          }
                          isGroup={currentChatData?.is_group}
                          sender={item.user}
                          showDateHeader={showDateHeader}
                          handleEditMessage={handleEditMessage} // Pass down the edit handler
                        />
                      );
                    })}
                  <div ref={messageRef} />
                </div>
              </div>

              {/* Footer Section */}
              <div className="footer bg-[#f0f2f5] rounded-br-lg w-full py-3 flex justify-center items-center text-2xl">
                {currentChatData?.blocked &&
                currentChatData?.blocked_by !== auth.reqUser.id ? (
                  <div className="flex flex-col justify-center items-center mt-2">
                    <p>You have been blocked.</p>
                  </div>
                ) : currentChatData?.blocked_by === auth.reqUser.id ? (
                  <div className="flex flex-col justify-center items-center space-y-2 -mt-2">
                    <p>You have blocked this user.</p>
                    <button
                      onClick={handleUnblockUser}
                      className="bg-[#1271ff] hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-lg focus:outline-none focus:shadow-outline text-sm md:text-lg"
                    >
                      Unblock
                    </button>
                  </div>
                ) : (
                  <div className="flex items-center space-x-2 px-5 w-full">
                    {/* Triple dots menu for small screens */}
                    <div className="relative md:hidden">
                      <Tooltip title="Options" placement="bottom">
                        <div>
                          <BsThreeDots
                            className="cursor-pointer md:hidden"
                            onClick={handleMenuClick}
                          />
                        </div>
                      </Tooltip>
                      <Menu
                        anchorEl={menuAnchorEl}
                        open={Boolean(menuAnchorEl)}
                        onClose={handleMenuClose}
                      >
                        <MenuItem onClick={() => handleMenuSelect("emoji")}>
                          <BsEmojiSmile className="mr-2" />
                          Emojis
                        </MenuItem>
                        <MenuItem onClick={() => handleMenuSelect("image")}>
                          <RiImageAddFill className="mr-2" />
                          Upload Image
                        </MenuItem>
                        <MenuItem onClick={() => handleMenuSelect("gif")}>
                          <HiOutlineGif className="mr-2" />
                          Send Gif
                        </MenuItem>
                      </Menu>
                      {showEmojiPicker && (
                        <ClickAwayListener
                          onClickAway={() => setShowEmojiPicker(false)}
                        >
                          <Box className="absolute bottom-14 left-0 z-10">
                            <Picker width="250px" onEmojiClick={onEmojiClick} />
                          </Box>
                        </ClickAwayListener>
                      )}
                    </div>
                    {/* Show icons for larger screens */}
                    <div className="hidden md:flex space-x-2">
                      <Tooltip title="Emojis" placement="bottom">
                        <div>
                          <BsEmojiSmile
                            className="cursor-pointer"
                            onClick={() => setShowEmojiPicker(!showEmojiPicker)}
                          />
                          {showEmojiPicker && (
                            <ClickAwayListener
                              onClickAway={() => setShowEmojiPicker(false)}
                            >
                              <Box className="absolute bottom-14 left-90 z-10">
                                <Picker onEmojiClick={onEmojiClick} />
                              </Box>
                            </ClickAwayListener>
                          )}
                        </div>
                      </Tooltip>
                      <Tooltip title="Upload Images" placement="bottom">
                        <div>
                          <RiImageAddFill
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
                        </div>
                      </Tooltip>
                      <Tooltip title="Send Gif" placement="bottom">
                        <button
                          className="cursor-pointer"
                          onClick={handleGiphyOpen}
                        >
                          <HiOutlineGif size={28} />
                        </button>
                      </Tooltip>
                    </div>
                    <input
                      ref={inputRef}
                      className="py-2 outline-none border-none bg-white pl-4 rounded-md flex-grow mr-2 min-w-0"
                      type="text"
                      onChange={(e) => setContent(e.target.value)}
                      placeholder="Type message..."
                      value={content}
                      onKeyPress={(e) => {
                        if (e.key === "Enter") {
                          isEditMessage
                            ? handleEditMessageSend()
                            : handleCreateNewMessage();
                          e.target.value = "";
                        }
                      }}
                    />
                    <Tooltip title="Send" placement="bottom">
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
                    </Tooltip>
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
                )}
              </div>
            </div>
          )}
        </div>
      </div>
      <SimpleSnackbar
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
          sx={{
            width: { xs: "80%", sm: "40%", md: "30%" },
            height: "70vh",
            overflowY: "auto",
            top: 100,
          }}
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
