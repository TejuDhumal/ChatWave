import {
  CREATE_GROUP_CHAT,
  CREATE_SINGLE_CHAT,
  GET_ALL_CHAT,
  // GET_NOTIFICATIONS,
  BLOCK_USER,
  UNBLOCK_USER,
  EXIT_GROUP,
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
  } else if (type === BLOCK_USER) {
    return {
      ...store,
      chats: store.chats.map((chat) =>
        chat.id === payload.id ? payload : chat
      ),
    };
  } else if (type === UNBLOCK_USER) {
    return {
      ...store,
      chats: store.chats.map((chat) =>
        chat.id === payload.id ? payload : chat
      ),
    };
  } else if (type === EXIT_GROUP) {
    return {
      ...store,
      chats: store.chats.map((chat) =>
        chat.id === payload.id ? payload : chat
      ),
    };
  }
  // else if (type === GET_NOTIFICATIONS) {
  //   return { ...store, chats: payload };
  // }

  return store;
};
