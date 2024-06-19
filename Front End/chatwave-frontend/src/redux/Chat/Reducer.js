import {
  CREATE_GROUP_CHAT,
  CREATE_SINGLE_CHAT,
  GET_ALL_CHAT,
} from "./ActionType";

const initialState = {
  chats: null,
  createdGroup: null,
};

export const chatReducer = (store = initialState, { type, payload }) => {
  if (type === CREATE_SINGLE_CHAT) {
    return { ...store, singleChat: payload };
  } else if (type === GET_ALL_CHAT) {
    return { ...store, chats: payload };
  } else if (type === CREATE_GROUP_CHAT) {
    return { ...store, createdGroup: payload };
  }

  return store;
};
