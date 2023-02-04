import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router } from 'react-router-dom';
import App from "./src/App";
import MyRoutes from "./MyRoutes";

ReactDOM.render(
     <Router>
        <div className="App">
            <MyRoutes />
        </div>
     </Router>
    ,
    document.getElementById('root')
);
/* const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<App />); */

// reactDom.render(<App />, document.getElementById("root"));
// reactDom.render(<C />, document.getElementById("info"));
