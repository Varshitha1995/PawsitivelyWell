import React, { Component } from "react";
import { Router, Routes, Route } from "react-router-dom";

import App from "./src/App";
import Dashboard from "./src/Dashboard";

export default class MyRoutes extends Component {
    render() {
        return (
            // <Router>
                <Routes>
                {/* <Switch> */}
                    <Route path="/" exact element={<App />} />
                    <Route path="/dashboard" exact element={<Dashboard />} />
                {/* </Switch> */}
                </Routes>
            // </Router>
        )
    }
}