import React from "react";
import reactDom from "react-dom";
import App from "./src/App";
import Cards from "./src/FlashCards";

reactDom.render(<App />, document.getElementById("root"));
reactDom.render(<Cards />, document.getElementById("card"));