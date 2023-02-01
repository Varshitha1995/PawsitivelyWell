import React from "react";
import reactDom from "react-dom";
import App from "./src/App";
import Slide from "./src/Carousel";

reactDom.render(<App />, document.getElementById("root"));
reactDom.render(<Slide />, document.getElementById("info"));