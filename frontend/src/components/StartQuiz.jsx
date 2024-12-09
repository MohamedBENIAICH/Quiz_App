import { useState, useEffect } from "react";

const QuizManager = () => {
  const [selectedOption, setSelectedOption] = useState(null);
  const [quizzes, setQuizzes] = useState([]);
  const [selectedQuiz, setSelectedQuiz] = useState(null);
  const [pinCode, setPinCode] = useState(null);
  const [loading, setLoading] = useState(false);
  const userId = localStorage.getItem("userId");
  const quizId = localStorage.getItem("quizId");

  useEffect(() => {
    if (selectedOption === "choose") {
      setLoading(true);
      fetch(`http://localhost:8080/quiz/userQuiz?userId=${userId}`)
        .then((response) => response.json())
        .then((data) => {
          console.log("Fetched Quizzes:", data);
          setQuizzes(data);
          setLoading(false);
        })
        .catch((error) => {
          console.error("Error fetching quizzes:", error);
          setLoading(false);
        });
    }
  }, [selectedOption, userId]);

  const handleOptionSelection = (option) => {
    setSelectedOption(option);
    setSelectedQuiz(null);
    setPinCode(null);
    localStorage.removeItem("quizId");
  };

  const handleQuizSelection = async (quiz) => {
    setSelectedQuiz(quiz);
    localStorage.setItem("quizId", quiz.id);
    try {
      const response = await fetch(
        `http://localhost:7777/session/createSession?user_id=${userId}&quiz_id=${quiz.id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            userId,
            quizId: quiz.id,
          }),
        }
      );

      if (response.ok) {
        console.log("Session created successfully.");
      } else {
        console.error("Error creating session:", response.statusText);
      }
    } catch (error) {
      console.error("Error creating session:", error);
    }
    const generatedPin = Math.floor(100000 + Math.random() * 900000);
    setPinCode(generatedPin);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Quiz Manager</h1>
      {!selectedOption && (
        <div>
          <button onClick={() => handleOptionSelection("create")}>
            Create a New Quiz
          </button>
          <button onClick={() => handleOptionSelection("choose")}>
            Choose from Your List
          </button>
        </div>
      )}

      {selectedOption === "create" && (
        <div>
          <h2>Create a New Quiz</h2>
          <p>
            Redirect to the quiz creation page or integrate the create quiz
            component here.
          </p>
        </div>
      )}

      {selectedOption === "choose" && (
        <div>
          <h2>Your Quizzes</h2>
          {loading && <p>Loading quizzes...</p>}
          {!loading && quizzes.length === 0 && (
            <p>You have not created any quiz yet!</p>
          )}
          {!loading && quizzes.length > 0 && (
            <ul>
              {quizzes.map((quiz, index) => (
                <li key={index} style={{ marginBottom: "20px" }}>
                  <h3>{quiz.quizTitle}</h3>
                  {quiz.questions?.length > 0 ? (
                    quiz.questions.map((q, idx) => (
                      <div key={idx} style={{ marginBottom: "15px" }}>
                        <p>
                          <strong>{q.questionTitle}</strong>
                        </p>
                        <ul>
                          {q.option1 && <li>{q.option1}</li>}
                          {q.option2 && <li>{q.option2}</li>}
                          {q.option3 && <li>{q.option3}</li>}
                          {q.option4 && <li>{q.option4}</li>}
                        </ul>
                      </div>
                    ))
                  ) : (
                    <p>No questions available for this quiz.</p>
                  )}
                  <button
                    onClick={() => handleQuizSelection(quiz)}
                    style={{
                      backgroundColor: "blue",
                      color: "white",
                      padding: "10px",
                      border: "none",
                      cursor: "pointer",
                    }}
                  >
                    Choose
                  </button>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}

      {pinCode && (
        <div>
          <h2>Quiz PIN Code</h2>
          <p>
            Share this PIN code with others to allow them to join the quiz:{" "}
            <strong>{pinCode}</strong>
          </p>
        </div>
      )}
    </div>
  );
};

export default QuizManager;
