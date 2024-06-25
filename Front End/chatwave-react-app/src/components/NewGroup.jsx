/* eslint-disable react/prop-types */
import { useState } from "react";
import axios from "axios";
import { Button, CircularProgress } from "@mui/material";
import { useDispatch } from "react-redux";
import { createGroupChat } from "../redux/Chat/Action";
import { BsArrowLeft, BsCheck2 } from "react-icons/bs";

const NewGroup = ({
  groupMember,
  handleNewGroupOpenClose,
  handleCreateGroupCloseOpen,
}) => {
  const [groupName, setGroupName] = useState("");
  const [groupImage, setgroupImage] = useState(null);
  const [isImageUploading, setIsImageUploading] = useState(false);

  const dispatch = useDispatch();

  const token = localStorage.getItem("token");

  const handleCreateGroup = () => {
    let userIds = [];
    for (let user of groupMember) {
      userIds.push(user.id);
    }
    const group = {
      userIds,
      chat_name: groupName,
      chat_image: groupImage,
    };
    const data = {
      group,
      token,
    };
    dispatch(createGroupChat(data));
    handleCreateGroupCloseOpen();
  };

  return (
    <div className="w-full h-full flex flex-col justify-between">
      <div>
        <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5">
          <BsArrowLeft
            className="cursor-pointer text-2xl font-bold"
            onClick={handleNewGroupOpenClose}
          />
          <p className="font-semibold text-xl">New Group</p>
        </div>

        <div className="flex flex-col justify-center items-center my-12">
          <label htmlFor="imgInput" className="relative">
            <img
              className="rounded-full w-40 sm:w-[15vw] h-40 sm:h-[15vw] cursor-pointer"
              src={
                groupImage ||
                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
              }
              alt="group profile pic"
            />
            {isImageUploading && (
              <CircularProgress className="absolute top-[5rem] left-[6rem]" />
            )}
          </label>
          <input
            type="file"
            id="imgInput"
            className="hidden"
            onChange={(e) => {
              const uploadPic = (pics) => {
                setIsImageUploading(true);
                const data = new FormData();
                data.append("file", pics);
                data.append("upload_preset", "chatwave");
                data.append("cloud_name", "ds6dfcnny");
                axios
                  .post(
                    "https://api.cloudinary.com/v1_1/ds6dfcnny/image/upload",
                    data
                  )
                  .then((response) => {
                    setgroupImage(response.data.url.toString());
                  })
                  .catch((error) => {
                    console.error("Error uploading group image: ", error);
                  })
                  .finally(() => {
                    setIsImageUploading(false);
                  });
              };
              if (!e.target.files) return;

              uploadPic(e.target.files[0]);
            }}
          />
        </div>

        <div className="w-full flex justify-between items-center py-2 px-5">
          <input
            className="w-full outline-none border-b-2 border-blue-700 px-2 bg-transparent"
            type="text"
            onChange={(e) => setGroupName(e.target.value)}
            placeholder="Group Name..."
            value={groupName}
          />
        </div>
      </div>

      {groupName && (
        <div className="mt-24 py-16 bg-slate-200 flex justify-center items-center">
          <Button onClick={handleCreateGroup} variant="text">
            <div className="bg-blue-600 rounded-full p-4 ">
              <BsCheck2 className="text-white font-bold text-3xl" />
            </div>
          </Button>
        </div>
      )}
    </div>
  );
};

export default NewGroup;
