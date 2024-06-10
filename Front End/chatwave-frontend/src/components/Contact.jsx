import { useState } from "react";
import { IoMdMail } from "react-icons/io";
import ContactImg from "../assets/images/contact.png";

const Contact = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    subject: "",
    message: "",
  });
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
    validateField(name, value);
  };

  const validateField = (name, value) => {
    let tempErrors = { ...errors };
    if (!value) {
      tempErrors[name] = "This field is required.";
    } else {
      switch (name) {
        case "name":
          tempErrors.name = /^[A-Za-z\s]+$/.test(value)
            ? ""
            : "Name must contain only characters.";
          break;
        case "email":
          tempErrors.email = /^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(value)
            ? ""
            : "Email is not valid.";
          break;
        case "subject":
          tempErrors.subject = /^[A-Za-z0-9\s]{4,80}$/.test(value)
            ? ""
            : "Subject must be 4-80 alphanumeric characters.";
          break;
        case "message":
          tempErrors.message =
            value.length >= 50
              ? ""
              : "Message must be at least 50 characters long.";
          break;
        default:
          break;
      }
    }
    setErrors(tempErrors);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    let isValid = true;
    let tempErrors = { ...errors };

    Object.keys(formData).forEach((key) => {
      if (!formData[key]) {
        tempErrors[key] = "This field is required.";
        isValid = false;
      } else {
        validateField(key, formData[key]);
        if (errors[key]) isValid = false;
      }
    });

    setErrors(tempErrors);

    if (isValid) {
      console.log(formData);
      // Submit the form data
    }
  };

  return (
    <div className="container max-w-[1280px] mx-auto px-4 md:px-10 bg-white pt-6 pb-16">
      <main>
        <h1 className="text-4xl md:text-3xl font-bold text-[#1271ff] text-center pt-4 pb-4 md:pb-12">
          We&apos;d love to hear from you!
        </h1>
        <div className="flex flex-col md:flex-row pt-10 md:pt-0 items-center justify-between">
          <div className="hidden md:block md:w-[50%]">
            <img className="pr-20" src={ContactImg} alt="Chat Wave App" />
          </div>
          <div className="w-[80%] sm:w-[60%] md:w-[50%]">
            <form className="w-full md:w-[80%]" onSubmit={handleSubmit}>
              <div className="mb-6">
                <label
                  className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                  htmlFor="name"
                >
                  Name
                </label>
                <input
                  className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                    errors.name
                      ? "border-red-500 focus:border-red-500"
                      : "border-blue-200 focus:border-blue-400"
                  }`}
                  id="name"
                  type="text"
                  placeholder="Enter your Name..."
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                />
                {errors.name && (
                  <p className="text-red-500 font-semibold text-xs italic">
                    {errors.name}
                  </p>
                )}
              </div>
              <div className="mb-6">
                <label
                  className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                  htmlFor="email"
                >
                  Email
                </label>
                <input
                  className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                    errors.email
                      ? "border-red-500 focus:border-red-500"
                      : "border-blue-200 focus:border-blue-400"
                  }`}
                  id="email"
                  type="email"
                  placeholder="Enter your Email ID..."
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                />
                {errors.email && (
                  <p className="text-red-500 font-semibold text-xs italic">
                    {errors.email}
                  </p>
                )}
              </div>
              <div className="mb-6">
                <label
                  className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                  htmlFor="subject"
                >
                  Subject
                </label>
                <input
                  className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                    errors.subject
                      ? "border-red-500 focus:border-red-500"
                      : "border-blue-200 focus:border-blue-400"
                  }`}
                  id="subject"
                  type="text"
                  placeholder="Enter Subject..."
                  name="subject"
                  value={formData.subject}
                  onChange={handleChange}
                />
                {errors.subject && (
                  <p className="text-red-500 font-semibold text-xs italic">
                    {errors.subject}
                  </p>
                )}
              </div>
              <div className="mb-6">
                <label
                  className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                  htmlFor="message"
                >
                  Message
                </label>
                <textarea
                  className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight h-48 resize-none border-2 ${
                    errors.message
                      ? "border-red-500 focus:border-red-500"
                      : "border-blue-200 focus:border-blue-400"
                  }`}
                  id="message"
                  placeholder="Enter your Message..."
                  name="message"
                  value={formData.message}
                  onChange={handleChange}
                ></textarea>
                {errors.message && (
                  <p className="text-red-500 font-semibold text-xs italic">
                    {errors.message}
                  </p>
                )}
              </div>
              <div className="flex justify-center">
                <button
                  className="flex justify-center items-center space-x-2 bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="submit"
                >
                  <span>Send</span>
                  <IoMdMail className="text-lg" />
                </button>
              </div>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Contact;
