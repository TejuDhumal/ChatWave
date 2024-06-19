import { useState, useEffect } from "react";
import axios from "axios";
import { BsArrowLeft } from "react-icons/bs";
import { useDispatch, useSelector } from "react-redux";
import { deactivateAccount, updateUser } from "../redux/Auth/Action";
import SimpleSnackbar from "./SimpleSnackbar";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Button,
} from "@mui/material";
import { setFieldToNull } from "../redux/Empty/Action";
import { REQ_USER, REGISTER, LOGIN } from "../redux/Auth/ActionType";
import { useNavigate } from "react-router-dom";

const Profile = ({ handleProfileCloseOpen }) => {
  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [name, setName] = useState(auth.reqUser.full_name);
  const [editName, setEditName] = useState(false);
  const [bio, setBio] = useState(auth.reqUser.bio);
  const [editBio, setEditBio] = useState(false);
  const [tempPicture, setTempPicture] = useState(null);
  const [openDeactivateModal, setOpenDeactivateModal] = useState(false);
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [snackbarMessage, setSnackbarMessage] = useState("");

  const data = {
    id: auth.reqUser?.id,
    token: localStorage.getItem("token"),
    data: { full_name: name, bio: bio },
  };

  const handleClose = () => setOpen(false);

  const handleEditName = () => {
    setEditName(true);
  };

  const handleEditNameCheck = () => {
    dispatch(updateUser(data));
    setMessage("Name updated successfully!");
    setEditName(false);
    setOpen(true);
  };

  const handleEditBio = () => {
    setEditBio(true);
  };

  const handleEditBioCheck = () => {
    dispatch(updateUser(data));
    setMessage("Bio updated successfully!");
    setEditBio(false);
    setOpen(true);
  };

  const handleOpenDeactivateModal = () => {
    setOpenDeactivateModal(true);
  };

  const handleCloseDeactivateModal = () => {
    setOpenDeactivateModal(false);
  };

  const handleDeactivate = async () => {
    const { status, message } = await dispatch(
      deactivateAccount({ email: auth?.reqUser.email })
    );
    handleCloseDeactivateModal();
    setSnackbarMessage(message);
    if (status) {
      localStorage.removeItem("token");
      dispatch(setFieldToNull(REGISTER));
      dispatch(setFieldToNull(LOGIN));
      dispatch(setFieldToNull(REQ_USER));
      navigate("/signin", {
        state: {
          snackbarMessage: "Account Deactivated successfully",
          snackbarType: "success",
        },
      });
    }
  };

  useEffect(() => {
    if (!auth.reqUser) navigate("/signin");
  }, [auth.reqUser, navigate]);

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
      setTempPicture(imageUrl);
      setMessage("Profile image updated successfully");
      setOpen(true);
      console.log("imgurl", imageUrl);
      const imgData = {
        id: auth.reqUser.id,
        token: localStorage.getItem("token"),
        data: { profile_picture: imageUrl },
      };
      dispatch(updateUser(imgData));
    } catch (error) {
      console.error("Error updating profile image: ", error);
      setMessage(
        `Error updating profile image: ${error.response.data.error.message}`
      );
      setOpen(true);
    }
  };

  return (
    <div className="h-full w-full">
      <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5">
        <BsArrowLeft
          className="cursor-pointer text-2xl font-bold"
          onClick={handleProfileCloseOpen}
        />
        <p className="font-semibold text-xl">Profile</p>
      </div>

      {/* update profile pic section */}
      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput">
          <img
            className="rounded-full w-[15vw] h-[15vw] cursor-pointer"
            src={
              tempPicture ||
              auth.reqUser.profile_picture ||
              "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
            }
            alt="profile picture"
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
        />
      </div>

      {/* update name section */}
      <div className="bg-white px-3 my-6">
        <p className="py-3 font-semibold">Name</p>
        {editName ? (
          <div className="w-full flex justify-between items-center">
            <input
              className="w-[80%] outline-none border-b-2 border-blue-700 p-2"
              type="text"
              placeholder="Enter your name..."
              onChange={(e) => setName(e.target.value)}
              value={name}
              onKeyPress={(e) => {
                if (e.key === "Enter") {
                  dispatch(updateUser(data));
                  setEditName(false);
                }
              }}
            />
            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 rounded-full"
              onClick={handleEditNameCheck}
            >
              Update
            </button>
          </div>
        ) : (
          <div className="w-full flex justify-between items-center">
            <p className="py-3">{name || auth.reqUser?.full_name}</p>
            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded-full"
              onClick={handleEditName}
            >
              Edit
            </button>
          </div>
        )}
      </div>

      {/* update bio section */}
      <div className="bg-white px-3 my-6">
        <p className="py-3 font-semibold">Bio</p>

        {editBio ? (
          <div className="w-full flex justify-between items-center">
            <input
              className="w-[80%] outline-none border-b-2 border-blue-700 p-2"
              type="text"
              placeholder="Enter your bio..."
              onChange={(e) => setBio(e.target.value)}
              value={bio}
              onKeyPress={(e) => {
                if (e.key === "Enter") {
                  dispatch(updateUser(data));
                  setEditBio(false);
                }
              }}
            />
            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 rounded-full"
              onClick={handleEditBioCheck}
            >
              Update
            </button>
          </div>
        ) : (
          <div className="w-full flex justify-between items-center">
            <p className="py-3">{bio || auth.reqUser?.full_name}</p>
            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded-full"
              onClick={handleEditBio}
            >
              Edit
            </button>
          </div>
        )}
      </div>

      <div className="text-center my-6 pb-12">
        <button
          className="text-red-600 bg-blue-200 hover:bg-red-600 hover:text-white font-semibold text-lg px-4 py-1 rounded-full"
          onClick={handleOpenDeactivateModal}
        >
          Deactivate Account
        </button>
      </div>

      <Dialog
        open={openDeactivateModal}
        onClose={handleCloseDeactivateModal}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Deactivate Account"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you want to deactivate your account? You can reactivate
            your account and access your previous chats later.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDeactivateModal} color="primary">
            Cancel
          </Button>
          <Button
            sx={{
              color: "red",
              ":hover": { color: "white", background: "red" },
            }}
            onClick={handleDeactivate}
            color="secondary"
            autoFocus
          >
            Deactivate
          </Button>
        </DialogActions>
      </Dialog>

      <SimpleSnackbar
        message={message}
        open={open}
        handleClose={handleClose}
        type={"success"}
      />
    </div>
  );
};

export default Profile;
