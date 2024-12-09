import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../components_style/Login.css";

// eslint-disable-next-line react/prop-types
const Login = ({ onLoginSuccess }) => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState({});
  const [serverError, setServerError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.email.trim()) {
      newErrors.email = "Email is required.";
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = "Please enter a valid email.";
    }
    if (!formData.password.trim()) {
      newErrors.password = "Password is required.";
    }
    return newErrors;
  };

  const fetchCurrentUser = async () => {
    try {
      const response = await fetch("http://localhost:8080/user/current", {
        method: "GET",
        credentials: "include",
      });

      if (response.ok) {
        const user = await response.json();
        console.log("Current user fetched:", user);
        localStorage.setItem("userId", user.id);
        return user.id;
      } else {
        console.error("Failed to fetch current user");
        return null;
      }
    } catch (error) {
      console.error("Error fetching current user:", error);
      return null;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      setErrors({});
      try {
        const response = await fetch("http://localhost:8080/user/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          body: new URLSearchParams(formData),
          credentials: "include",
        });

        if (response.ok) {
          console.log("Login successful.");
          const userId = await fetchCurrentUser();
          if (userId) {
            console.log("User ID:", userId);
            onLoginSuccess(userId);
            navigate("/menu"); /////// Here
          } else {
            setServerError("Failed to retrieve user information.");
          }
        } else {
          setServerError("Invalid email or password.");
        }
      } catch (error) {
        console.error("Error during login:", error);
        setServerError("An error occurred. Please try again.");
      }
    }
  };

  return (
    <div className="login-container">
      <h2>Log In</h2>
      <form onSubmit={handleSubmit} className="login-form">
        <div className="form-field">
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && (
            <span className="error-message">{errors.email}</span>
          )}
        </div>
        <div className="form-field">
          <label>Password</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          {errors.password && (
            <span className="error-message">{errors.password}</span>
          )}
        </div>
        {serverError && <p className="error-message">{serverError}</p>}
        <button type="submit" className="login-button">
          Log In
        </button>
      </form>
    </div>
  );
};

export default Login;
