/* eslint-disable react/prop-types */
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { searchUser } from "../redux/Auth/Action";
import { BsArrowLeft, BsArrowRight } from "react-icons/bs";
import SelectedMember from "./SelectedMember";
import ChatCard from "./ChatCard";
import NewGroup from "./NewGroup";

const CreateGroup = ({ handleCreateGroupCloseOpen }) => {
  const [newGroup, setNewGroup] = useState(false);
  const [groupMember, setGroupMember] = useState(new Set());
  const [query, setQuery] = useState("");

  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();

  const token = localStorage.getItem("token");

  const handleRemoveMember = (item) => {
    setGroupMember((currentMembers) => {
      const updatedMembers = new Set(currentMembers);
      updatedMembers.delete(item);
      return updatedMembers;
    });
  };

  const handleNewGroupOpenClose = () => setNewGroup(false);

  const handleSearch = (keyword) => {
    dispatch(searchUser({ userId: auth.reqUser?.id, keyword, token }));
  };

  return (
    <div className="w-full h-full bg-white flex flex-col justify-between">
      {newGroup ? (
        <div>
          <NewGroup
            groupMember={groupMember}
            handleNewGroupOpenClose={handleNewGroupOpenClose}
            handleCreateGroupCloseOpen={handleCreateGroupCloseOpen}
          />
        </div>
      ) : (
        <div className="flex flex-col justify-between h-full">
          <div>
            <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5">
              <BsArrowLeft
                className="cursor-pointer text-2xl font-bold"
                onClick={handleCreateGroupCloseOpen}
              />
              <p className="font-semibold text-xl">Add Group Participants</p>
            </div>

            <div className="flex flex-col items-center space-y-4 bg-white py-4 px-3">
              <div className="flex flex-wrap space-y-1">
                {groupMember.size > 0 &&
                  Array.from(groupMember).map((item) => (
                    <SelectedMember
                      key={item}
                      handleRemoveMember={() => handleRemoveMember(item)}
                      member={item}
                    />
                  ))}
              </div>

              <input
                className="outline-none border-b border-[#888888] p-2 w-[93%]"
                type="text"
                placeholder="Search users..."
                onChange={(e) => {
                  handleSearch(e.target.value);
                  setQuery(e.target.value);
                }}
                value={query}
              />
            </div>

            <div className="bg-white overflow-y-scroll">
              {query &&
                auth.searchUser?.map((item) => (
                  <div
                    key={item?.id}
                    onClick={() => {
                      groupMember.add(item);
                      setGroupMember(groupMember);
                      setQuery("");
                    }}
                  >
                    <hr />
                    <ChatCard
                      isChat={false}
                      name={item.full_name}
                      userImg={
                        item.profile_picture ||
                        "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460__340.png"
                      }
                    />
                  </div>
                ))}
            </div>
          </div>

          <div className="py-4 bg-slate-200 flex justify-center items-center">
            <div
              className="bg-blue-600 rounded-full p-4 cursor-pointer"
              onClick={() => {
                setNewGroup(true);
              }}
            >
              <BsArrowRight className="text-white font-bold text-3xl" />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default CreateGroup;
