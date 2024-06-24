import {
  LOGIN,
  REGISTER,
  REQ_USER,
  SEARCH_USER,
  UPDATE_USER,
  VERIFY_OTP,
  FORGOT_PASSWORD,
  RESET_PASSWORD,
  DEACTIVATE_ACCOUNT,
  REACTIVATE_ACCOUNT,
} from "./ActionType";

const initialState = {
  reqUser: null,
  isAtuh: "pending",
  login: null,
};
export const authReducer = (store = initialState, { type, payload }) => {
  if (type === REGISTER) {
    return { ...store, signup: payload };
  } else if (type === LOGIN) {
    return { ...store, login: payload };
  } else if (type === REQ_USER) {
    return { ...store, reqUser: payload };
  } else if (type === SEARCH_USER) {
    return { ...store, searchUser: payload };
  } else if (type === UPDATE_USER) {
    return { ...store, updatedUser: payload };
  } else if (type === VERIFY_OTP) {
    return { ...store, verifyOtp: payload };
  } else if (type === FORGOT_PASSWORD) {
    return { ...store, forgotPassword: payload };
  } else if (type === RESET_PASSWORD) {
    return { ...store, resetPassword: payload };
  } else if (type === DEACTIVATE_ACCOUNT) {
    return { ...store, deactivateAccount: payload };
  } else if (type === REACTIVATE_ACCOUNT) {
    return { ...store, reactivateAccount: payload };
  }

  return store;
};
