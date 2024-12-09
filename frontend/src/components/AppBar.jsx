// AppBar.js
import "../components_style/AppBar.css";

const AppBar = () => {
  return (
    <header className="app-bar">
      <div className="app-bar-logo">
        <h1>Quiz App</h1>
      </div>
      <nav className="app-bar-nav">
        <ul>
          <li>
            <a href="#home">Home</a>
          </li>
          <li>
            <a href="#about">About</a>
          </li>
          <li>
            <a href="#contact">Contact</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default AppBar;
