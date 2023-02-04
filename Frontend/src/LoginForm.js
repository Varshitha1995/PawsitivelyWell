import React from "react";
import {Modal} from "react-bootstrap";
import axios from "axios";
import * as EmailValidator from 'email-validator';
import "./loginStyles.css";
import { withRouter } from "./withRouter";

class LoginForm extends React.Component {
  constructor() {
    super();
    this.state = {
      showModal: false,
      smShow: false,
      email: "",
      password: "",
      mode: "login",
      validSession: false,
      invalidMsg: false,
      validEmail:true
    };
    
  }


  setMode = mode => {
    this.setState({
      mode
    });
  };

  setInvalidMsg = invalidMsg => {
    this.setState({
      invalidMsg
    });
  }

  setValidSession = validSession => {
    this.setState({
      validSession
    });
  }

  setValidEmail = validEmail => {
    this.setState({
      validEmail
    });
  }

  handleClick = (event) => {
    this.setInvalidMsg(false)
    var data = {}
    const params = new URLSearchParams({
        emailId: event.target.form[1].value,
        password: event.target.form[2].value
      }).toString();
    const url = "http://localhost:8080/pawsitivelywell/user/login?"+params;

    axios.post(url, data ,{

    }).then( (response)  => {
        if(response.data){
            console.log("Login success")
            this.setValidSession(true)
            this.props.navigate('/dashboard')
        }else{
          console.log("Login failed")
          this.setValidSession(false)
          this.setInvalidMsg(true)
          this.setMode("login");
        }
      })
      .catch(function (error) {
        console.log(error);
      });
}

handleChange = () =>{
  this.setInvalidMsg(false)
}

handleEmailChange = (e) =>{
  this.setValidEmail(EmailValidator.validate(e.target.value))
}

handleRegister = (event) => {
  this.setInvalidMsg(false)
    var data = new FormData();
    data.append('firstName', event.target.form[1].value)
    data.append('lastName', event.target.form[2].value)
    data.append('emailId', event.target.form[3].value)
    data.append('password', event.target.form[4].value)
    const url = "http://localhost:8080/pawsitivelywell/user/signup"

    axios.post(url, data ,{

    }).then( (response)  => {
        if(response.data){
            console.log("Register success")
            this.setValidSession(true)
            // this.setMode("login");
        }else{
          console.log("Register failed")
          this.setValidSession(false)
          this.setInvalidMsg(true)
          this.setMode("register");
        }
      })
      .catch(function (error) {
        console.log(error);
      });
}

   renderRegister = () => {
    return (
      <div>
        <div>
          <form className="form-horizontal form-loanable">
            <fieldset>
                
            <div className="form-group has-feedback required">
                <label htmlFor="login-email" className="col-sm-5">FirstName</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <input
                    type="text"
                    name="firstName"
                    id="firstName"
                    className="form-control"
                    placeholder="Enter FirstName"
                    onChange={this.handleChange}
                  />
                </div>
                </div>
                <div className="form-group has-feedback required">
                <label htmlFor="login-email" className="col-sm-5">LastName</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <input
                    type="text"
                    name="LastName"
                    id="LastName"
                    className="form-control"
                    placeholder="Enter LastName"
                    onChange={this.handleChange}
                  />
                </div>
                </div>

              <div className="form-group has-feedback required">
                <label htmlFor="login-email" className="col-sm-5">Email</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <input
                    type="text"
                    name="email"
                    id="login-email"
                    className="form-control"
                    placeholder="Enter Email"
                    onChange={this.handleEmailChange}
                  />
                </div>
              </div>
              {!this.state.validEmail && <div>
              <span className="alert">Invalid Email!</span>
              </div>}
              <div className="form-group has-feedback required">
                <label htmlFor="login-password" className="col-sm-5">Password</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <div className="login-password-wrapper">
                    <input
                      type="password"
                      name="password"
                      id="login-password"
                      className="form-control"
                      placeholder="*****"
                      required
                      onChange={this.handleChange}
                    />

                  </div>
                </div>
              </div>
            </fieldset>
            <div className="form-action">
             {/*  <button
                type="submit"
                className="btn btn-lg btn-primary btn-left">Create Account<span className="icon-arrow-right2 outlined"></span></button> */}
              
              <input
                type="submit"
                className="btn btn-lg btn-primary btn-left" onClick={e => {e.preventDefault(); this.handleRegister(e);}} value="Create Account" />
            </div>

            {this.state.invalidMsg && <div>
              <span className="alert">EmailId already exists!</span>
              </div>}
              {this.state.validSession && <div>
              <span className="success-alert">User Account Created! Please LogIn</span>
              </div>}
          </form>
        
        </div>
        <a
          href="#"
          onClick={e => {
            e.preventDefault();
            this.setMode("login");
          }}
        >
          Log in here
        </a>
      </div>
    );
  }; 

  renderLogin = () => {
    return (
      <div>
          <form className="form-horizontal form-loanable">
            <fieldset>
              <div className="form-group has-feedback required">
                <label htmlFor="login-email" className="col-sm-5">Email</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <input
                    type="text"
                    name="email"
                    id="login-email"
                    className="form-control"
                    placeholder="Enter email"
                    defaultValue={this.state.email}
                    onChange={this.handleEmailChange}
                    required
                  />
                </div>
              </div>
              {!this.state.validEmail && <div>
              <span className="alert">Invalid Email!</span>
              </div>}
              <div className="form-group has-feedback required">
                <label htmlFor="login-password" className="col-sm-5">Password</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <div className="login-password-wrapper">
                    <input
                      type="password"
                      name="password"
                      id="login-password"
                      className="form-control"
                      placeholder="*****"
                      onChange={this.handleChange}
                      required
                      defaultValue={this.state.password}
                    />
                  </div>
                </div>
              </div>
            </fieldset>
            <div className="form-action">
              <input
                type="submit"
                className="btn btn-lg btn-primary btn-left" onClick={e => {e.preventDefault(); this.handleClick(e);}} value="Login" />
            </div>
            {this.state.invalidMsg && <div>
              <span className="alert">Invalid Credentials</span>
              </div>}
          </form>
       <a
          href="#"
          onClick={e => {
            e.preventDefault();
            this.setMode("register");
          }}
        >
        Create your account
        </a>
      </div>
    );
  };

  render() {
    return (
      <div>
        <Modal
          show={this.props.showModal}
          onHide={this.props.onClose}
          onSubmit={this.onSubmit}
        >
          <Modal.Header closeButton={true}>
            <h2>{ this.state.mode === "login" ? "Login" : "Register" }</h2>
          </Modal.Header>
          <Modal.Body>
            {this.state.mode === "login" ? (this.renderLogin()) : (this.renderRegister())}
          </Modal.Body>
        </Modal>
      </div>
    );
  }
}

export default withRouter(LoginForm);
