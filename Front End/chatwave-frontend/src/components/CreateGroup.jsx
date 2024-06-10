/* eslint-disable react/prop-types */
import { useState } from "react";
import { BsArrowLeft, BsArrowRight } from "react-icons/bs";
import SelectedMember from "./SelectedMember";
import ChatCard from "./ChatCard";
import NewGroup from "./NewGroup";

const CreateGroup = ({ handleCreateGroupCloseOpen }) => {
  const [newGroup, setNewGroup] = useState(false);
  const [groupMember, setGroupMember] = useState(new Set());
  const [query, setQuery] = useState("");

  const handleRemoveMember = (item) => {
    setGroupMember((currentMembers) => {
      const updatedMembers = new Set(currentMembers);
      updatedMembers.delete(item);
      return updatedMembers;
    });
  };

  const handleNewGroupOpenClose = () => setNewGroup(false);

  const handleSearch = () => {};

  return (
    <div className="w-full h-full">
      {newGroup ? (
        <div>
          <NewGroup handleNewGroupOpenClose={handleNewGroupOpenClose} />
        </div>
      ) : (
        <div>
          <div className="flex items-center space-x-4 bg-[#1249ff] text-white pt-5 px-4 pb-5">
            <BsArrowLeft
              className="cursor-pointer text-2xl font-bold"
              onClick={handleCreateGroupCloseOpen}
            />
            <p className="cursor-pointer font-semibold text-xl">
              Add Group Participants
            </p>
          </div>

          <div className="relative bg-white py-4 px-3">
            <div className="flex flex-wrap scale-x-2 space-y-1">
              {groupMember.size > 0 &&
                Array.from(groupMember).map((item) => (
                  <SelectedMember
                    key={item}
                    handleRemoveMember={() => handleRemoveMember(item)}
                    member={item}
                  />
                ))}
              {console.log(groupMember)}
            </div>

            <div>
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

            <div className="bg-white overscroll-y-scroll h-[50.2vh]">
              {query &&
                [1, 2, 3, 4].map((item) => (
                  <div
                    key={item}
                    onClick={() => {
                      groupMember.add(item);
                      setGroupMember(groupMember);
                      setQuery("");
                    }}
                  >
                    <hr />
                    <ChatCard />
                  </div>
                ))}
            </div>

            <div className="bottom-10 py-10 bg-slate-200 flex items-center justify-center">
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
        </div>
      )}
    </div>
  );
};

export default CreateGroup;
