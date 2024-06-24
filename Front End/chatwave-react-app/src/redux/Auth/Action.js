import axios from "axios";
import { BASE_URL } from "../../config/Api";
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

export const register = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/signup`, data);
    if (response.data.error) {
      console.log("error", response.data.error);
      return { message: response.data.error, success: false };
    }
    const user = response.data;
    console.log("register", user);
    dispatch({ type: REGISTER, payload: user });
    return {
      message: "Registration successful. Please verify your email.",
      success: true,
    };
  } catch (error) {
    console.error("Registration error", error);
    return {
      message: error.response?.data?.message || "Registration failed",
      success: false,
    };
  }
};

export const verifyOtp = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/verifyOtp`, data);
    const otp = response.data;
    console.log("verify otp", otp);
    dispatch({ type: VERIFY_OTP, payload: otp });
    return { message: "OTP verified successfully", success: true };
  } catch (error) {
    console.error("OTP verification error", error);
    return {
      message: error.response?.data?.message || "OTP verification failed",
      success: false,
    };
  }
};

export const forgotPassword = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/forgetPassword`, data);
    if (response.data.error) {
      console.log("error", response.data.error);
      return { message: response.data.error, success: false };
    }
    const user = response.data;
    if (user.jwt) localStorage.setItem("token", user.jwt);
    dispatch({ type: FORGOT_PASSWORD, payload: user });
    return { success: true };
  } catch (error) {
    console.error("Forgot Password error", error);
    return {
      message: error.response?.data?.message || "Forgot Password failed",
      success: false,
    };
  }
};
export const resetPassword = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/resetPassword`, data);
    if (response.data.error) {
      console.log("error", response.data.error);
      return { message: response.data.error, success: false };
    }
    const user = response.data;
    if (user.jwt) localStorage.setItem("token", user.jwt);
    dispatch({ type: RESET_PASSWORD, payload: user });
    return { success: true };
  } catch (error) {
    console.error("Reset Password error", error);
    return {
      message: error.response?.data?.message || "Reset Password failed",
      success: false,
    };
  }
};

export const login = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/signin`, data);
    const user = response.data;
    if (user.jwt) localStorage.setItem("token", user.jwt);
    dispatch({ type: LOGIN, payload: user });
    return { success: true };
  } catch (error) {
    const errorMessage =
      error.response?.data?.message || "Wrong Email or Password";
    console.error("Login error", errorMessage);
    dispatch({ type: LOGIN, payload: { error: errorMessage } });
    return { success: false, message: errorMessage };
  }
};

export const deactivateAccount = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/deactivate`, data);
    const user = response.data;
    const { status, message } = user;
    dispatch({ type: DEACTIVATE_ACCOUNT, payload: user });
    return { status, message };
  } catch (error) {
    const errorMessage =
      error.response?.data?.message || "Failed to deactivate account";
    console.error("Deactivate error", errorMessage);
    dispatch({ type: DEACTIVATE_ACCOUNT, payload: { error: errorMessage } });
    return { status: false, message: errorMessage };
  }
};
export const reactivateAccount = (data) => async (dispatch) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/reactivate`, data);
    const user = response.data;
    const { status, message } = user;
    dispatch({ type: REACTIVATE_ACCOUNT, payload: user });
    return { status, message };
  } catch (error) {
    const errorMessage =
      error.response?.data?.message || "Failed to reactivate account";
    console.error("Reactivate error", errorMessage);
    dispatch({ type: REACTIVATE_ACCOUNT, payload: { error: errorMessage } });
    return { status: false, message: errorMessage };
  }
};

export const currentUser = (token) => async (dispatch) => {
  console.log("token req profile ------ ", token);
  try {
    const response = await axios.get(`${BASE_URL}/api/users/profile`, {
      headers: {
        Authorization: token,
      },
    });
    const user = response.data;
    console.log("req user profile ", user);
    dispatch({ type: REQ_USER, payload: user });
  } catch (error) {
    console.log("Current user error ", error);
  }
};

export const searchUser = (data) => async (dispatch) => {
  try {
    const response = await axios.get(`${BASE_URL}/api/users/search`, {
      params: { name: data.keyword },
      headers: {
        Authorization: data.token,
      },
    });
    const users = response.data;
    const filteredUsers = users.filter((item) => item.id !== data.userId);
    dispatch({ type: SEARCH_USER, payload: filteredUsers });
  } catch (error) {
    console.error("Search user error", error);
  }
};

export const updateUser = (data) => async (dispatch) => {
  console.log("update user data - ", data);
  try {
    const response = await axios.put(
      `${BASE_URL}/api/users/update/${data.id}`,
      data.data,
      {
        headers: {
          Authorization: data.token,
        },
      }
    );
    const updatedUser = response.data;
    console.log("updated user", updatedUser);
    dispatch({ type: UPDATE_USER, payload: updatedUser });
  } catch (error) {
    console.log("Update user error ", error);
  }
};
