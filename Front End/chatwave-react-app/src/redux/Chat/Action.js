import axios from "axios";
import { BASE_URL } from "../../config/Api";
import {
  CREATE_GROUP_CHAT,
  CREATE_SINGLE_CHAT,
  GET_ALL_CHAT,
  // GET_NOTIFICATIONS,
  BLOCK_USER,
  UNBLOCK_USER,
  EXIT_GROUP,
} from "./ActionType";

export const createSingleChat = (data) => async (dispatch) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/api/chats/single`,
      { userId: data.userId },
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("created chat ----- ", chat);
    dispatch({ type: CREATE_SINGLE_CHAT, payload: chat });
  } catch (error) {
    console.log("Create single chat error ", error);
  }
};

export const createGroupChat = (data) => async (dispatch) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/api/chats/group`,
      data.group,
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("created group chat ----- ", chat);
    dispatch({ type: CREATE_GROUP_CHAT, payload: chat });
  } catch (error) {
    console.log("Create group chat error", error);
  }
};

export const getAllChat = (token) => async (dispatch) => {
  try {
    console.log("token get chat ----- ", token);
    const response = await axios.get(`${BASE_URL}/api/chats/user`, {
      headers: {
        Authorization: token,
      },
    });
    const chats = response.data;
    console.log("get chats ----- ", chats);
    dispatch({ type: GET_ALL_CHAT, payload: chats });
  } catch (error) {
    console.error("Get all chat error", error);
  }
};

export const updateGroup = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/chats/${data.currentChatId}/rename`,
      {
        groupName: data.groupName,
        groupImage: data.groupImage,
      },
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("updated group ----- ", chat);
    dispatch({ type: CREATE_GROUP_CHAT, payload: chat });
  } catch (error) {
    console.log("Update group error", error);
  }
};

export const addGroupMembers = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/chats/${data.currentChatId}/add-users`,
      {
        userIds: data.userIdsList,
      },
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("added user(s) to group ----- ", chat);
    dispatch({ type: CREATE_GROUP_CHAT, payload: chat });
  } catch (error) {
    console.log("adding user to group error", error);
  }
};

export const removeGroupMember = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/chats/${data.currentChatId}/remove/${data.userId}`,
      {},
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("removed user from group ----- ", chat);
    dispatch({ type: CREATE_GROUP_CHAT, payload: chat });
  } catch (error) {
    console.log("remove user from group error", error);
  }
};

export const blockUser = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/chats/${data.currentChatId}/block`,
      {},
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data; // Ensure this includes the `blocked` and `blocked_by` properties
    console.log(
      "blocked user --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ",
      chat
    );
    dispatch({ type: BLOCK_USER, payload: chat }); // Dispatch the updated chat object
  } catch (error) {
    console.log("Error blocking user", error);
  }
};

export const unblockUser = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/chats/${data.currentChatId}/unblock`,
      {},
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("unblocked user ----- ", chat);
    dispatch({ type: UNBLOCK_USER, payload: chat });
  } catch (error) {
    console.log("Error unblocking user", error);
  }
};

export const exitGroup = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/chats/${data.currentChatId}/exit`,
      {},
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const chat = response.data;
    console.log("exit from group success ----- ", chat);
    dispatch({ type: EXIT_GROUP, payload: chat });
  } catch (error) {
    console.log("Error exiting group", error);
  }
};

// export const getNotifications = (data) => async (dispatch) => {
//   try {
//     const response = await axios.get(
//       `${BASE_URL}/notifications/${data.userId}`,
//       {
//         headers: { Authorization: data.token },
//       }
//     );
//     const chat = response.data;
//     console.log("Notifications received  ----- ", chat);
//     dispatch({ type: GET_NOTIFICATIONS, payload: chat });
//   } catch (error) {
//     console.error("Error receiving notifications", error);
//   }
// };
