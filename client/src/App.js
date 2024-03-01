//import logo from './logo.svg';
import './App.css';
import ItemQuantity from './components/ItemQuantity';
import './App.css';

function App() {
  return (
    /*<div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>*/
    <div className="container-fluid">
      <nav>
        <div className="nav-wrapper center-align">
          <a href="/" className="brand-logo">ItemQuantity</a>
        </div>
      </nav>
      <div className="row">
        <ItemQuantity />
      </div>
    </div>
  );
}

export default App;
