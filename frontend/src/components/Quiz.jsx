import { useEffect, useState } from "react";
import axios from "axios";
import "../components_style/Quiz.css";
import { CircularProgressbar, buildStyles } from "react-circular-progressbar";
import "react-circular-progressbar/dist/styles.css";

const Quiz = () => {
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [selectedAnswer, setSelectedAnswer] = useState(null);
  const [feedback, setFeedback] = useState(null);
  const [timeLeft, setTimeLeft] = useState(10);
  const [correctAnswer, setCorrectAnswer] = useState(0);
  const [quizFinished, setQuizFinished] = useState(false);
  const [userAnswers, setUserAnswers] = useState([]);
  const [codePin, setCodePin] = useState("");
  const [isInputVisible, setIsInputVisible] = useState(false);
  const [isInputVisible2, setIsInputVisible2] = useState(false);
  const [isInputVisible3, setIsInputVisible3] = useState(false);
  const [codePinQuestions, setcodePinQuestions] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/questions/getAll")
      .then((response) => {
        setQuestions(response.data);
      })
      .catch((error) => {
        console.error("Error fetching questions:", error);
      });
  }, []);

  useEffect(() => {
    if (timeLeft === 0 || selectedAnswer !== null) {
      const nextQuestionTimeout = setTimeout(() => {
        handleNextQuestion();
      }, 1000);

      return () => clearTimeout(nextQuestionTimeout);
    }

    const timer = setInterval(() => {
      setTimeLeft((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);

    return () => clearInterval(timer);
  }, [timeLeft, selectedAnswer]);

  const handleAnswerClick = (option) => {
    setSelectedAnswer(option);

    const isCorrect =
      option.trim().toLowerCase() ===
      questions[currentQuestionIndex].rightAnswer.trim().toLowerCase();

    if (isCorrect) {
      setFeedback("Correct!");
      setCorrectAnswer((prev) => prev + 1);
    } else {
      setFeedback("Wrong! Try again.");
    }

    setUserAnswers((prev) => [
      ...prev,
      {
        question: questions[currentQuestionIndex].questionTitle,
        selectedAnswer: option,
        correctAnswer: questions[currentQuestionIndex].rightAnswer,
        options: [
          questions[currentQuestionIndex].option1,
          questions[currentQuestionIndex].option2,
          questions[currentQuestionIndex].option3,
          questions[currentQuestionIndex].option4,
        ],
        isCorrect,
      },
    ]);
  };

  const handleNextQuestion = () => {
    setSelectedAnswer(null);
    setFeedback(null);
    setTimeLeft(10);

    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
    } else {
      setQuizFinished(true);
    }
  };

  // useEffect(() => {
  //   const createSession = async () => {
  //     try {
  //       const response = await axios.post('http://localhost:8080/questions/createSession?numQ=5');
  //       setCodePin(response.data);
  //     } catch (error) {
  //       console.error('Error creating session:', error);
  //     }
  //   };

  //   createSession();
  //   }, []);

  const handleCreateSession = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/questions/createSession?numQ=1"
      );
      setCodePin(response.data);
      setIsInputVisible2(true);
    } catch (error) {
      console.error("Error creating session:", error);
    }
  };

  const handleInputVisible = () => {
    setIsInputVisible(true);
    if (codePin) {
      console.log("Joining session with code:", codePin);
    } else {
      console.log("Please enter a valid code.");
    }
  };

  const handleJoinSession = async () => {
    try {
      const response = await axios.post(
        "http://localhost:8080/questions/joinSession?codePin=" + codePin
      );
      setCodePin(response.data);
      setIsInputVisible3(true);
    } catch (error) {
      console.error("Error creating session:", error);
    }
  };

  const currentQuestion = questions[currentQuestionIndex];

  return (
    <>
      <div>
        <button onClick={handleCreateSession}>Create Session</button>
        {isInputVisible2 && (
          <>
            <input
              type="text"
              placeholder="Enter code pin to join session"
              value={codePin}
              onChange={(e) => setCodePin(e.target.value)}
            />
          </>
        )}
        <button onClick={handleInputVisible}>Join Session</button>
        {isInputVisible && (
          <>
            <input
              type="text"
              placeholder="Enter code pin to join session"
              value={codePin}
              onChange={(e) => setCodePin(e.target.value)}
            />
            <button onClick={handleJoinSession}>Join</button>
          </>
        )}
      </div>

      {isInputVisible3 && (
        <div className="quiz-container">
          {quizFinished ? (
            <div className="quiz-result">
              <h2>Quiz Finished!</h2>
              <p>
                Your score: {correctAnswer} out of {questions.length}
              </p>
              <h3>Review Your Answers:</h3>
              <div className="review-questions">
                {userAnswers.map((answer, index) => (
                  <div
                    key={index}
                    className={`review-question ${
                      answer.isCorrect ? "correct" : "incorrect"
                    }`}
                  >
                    <h4>
                      {index + 1}. {answer.question}
                    </h4>
                    <div className="review-options">
                      {answer.options.map((option, idx) => (
                        <div
                          key={idx}
                          className={`review-option ${
                            option === answer.correctAnswer
                              ? "correct-option"
                              : ""
                          } ${
                            option === answer.selectedAnswer
                              ? "user-selected"
                              : ""
                          }`}
                        >
                          {option}
                        </div>
                      ))}
                    </div>
                    <p>
                      Your answer: <strong>{answer.selectedAnswer}</strong>
                      {answer.isCorrect ? " (Correct)" : " (Incorrect)"}
                    </p>
                  </div>
                ))}
              </div>
            </div>
          ) : currentQuestion ? (
            <>
              <h2>{currentQuestion.questionTitle}</h2>
              <div className="options-container">
                <button
                  onClick={() => handleAnswerClick(currentQuestion.option1)}
                  disabled={selectedAnswer !== null}
                  className={`option-button ${
                    selectedAnswer === currentQuestion.option1 ? "selected" : ""
                  }`}
                >
                  {currentQuestion.option1}
                </button>
                <button
                  onClick={() => handleAnswerClick(currentQuestion.option2)}
                  disabled={selectedAnswer !== null}
                  className={`option-button ${
                    selectedAnswer === currentQuestion.option2 ? "selected" : ""
                  }`}
                >
                  {currentQuestion.option2}
                </button>
                <button
                  onClick={() => handleAnswerClick(currentQuestion.option3)}
                  disabled={selectedAnswer !== null}
                  className={`option-button ${
                    selectedAnswer === currentQuestion.option3 ? "selected" : ""
                  }`}
                >
                  {currentQuestion.option3}
                </button>
                <button
                  onClick={() => handleAnswerClick(currentQuestion.option4)}
                  disabled={selectedAnswer !== null}
                  className={`option-button ${
                    selectedAnswer === currentQuestion.option4 ? "selected" : ""
                  }`}
                >
                  {currentQuestion.option4}
                </button>
              </div>
              {feedback && <div className="feedback">{feedback}</div>}
              <div className="timer">
                <CircularProgressbar
                  value={timeLeft}
                  maxValue={10}
                  text={`${timeLeft}s`}
                  styles={buildStyles({
                    textColor: "#f88",
                    pathColor: "#4caf50",
                    trailColor: "#d6d6d6",
                  })}
                />
              </div>
            </>
          ) : (
            <p>Loading question...</p>
          )}
        </div>
      )}
    </>
  );
};

export default Quiz;
