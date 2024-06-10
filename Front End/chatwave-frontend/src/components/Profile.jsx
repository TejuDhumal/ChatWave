/* eslint-disable react/prop-types */
import { useState } from "react";
import { BsArrowLeft, BsCheck2, BsPencil } from "react-icons/bs";

const Profile = ({ handleProfileCloseOpen }) => {
  const [name, setName] = useState(null);
  const [editName, setEditName] = useState(false);
  const [bio, setBio] = useState(null);
  const [editBio, setEditBio] = useState(false);

  const handleEditName = () => {
    setEditName(true);
  };

  const handleEditNameCheck = () => {
    setEditName(false);
  };

  const handleEditBio = () => {
    setEditBio(true);
  };

  const handleEditBioCheck = () => {
    setEditBio(false);
  };

  return (
    <div>
      <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5">
        <BsArrowLeft
          className="cursor-pointer text-2xl font-bold"
          onClick={handleProfileCloseOpen}
        />
        <p className="font-semibold">Profile</p>
      </div>

      {/* update profile pic section */}
      <div className="flex flex-col justify-center items-center my-12">
        <label htmlFor="imgInput">
          <img
            className="rounded-full w-[15vw] h-[15vw] cursor-pointer"
            src="https://cdn.pixabay.com/photo/2024/05/30/15/31/great-spotted-woodpecker-8798731_1280.jpg"
            alt="profile picture"
          />
        </label>
        <input type="file" id="imgInput" className="hidden" />
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
            />
            <BsCheck2
              className="cursor-pointer text-2xl"
              onClick={handleEditNameCheck}
            />
          </div>
        ) : (
          <div className="w-full flex justify-between items-center">
            <p className="py-3">{name || "user name"}</p>
            <BsPencil className="cursor-pointer" onClick={handleEditName} />
          </div>
        )}
      </div>

      {/* update bio section */}
      <div className="bg-white px-3">
        <p className="py-3 font-semibold">Bio</p>

        {editBio ? (
          <div className="w-full flex justify-between items-center">
            <input
              className="w-[80%] outline-none border-b-2 border-blue-700 p-2"
              type="text"
              placeholder="Enter your name..."
              onChange={(e) => setBio(e.target.value)}
            />
            <BsCheck2
              className="cursor-pointer text-2xl"
              onClick={handleEditBioCheck}
            />
          </div>
        ) : (
          <div className="w-full flex justify-between items-center">
            <p className="py-3">{bio || "Available"}</p>
            <BsPencil className="cursor-pointer" onClick={handleEditBio} />
          </div>
        )}
      </div>
    </div>
  );
};

export default Profile;
