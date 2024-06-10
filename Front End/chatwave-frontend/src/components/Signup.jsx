import { useState } from "react";
import { useNavigate } from "react-router-dom";

import SignupImg from "../assets/images/signup-img.jpg";

const Signup = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [errors, setErrors] = useState({});
  const [step, setStep] = useState(1); // 1: signup, 2: otp
  const [otp, setOtp] = useState("");

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
    validateField(name, value);
  };

  const handleOtpChange = (e) => {
    const { value } = e.target;
    setOtp(value);
    validateField("otp", value);
  };

  const validateField = (name, value) => {
    let tempErrors = { ...errors };
    switch (name) {
      case "firstName":
      case "lastName":
        tempErrors[name] = /^[A-Za-z]+$/.test(value)
          ? ""
          : "Name must contain only characters.";
        break;
      case "email":
        tempErrors.email = /^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(value)
          ? ""
          : "Email is not valid.";
        break;
      case "username":
        tempErrors.username = /^[A-Za-z0-9]{4,25}$/.test(value)
          ? ""
          : "Username must be 4-25 alphanumeric characters.";
        break;
      case "password":
        tempErrors.password =
          value.length >= 8
            ? ""
            : "Password must be at least 8 characters long.";
        tempErrors.confirmPassword =
          value === formData.confirmPassword ? "" : "Passwords do not match.";
        break;
      case "confirmPassword":
        tempErrors.confirmPassword =
          value === formData.password ? "" : "Passwords do not match.";
        break;
      case "otp":
        tempErrors.otp = /^\d{6}$/.test(value) ? "" : "OTP must be 6 digits.";
        break;
      default:
        break;
    }
    setErrors(tempErrors);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    let tempErrors = { ...errors };
    Object.keys(formData).forEach((key) => {
      if (!formData[key]) {
        tempErrors[key] = "This field is required.";
      } else {
        validateField(key, formData[key]);
      }
    });

    setErrors(tempErrors);

    if (Object.values(tempErrors).every((x) => x === "")) {
      setStep(2);
      console.log("OTP sent to", formData.email);
    }
  };

  const handleOtpSubmit = (e) => {
    e.preventDefault();
    if (otp.trim() === "") {
      setErrors({ otp: "OTP is required." });
      return;
    }
    if (!errors.otp) {
      console.log("Signup complete");
      navigate('/signin');
    }
  };

  return (
    <div className="container max-w-[1280px] mx-auto px-4 md:px-10 bg-white pt-6 pb-16">
      <main>
        <h1 className="text-4xl md:text-3xl font-bold text-[#1271ff] text-center pt-4 pb-4">
          Join the Wave!
        </h1>
        <p className="text-center text-slate-500 pb-8">
          Sign up today and start riding the tide of seamless communication with
          Chat Wave.
        </p>
        <div className="flex flex-col md:flex-row pt-10 md:pt-0 items-center justify-between">
          <div className="hidden md:block md:w-[50%]">
            <img className="pr-20" src={SignupImg} alt="Chat Wave App" />
          </div>
          <div className="w-[80%] sm:w-[60%] md:w-[50%]">
            {step === 1 ? (
              <form className="w-full md:w-[80%]" onSubmit={handleSubmit}>
                <div className="mb-3">
                  <div className="flex flex-col md:flex-row justify-between items-center space-x-0 md:space-x-2">
                    <div className="w-full">
                      <label
                        className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                        htmlFor="firstName"
                      >
                        First Name
                      </label>
                      <input
                        className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                          errors.firstName
                            ? "border-red-500 focus:border-red-500"
                            : "border-blue-200 focus:border-blue-400"
                        }`}
                        id="firstName"
                        type="text"
                        placeholder="Enter your First Name..."
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                      />
                      {errors.firstName && (
                        <p className="text-red-500 font-semibold text-xs italic">
                          {errors.firstName}
                        </p>
                      )}
                    </div>
                    <div className="w-full">
                      <label
                        className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                        htmlFor="lastName"
                      >
                        Last Name
                      </label>
                      <input
                        className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                          errors.lastName
                            ? "border-red-500 focus:border-red-500"
                            : "border-blue-200 focus:border-blue-400"
                        }`}
                        id="lastName"
                        type="text"
                        placeholder="Enter your Last Name..."
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                      />
                      {errors.lastName && (
                        <p className="text-red-500 font-semibold text-xs italic">
                          {errors.lastName}
                        </p>
                      )}
                    </div>
                  </div>
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
                    htmlFor="username"
                  >
                    Username
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.username
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    }`}
                    id="username"
                    type="text"
                    placeholder="Enter Username..."
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                  />
                  {errors.username && (
                    <p className="text-red-500 font-semibold text-xs italic">
                      {errors.username}
                    </p>
                  )}
                </div>
                <div className="mb-6">
                  <label
                    className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="password"
                  >
                    Password
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.password
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    }`}
                    id="password"
                    type="password"
                    placeholder="Enter Password..."
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                  />
                  {errors.password && (
                    <p className="text-red-500 font-semibold text-xs italic">
                      {errors.password}
                    </p>
                  )}
                </div>
                <div className="mb-6">
                  <label
                    className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="confirmPassword"
                  >
                    Confirm Password
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.confirmPassword
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    }`}
                    id="confirmPassword"
                    type="password"
                    placeholder="Enter Confirm Password..."
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                  />
                  {errors.confirmPassword && (
                    <p className="text-red-500 font-semibold text-xs italic">
                      {errors.confirmPassword}
                    </p>
                  )}
                </div>
                <div className="flex justify-center">
                  <button
                    className="flex justify-center items-center space-x-2 bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                    type="submit"
                  >
                    <span>Sign up</span>
                  </button>
                </div>
              </form>
            ) : (
              <form className="w-full md:w-[80%]" onSubmit={handleOtpSubmit}>
                <h2 className="text-center text-2xl text-[#1271ff] font-semibold pb-4">Email Verification</h2>
                <p className="text-center text-slate-600">Enter OTP Sent to <span className="text-slate-700 hover:text-blue-600">{formData.email}</span></p>
                <div className="mb-6">
                  <label
                    className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="otp"
                  >
                    OTP
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.otp
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    }`}
                    id="otp"
                    type="text"
                    placeholder="Enter OTP..."
                    name="otp"
                    value={otp}
                    onChange={handleOtpChange}
                  />
                  {errors.otp && (
                    <p className="text-red-500 font-semibold text-xs italic">
                      {errors.otp}
                    </p>
                  )}
                </div>
                <div className="flex justify-center">
                  <button
                    className="flex justify-center items-center space-x-2 bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                    type="submit"
                  >
                    <span>Submit OTP</span>
                  </button>
                </div>
              </form>
            )}
          </div>
        </div>
      </main>
    </div>
  );
};

export default Signup;
