import { useNavigate } from "react-router-dom";
import "../components_style/Home.css";

const HomePage = () => {
  const navigate = useNavigate();

  const handleMenu = () => {
    navigate("/start");
  };

  const handleJoinQuiz = () => {
    navigate("/quiz_pass");
  };

  // const handleStartQuiz = () => {
  //   navigate("/start-quiz");
  // };

  return (
    <div className="home-container">
      <h1>Welcome to Quiz App</h1>
      <button onClick={handleMenu} className="home-button" aria-label="Menu">
        See The Menu
      </button>
      <button
        onClick={handleJoinQuiz}
        className="home-button"
        aria-label="Join Quiz"
      >
        Join Quiz
      </button>
      {/* <button
        onClick={handleStartQuiz}
        className="home-button"
        aria-label="Start Quiz"
      >
        Start a Quiz
      </button> */}
    </div>
  );
};

export default HomePage;
