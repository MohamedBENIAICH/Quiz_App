import { useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Home from "./components/Home";
import CreateAccount from "./components/CreateAccount";
import CreateQuiz from "./components/CreateQuiz";
import Quiz from "./components/Quiz";
import Start from "./components/Start";
import Login from "./components/Login";
import StartQuiz from "./components/StartQuiz";
import Menu from "./components/Menu";
import Quiz_Pass from "./components/Quiz_Pass";

const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLoginSuccess = () => {
    setIsAuthenticated(true);
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/start" element={<Start />} />
        <Route
          path="/login"
          element={<Login onLoginSuccess={handleLoginSuccess} />}
        />
        <Route path="/create-account" element={<CreateAccount />} />
        <Route
          path="/menu"
          element={
            isAuthenticated ? <Menu /> : <Navigate to="/start" replace />
          }
        />
        <Route path="/start-quiz" element={<StartQuiz />} />
        <Route path="/menu" element={<Menu />} />
        <Route path="/quiz" element={<Quiz />} />
        <Route path="/quiz_pass" element={<Quiz_Pass />} />
        <Route path="/create-quiz" element={<CreateQuiz />} />
      </Routes>
    </Router>
  );
};

export default App;
