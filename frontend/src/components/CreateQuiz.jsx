import { useState } from "react";
import "../components_style/CreateQuiz.css";

const CreateQuiz = () => {
  const [quizTitle, setQuizTitle] = useState("");
  const [question, setQuestion] = useState("");
  const [answers, setAnswers] = useState(["", "", "", ""]);
  const [correctAnswer, setCorrectAnswer] = useState(null);
  const [questions, setQuestions] = useState([]);
  const [error, setError] = useState("");
  const userId = localStorage.getItem("userId");

  const handleQuizTitleChange = (e) => {
    setQuizTitle(e.target.value);
    setError("");
  };

  const handleQuestionChange = (e) => {
    setQuestion(e.target.value);
    setError("");
  };

  const handleAnswerChange = (index, value) => {
    const updatedAnswers = [...answers];
    updatedAnswers[index] = value;
    setAnswers(updatedAnswers);
    setError("");
  };

  const handleCorrectAnswerChange = (index) => {
    setCorrectAnswer(index);
    setError("");
  };

  const handleAddQuestion = () => {
    if (!question.trim()) {
      setError("Please enter a question.");
      return;
    }

    if (answers.some((answer) => !answer.trim())) {
      setError("Please fill in all answer fields.");
      return;
    }

    if (correctAnswer === null) {
      setError("Please select a correct answer.");
      return;
    }

    const newQuestion = {
      questionTitle: question,
      answers,
      correctAnswerIndex: correctAnswer,
    };

    setQuestions([...questions, newQuestion]);
    setQuestion("");
    setAnswers(["", "", "", ""]);
    setCorrectAnswer(null);
    setError("");
  };

  const handleDone = async (e) => {
    e.preventDefault();

    if (!quizTitle.trim()) {
      setError("Please enter a quiz title.");
      return;
    }

    if (questions.length === 0) {
      setError("Please add at least one question.");
      return;
    }

    for (const question of questions) {
      if (
        !question.questionTitle.trim() ||
        !question.answers.some((a) => a.trim())
      ) {
        setError(
          "Please ensure each question has a title and at least one answer."
        );
        return;
      }
    }

    const formattedQuestions = questions.map((q) => ({
      questionTitle: q.questionTitle,
      option1: q.answers[0],
      option2: q.answers[1],
      option3: q.answers[2],
      option4: q.answers[3],
      rightAnswer: q.answers[q.correctAnswerIndex],
    }));

    try {
      const response = await fetch(
        `http://localhost:8080/quiz/createQuiz?userId=${userId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            quizTitle,
            questions: formattedQuestions,
          }),
        }
      );

      if (response.ok) {
        console.log("Formatted Questions:", formattedQuestions);
        alert("Quiz successfully created!");
        setQuizTitle("");
        setQuestions([]);
        setAnswers(["", "", "", ""]);
        setCorrectAnswer(null);
      } else {
        const errorMessage = await response.text();
        alert(`Failed to create quiz: ${errorMessage}`);
      }
    } catch (error) {
      console.error("Error:", error);
      alert("An error occurred. Please try again.");
    }
  };

  return (
    <div className="create-quiz-container">
      <h2>Create Quiz</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div>
        <input
          type="text"
          placeholder="Enter Quiz Title"
          value={quizTitle}
          onChange={handleQuizTitleChange}
        />
      </div>
      <div>
        <textarea
          placeholder="Enter your question here..."
          value={question}
          onChange={handleQuestionChange}
        />
      </div>
      {answers.map((answer, index) => (
        <div className="answer-container" key={index}>
          <input
            type="text"
            value={answer}
            onChange={(e) => handleAnswerChange(index, e.target.value)}
            placeholder={`Answer ${index + 1}`}
          />
          <input
            type="radio"
            name="correctAnswer"
            checked={correctAnswer === index}
            onChange={() => handleCorrectAnswerChange(index)}
          />
        </div>
      ))}
      <button onClick={handleAddQuestion}>Add Question</button>
      <div>
        {questions.length > 0 && (
          <div className="added-questions-list">
            <h3>Added Questions:</h3>
            <ul>
              {questions.map((q, idx) => (
                <li key={idx}>
                  {q.questionTitle} ({q.answers[q.correctAnswerIndex]})
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
      <button
        onClick={handleDone}
        disabled={!quizTitle.trim() || questions.length === 0}
      >
        Done
      </button>
    </div>
  );
};

export default CreateQuiz;
