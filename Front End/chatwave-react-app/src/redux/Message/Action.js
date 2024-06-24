import axios from "axios";
import { BASE_URL } from "../../config/Api";
import {
  CREATE_NEW_MESSAGE,
  GET_ALL_MESSAGE,
  DELETE_MESSAGE,
  EDIT_MESSAGE,
} from "./ActionType";

// Create new message
export const createNewMessage = (data) => async (dispatch) => {
  try {
    const newMessage = await axios.post(
      `${BASE_URL}/api/messages/create`,
      {
        content: data.content,
        chatId: data.chatId,
      },
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    console.log("new messages - ", newMessage.data);
    dispatch({ type: CREATE_NEW_MESSAGE, payload: newMessage.data });
    return newMessage.data;
  } catch (error) {
    console.error("Create new message error", error);
  }
};

// Get all messages
export const getAllMessage = (data) => async (dispatch) => {
  try {
    const response = await axios.get(
      `${BASE_URL}/api/messages/chat/${data.chatId}`,
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const messages = response.data;
    dispatch({ type: GET_ALL_MESSAGE, payload: messages });
  } catch (error) {
    console.error("Get all message error", error);
  }
};

// Delete message
export const deleteMessage = (data) => async (dispatch) => {
  try {
    const response = await axios.delete(
      `${BASE_URL}/api/messages/${data.messageId}`,
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    if (response.status === 200) {
      dispatch({
        type: DELETE_MESSAGE,
        payload: { messageId: data.messageId },
      });
    }
  } catch (error) {
    console.error("Delete message error", error);
  }
};

// Edit message
export const editMessage = (data) => async (dispatch) => {
  try {
    const response = await axios.put(
      `${BASE_URL}/api/messages/${data.messageId}`,
      {
        newContent: data.content,
      },
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const updatedMessage = response.data;
    dispatch({ type: EDIT_MESSAGE, payload: updatedMessage });
    return updatedMessage;
  } catch (error) {
    console.error("Edit message error", error);
  }
};
