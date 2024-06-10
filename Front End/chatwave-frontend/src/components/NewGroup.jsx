/* eslint-disable react/prop-types */
import { Button, CircularProgress } from "@mui/material";
import { useState } from "react";
import { BsArrowLeft, BsCheck2 } from "react-icons/bs";

const NewGroup = ({ handleNewGroupOpenClose }) => {
  const [isImageUploading, setIsImageUploading] = useState(false);
  const [groupName, setGroupName] = useState("");

  return (
    <div className="w-full h-full">
      <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5">
        <BsArrowLeft
          className="cursor-pointer text-2xl font-bold"
          onClick={handleNewGroupOpenClose}
        />
        <p className="cursor-pointer font-semibold text-xl">New Group</p>
      </div>

      <div className="flex flex-col justify-center items-center my-12 ">
        <label htmlFor="imgInput" className="relative">
          <img
            className="rounded-full w-[15vw] h-[15vw] cursor-pointer"
            src="https://cdn.pixabay.com/photo/2022/06/04/05/50/feedback-7241297_1280.jpg"
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
          onChange={() => console.log("imageonChange")}
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

      {groupName && (
        <div className="py-10 bg-slate-200 flex items-center justify-center">
          <Button>
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
