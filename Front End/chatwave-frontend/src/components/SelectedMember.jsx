/* eslint-disable react/prop-types */
import { AiOutlineClose } from "react-icons/ai";

const SelectedMember = ({ handleRemoveMember, member }) => {
  return (
    <div className="flex items-center bg-slate-300 rounded-full">
      <img
        className="w-7 h-7 rounded-full"
        src="https://cdn.pixabay.com/photo/2020/05/09/13/29/photographer-5149664_1280.jpg"
        alt=""
      />
      <p className="px-2">username</p>
      <AiOutlineClose
        className="pr-1 cursor-pointer"
        onClick={handleRemoveMember}
      />
    </div>
  );
};

export default SelectedMember;
