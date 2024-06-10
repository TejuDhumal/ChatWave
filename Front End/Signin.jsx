import { useState } from "react";
import SigninImg from "../assets/images/signin-img.jpeg";
import { Link, useNavigate } from "react-router-dom";

const Signin = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState({});

  const navigate = useNavigate();

  const validateField = (name, value) => {
    let tempErrors = { ...errors };
    if (name === "email") {
      tempErrors.email = value ? "" : "Email is required.";
      if (value) {
        tempErrors.email = /^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(value)
          ? ""
          : "Email is not valid.";
      }
    }
    if (name === "password") {
      tempErrors.password = value ? "" : "Password is required.";
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

  const handleSubmit = (e) => {
    e.preventDefault();
    let tempErrors = { ...errors };
    Object.keys(formData).forEach((key) => {
      if (!formData[key]) {
        tempErrors[key] = "This field is required.";
      }
      validateField(key, formData[key]);
    });

    setErrors(tempErrors);

    if (Object.values(tempErrors).every((x) => x === "")) {
      console.log(formData);
      navigate('/dashboard');
    }
  };

  return (
    <div className="container max-w-[1280px] mx-auto px-4 md:px-10 bg-white pt-6 pb-16">
      <main>
        <h1 className="text-4xl md:text-3xl font-bold text-[#1271ff] text-center pt-4 pb-4 md:pb-12">
          Sign in and Start the Conversation!
        </h1>
        <div className="flex flex-col md:flex-row pt-10 md:pt-0 items-center justify-around">
          <div className="hidden md:block md:w-[40%]">
            <img className="pl-32" src={SigninImg} alt="Chat Wave App" />
          </div>
          <div className="w-[80%] sm:w-[60%] md:w-[50%]">
            <form className="w-full md:w-[60%]" onSubmit={handleSubmit}>
              <div className="mb-6">
                <label
                  className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                  htmlFor="email"
                >
                  Email
                </label>
                <input
                  className={`appearance-none block w-full bg-blue-50 text-slate-600 rounded py-2 px-4 mb-3 leading-tight border-2 ${
                    errors.email ? "border-red-500 focus:border-red-500" : "border-blue-200 focus:border-blue-400"
                  } focus:outline-none focus:bg-blue-100`}
                  id="email"
                  type="email"
                  placeholder="Enter your Email ID..."
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                />
                {errors.email && (
                  <p className="text-red-500 text-xs font-semibold italic">{errors.email}</p>
                )}
              </div>
              <div className="mb-4">
                <label
                  className="block uppercase tracking-wide text-slate-700 text-xs font-bold mb-2"
                  htmlFor="password"
                >
                  Password
                </label>
                <input
                  className={`appearance-none block w-full bg-blue-50 text-slate-600 rounded py-2 px-4 mb-3 leading-tight border-2 ${
                    errors.password ? "border-red-500 focus:border-red-500" : "border-blue-200 focus:border-blue-400"
                  } focus:outline-none focus:bg-blue-100`}
                  id="password"
                  type="password"
                  placeholder="Enter Password..."
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                />
                {errors.password && (
                  <p className="text-red-500 text-xs font-semibold italic">
                    {errors.password}
                  </p>
                )}
              </div>
              <div className="mb-6">
                <Link to='/forgot-password' type="button" className="text-slate-500 underline hover:text-blue-600">
                  Forgot Password ?
                </Link>
              </div>
              <div className="flex justify-center">
                <button
                  className="flex justify-center items-center space-x-2 bg-[#1271ff] hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                  type="submit"
                >
                  <span>Sign in</span>
                </button>
              </div>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Signin;
