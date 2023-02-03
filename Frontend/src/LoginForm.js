
import React from "react";
import "./loginStyles.css";
import axios from "axios";

function handleClick(event) {
    var data = {}
    const params = new URLSearchParams({
        emailId: event.target.form[0].value,
        password: event.target.form[1].value
      }).toString();

    const url = "http://localhost:8080/pawsitivelywell/user/login?"+params;

    axios.post(url, data ,{

    }).then(function (response) {
        console.log(response);
        if(response.data){
            console.log("Login success")
        }
      })
      .catch(function (error) {
        console.log(error);
      });
}


const LoginForm = ({ isShowLogin }) => {

    const form = document.querySelector("form");
    if (form) {
        form.addEventListener("submit", (e) => {
        e.preventDefault();
    });}
  return (
    <div className={`${isShowLogin ? "active" : ""} show`}>
      <div className="login-form">
        <div className="form-box solid">
          <form>
            <h1 className="login-text">SIGN IN</h1>
            <label>EmailId</label>
            <br></br>
            <input type="text" name="username" className="login-box" />
            <br></br>
            <label>Password</label>
            <br></br>
            <input type="password" name="password" className="login-box" />
            <br></br>
            <input type="submit" value="LOGIN" className="login-btn" onClick={handleClick} />
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
