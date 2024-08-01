import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import SimpleSnackbar from "./SimpleSnackbar";
import { currentUser, register, verifyOtp } from "../redux/Auth/Action";
import SignupImg from "../../public/signup-img.jpg";

const Signup = () => {
  const [formData, setFormData] = useState({
    full_name: "",
    email: "",
    username: "",
    password: "",
    confirmPassword: "",
    bio: "Available in Chatwave",
  });
  const [errors, setErrors] = useState({});
  const [step, setStep] = useState(1); // 1: signup, 2: otp
  const [otp, setOtp] = useState("");
  const [open, setOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarType, setSnackbarType] = useState("error");

  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);

  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const handleClose = () => setOpen(false);

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
      case "full_name":
        tempErrors[name] = /^[A-Za-z\s]+$/.test(value)
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
          /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(
            value
          )
            ? ""
            : "Password must be at least 8 characters long, include at least one uppercase, one lowercase, one number, and one special character.";
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

  //dispatch current user if user already signup
  useEffect(() => {
    if (token) dispatch(currentUser(token));
  }, [auth.isAuth, token]);

  //redirect to signin page if register success
  useEffect(() => {
    if (auth.reqUser) {
      navigate("/signin");
    }
  }, [auth.reqUser]);

  const handleSubmit = async (e) => {
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
      const { message, success } = await dispatch(register(formData));
      setSnackbarMessage(message);
      setSnackbarType(success ? "success" : "error");
      setOpen(true);
      if (success) {
        setStep(2);
      }
    }
  };

  const handleOtpSubmit = async (e) => {
    e.preventDefault();
    if (otp.trim() === "") {
      setErrors({ otp: "OTP is required." });
      return;
    }
    if (!errors.otp) {
      const { message, success } = await dispatch(
        verifyOtp({ email: formData.email, otp })
      );
      setSnackbarMessage(message);
      setSnackbarType(success ? "success" : "error");
      setOpen(true);
      if (success) {
        navigate("/signin", {
          state: {
            snackbarMessage: "Email verified successfully",
            snackbarType: "success",
          },
        });
      }
    }
  };

  return (
    <div className="container max-w-[1440px] mx-auto px-4 md:px-10 bg-white pt-6 pb-16">
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
                <div className="mb-6">
                  <label
                    className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="full_name"
                  >
                    Full Name
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 outline-none rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.full_name
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    }`}
                    id="full_name"
                    type="text"
                    placeholder="Enter your First Name..."
                    name="full_name"
                    value={formData.full_name}
                    onChange={handleChange}
                  />
                  {errors.full_name && (
                    <p className="text-red-500 font-semibold text-xs italic">
                      {errors.full_name}
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
                <h2 className="text-center text-2xl text-[#1271ff] font-semibold pb-4">
                  Email Verification
                </h2>
                <p className="text-center text-slate-600">
                  Enter OTP Sent to{" "}
                  <span className="text-slate-700 hover:text-blue-600">
                    {formData.email}
                  </span>
                </p>
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
                    <span>Verify</span>
                  </button>
                </div>
              </form>
            )}
          </div>
        </div>
        <SimpleSnackbar
          message={snackbarMessage}
          open={open}
          handleClose={handleClose}
          type={snackbarType}
        />
      </main>
    </div>
  );
};

export default Signup;
