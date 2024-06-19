/* eslint-disable react/prop-types */


const MessageCard = ({ isReqUser, content }) => {
  return (
    <div className={`p-2 rounded-md max-w-[50%] ${isReqUser ? 'self-start bg-white' : 'self-end bg-blue-600 text-white'}`}>
      <p>{content}</p>
    </div>
  )
}

export default MessageCard