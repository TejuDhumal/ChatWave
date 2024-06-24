import {
  CREATE_NEW_MESSAGE,
  GET_ALL_MESSAGE,
  DELETE_MESSAGE,
  EDIT_MESSAGE,
} from "./ActionType";

const initialState = {
  messages: null,
  newMessage: null,
};
export const messageReducer = (store = initialState, { type, payload }) => {
  if (type === CREATE_NEW_MESSAGE) {
    return { ...store, newMessage: payload };
  } else if (type === GET_ALL_MESSAGE) {
    return { ...store, messages: payload };
  } else if (type === DELETE_MESSAGE) {
    return {
      ...store,
      messages: store.messages.map((message) =>
        message._id === payload.messageId
          ? { ...message, content: "message deleted" }
          : message
      ),
    };
  } else if (type === EDIT_MESSAGE) {
    return { ...store, messages: payload };
  }

  return store;
};
