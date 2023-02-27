import React, { Component } from "react";
import { Router, Routes, Route } from "react-router-dom";

import App from "./src/App";
import FoodTracking from "./src/FoodTracking";

export default class MyRoutes extends Component {
    render() {
        return (
            // <Router>
                <Routes>
                {/* <Switch> */}
                    <Route path="/" exact element={<App />} />
                    <Route path="/dashboard" exact element={<FoodTracking />} />
                {/* </Switch> */}
                </Routes>
            // </Router>
        )
    }
}