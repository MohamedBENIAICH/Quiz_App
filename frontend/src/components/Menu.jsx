import { useNavigate } from "react-router-dom";
import "../components_style/Home.css";

const MenuPage = () => {
  const navigate = useNavigate();

  const handleCreateQuiz = () => {
    navigate("/create-quiz");
  };

  const handleStartQuiz = () => {
    navigate("/start-quiz");
  };

  return (
    <div className="home-container">
      <h1>Welcome to Quiz App</h1>
      <button
        onClick={handleCreateQuiz}
        className="home-button"
        aria-label="Create Quiz"
      >
        Create Quiz
      </button>
      <button
        onClick={handleStartQuiz}
        className="home-button"
        aria-label="Start Quiz"
      >
        Start a Quiz
      </button>
    </div>
  );
};

export default MenuPage;
