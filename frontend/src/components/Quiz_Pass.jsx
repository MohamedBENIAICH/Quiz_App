import { useState } from "react";
import axios from "axios";
import "../components_style/Quiz.css";

const Quiz_Pass = () => {
  const [codePin, setCodePin] = useState("");
  const userId = localStorage.getItem("userId");

  const handleJoinSession = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/session/joinSession?user_id=${userId}&pincode=${codePin}`
      );
      console.log("Response:", response.data);
    } catch (error) {
      console.error("Error joining session:", error);
    }
  };

  return (
    <div>
      <input
        type="text"
        placeholder="Enter code pin to join session"
        value={codePin}
        onChange={(e) => setCodePin(e.target.value)}
      />
      <button onClick={handleJoinSession}>Join Session</button>{" "}
    </div>
  );
};

export default Quiz_Pass;
