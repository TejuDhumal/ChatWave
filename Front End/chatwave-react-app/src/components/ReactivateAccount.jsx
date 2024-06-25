import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import SimpleSnackbar from "./SimpleSnackbar";
import { currentUser, reactivateAccount } from "../redux/Auth/Action";

const ReactivateAccount = () => {
  const [formData, setFormData] = useState({
    email: "",
    otp: "",
  });
  const [errors, setErrors] = useState({});
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

  const validateField = (name, value) => {
    let tempErrors = { ...errors };
    switch (name) {
      case "email":
        tempErrors.email = /^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(value)
          ? ""
          : "Email is not valid.";
        break;
      case "otp":
        tempErrors.otp = /^\d{6}$/.test(value) ? "" : "OTP must be 6 digits.";
        break;
      default:
        break;
    }
    setErrors(tempErrors);
  };

  useEffect(() => {
    if (token) dispatch(currentUser(token));
  }, [auth.isAuth, token]);

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
      const { status, message } = await dispatch(
        reactivateAccount({ email: formData.email, otp: formData.otp })
      );
      setSnackbarMessage(message);
      // console.log("status", user);
      if (status) {
        navigate("/signin", {
          state: {
            snackbarMessage: "Account Reactivated successfully",
            snackbarType: "success",
          },
        });
      } else {
        setOpen(true);
      }
    }
  };

  return (
    <div className="container max-w-[1440px] mx-auto px-4 md:px-10 bg-white pt-6 md:pt-24 pb-16 md:h-[78vh]">
      <main>
        <h1 className="text-4xl md:text-3xl font-bold text-[#1271ff] text-center pt-4 pb-4">
          Re-Join the Wave!
        </h1>
        <p className="text-center text-slate-500 pb-8">
          Ride the Wave Back to Chat - Reactivate Your Account Today!
        </p>
        <div className="flex justify-center pt-10 md:pt-0">
          <form onSubmit={handleSubmit} className="w-[80%] sm:w-[40%]">
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
                />
                {errors.email && (
                  <p className="text-red-500 text-left text-xs font-semibold italic">
                    {errors.email}
                  </p>
                )}
              </div>
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
                />
                {errors.otp && (
                  <p className="text-red-500 text-left text-xs font-semibold italic">
                    {errors.otp}
                  </p>
                )}
              </div>
              <button
                className="bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="submit"
                onClick={handleSubmit}
              >
                Reactivate Account
              </button>
            </div>
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

export default ReactivateAccount;
