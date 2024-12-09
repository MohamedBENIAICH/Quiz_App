import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../components_style/Start.css";

const AuthToggle = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleLogIn = () => {
    navigate("/login");
  };

  const handleSignIn = () => {
    navigate("/create-account");
  };

  return (
    <div className="auth-toggle-container">
      <h2>{isLoggedIn ? "Welcome Back!" : "Start Here"}</h2>
      {!isLoggedIn ? (
        <div>
          <button onClick={handleLogIn} className="auth-toggle-button">
            Log In
          </button>
          <button onClick={handleSignIn} className="auth-toggle-button">
            Sign In
          </button>
        </div>
      ) : (
        <button
          onClick={() => setIsLoggedIn(false)}
          className="auth-toggle-button"
        >
          Log Out
        </button>
      )}
    </div>
  );
};

export default AuthToggle;
