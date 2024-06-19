import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import SimpleSnackbar from "./SimpleSnackbar";
import { forgotPassword, resetPassword } from "../redux/Auth/Action";

const ForgotPassword = () => {
  const [formData, setFormData] = useState({
    email: "",
    otp: "",
    newPassword: "",
    confirmNewPassword: "",
  });
  const [errors, setErrors] = useState({});
  const [step, setStep] = useState(1); // 1: email, 2: otp, newpasswords
  const [open, setOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarType, setSnackbarType] = useState("error");

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const validateField = (name, value) => {
    let tempErrors = { ...errors };
    if (name === "email") {
      tempErrors.email = value ? "" : "Email is required.";
      if (value && !/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(value)) {
        tempErrors.email = "Email is not valid.";
      }
    } else if (name === "otp") {
      tempErrors.otp = value ? "" : "OTP is required.";
    } else if (name === "newPassword") {
      tempErrors.newPassword = value ? "" : "New Password is required.";
      if (value && value.length < 8) {
        tempErrors.newPassword = "Password must be at least 8 characters long.";
      }
      if (value && value !== formData.confirmNewPassword) {
        tempErrors.confirmNewPassword = "Passwords do not match.";
      } else if (value && value === formData.confirmNewPassword) {
        tempErrors.confirmNewPassword = "";
      } else if (!formData.confirmNewPassword) {
        tempErrors.confirmNewPassword = "Confirm New Password is required.";
      }
    } else if (name === "confirmNewPassword") {
      tempErrors.newPassword = formData.newPassword
        ? ""
        : "New Password is required.";
      tempErrors.confirmNewPassword = value
        ? ""
        : "Confirm New Password is required.";
      if (formData.newPassword && value && value !== formData.newPassword) {
        tempErrors.confirmNewPassword = "Passwords do not match.";
      }
    }

    setErrors(tempErrors);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
    validateField(name, value);
  };

  const handleSendOtp = async (e) => {
    e.preventDefault();
    validateField("email", formData.email);

    if (!errors.email && formData.email) {
      const { success, message } = await dispatch(
        forgotPassword({ email: formData.email })
      );
      setSnackbarMessage(message);
      setSnackbarType(success ? "success" : "error");
      setOpen(true);
      console.log(success, message);
      if (success) {
        setStep(2);
      }
    }
  };

  const handleSubmitOtp = async (e) => {
    e.preventDefault();
    validateField("otp", formData.otp);
    validateField("newPassword", formData.newPassword);
    validateField("confirmNewPassword", formData.confirmNewPassword);
    if (
      !errors.otp &&
      formData.otp &&
      !errors.newPassword &&
      !errors.confirmNewPassword &&
      formData.newPassword &&
      formData.confirmNewPassword
    ) {
      const { success, message } = await dispatch(
        resetPassword({
          email: formData.email,
          otp: formData.otp,
          newPassword: formData.newPassword,
        })
      );
      setSnackbarMessage(message);
      setSnackbarType(success ? "success" : "error");
      setOpen(true);
      if (success) {
        console.log("successfully reset password");
        navigate("/signin", {
          state: {
            snackbarMessage: "Reset Password successful",
            snackbarType: "success",
          },
        });
      }
    }
  };
  const handleClose = () => {
    setOpen(false);
  };
  return (
    <div className="container max-w-[1280px] mx-auto px-4 md:px-10 bg-white pt-6 pb-16 min-h-[75vh]">
      <main>
        <h1 className="text-4xl md:text-3xl font-bold text-[#1271ff] text-center pt-4 pb-4">
          Forgot Password
        </h1>
        <p className="text-center text-slate-500 pb-8">
          Reset Your Waves – Surf Back into Your Account with Ease!
        </p>
        <div className="flex justify-center pt-10 md:pt-0">
          <form
            onSubmit={
              step === 1
                ? handleSendOtp
                : step === 2
                ? handleSubmitOtp
                : handleResetPassword
            }
            className="w-[80%] sm:w-[40%]"
          >
            {step === 1 && (
              <div className="text-center">
                <div className="mb-6">
                  <label
                    className="block text-left uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="email"
                  >
                    Email
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.email
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    } focus:outline-none focus:bg-blue-100`}
                    type="email"
                    name="email"
                    placeholder="Enter your email"
                    value={formData.email}
                    onChange={handleChange}
                    disabled={step !== 1}
                  />
                  {errors.email && (
                    <p className="text-red-500 text-left text-xs font-semibold italic">
                      {errors.email}
                    </p>
                  )}
                </div>
                <button
                  className="bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="submit"
                >
                  Send OTP
                </button>
              </div>
            )}
            {step === 2 && (
              <div className="text-center">
                <div className="mb-4">
                  <label
                    className="block text-left uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="otp"
                  >
                    OTP
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.otp
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    } focus:outline-none focus:bg-blue-100`}
                    type="text"
                    name="otp"
                    placeholder="Enter OTP"
                    value={formData.otp}
                    onChange={handleChange}
                    disabled={step !== 2}
                  />
                  {errors.otp && (
                    <p className="text-red-500 text-left text-xs font-semibold italic">
                      {errors.otp}
                    </p>
                  )}
                </div>
                <div className="mb-4">
                  <label
                    className="block text-left uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="newPassword"
                  >
                    New Password
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.newPassword
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    } focus:outline-none focus:bg-blue-100`}
                    type="password"
                    name="newPassword"
                    placeholder="New Password"
                    value={formData.newPassword}
                    onChange={handleChange}
                  />
                  {errors.newPassword && (
                    <p className="text-red-500 text-left text-xs font-semibold italic">
                      {errors.newPassword}
                    </p>
                  )}
                </div>
                <div className="mb-4">
                  <label
                    className="block text-left uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                    htmlFor="confirmNewPassword"
                  >
                    Confirm New Password
                  </label>
                  <input
                    className={`appearance-none block w-full bg-blue-50 text-slate-600 rounded py-2 px-4 mb-3 leading-tight border-2 ${
                      errors.confirmNewPassword
                        ? "border-red-500 focus:border-red-500"
                        : "border-blue-200 focus:border-blue-400"
                    } focus:outline-none focus:bg-blue-100`}
                    type="password"
                    name="confirmNewPassword"
                    placeholder="Confirm New Password"
                    value={formData.confirmNewPassword}
                    onChange={handleChange}
                  />
                  {errors.confirmNewPassword && (
                    <p className="text-red-500 text-left text-xs font-semibold italic">
                      {errors.confirmNewPassword}
                    </p>
                  )}
                </div>
                <button
                  className="bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="submit"
                >
                  Reset Password
                </button>
              </div>
            )}
          </form>
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

export default ForgotPassword;
