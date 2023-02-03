import React, { Component } from "react";
import {
  Navbar,
  NavDropdown,
  MenuItem,
  NavItem,
  Nav,
  Popover,
  Tooltip,
  Button,
  Modal,
  OverlayTrigger
} from "react-bootstrap";
import axios from "axios";

import CreateNewAccount from "./CreateNewAccount";

class LoginForm extends Component {
  constructor() {
    super();
    this.state = {
      showModal: false,
      smShow: false,
      email: "",
      password: "",
      mode: "login"
    };
  }

  setMode = mode => {
    this.setState({
      mode
    });
  };


  handleClick= (event) => {
    console.log("Handling event");
    console.log(event);
    var data = {}
    const params = new URLSearchParams({
        emailId: event.target.form[1].value,
        password: event.target.form[2].value
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




  renderRegister = () => {
    return (
      <div>
        <div>
          <form className="form-horizontal form-loanable">
            <fieldset>
              <div className="form-group has-feedback required">
                <label htmlFor="login-email" className="col-sm-5">Username or email</label>
                <div className="col-sm-7">
                  <span className="form-control-feedback" aria-hidden="true"></span>
                  <input
                    type="text"
                    name="email"
                    id="login-email"
                    className="form-control"
                    placeholder="Enter email"
                    required
                  />
                </div>
              </div>
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
                    />
                    
                  </div>
                </div>
              </div>
            </fieldset>
            <div className="form-action">
              <button
                type="submit"
                className="btn btn-lg btn-primary btn-left">Create Account<span className="icon-arrow-right2 outlined"></span></button>
              
            </div>
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
                    required
                  />
                </div>
              </div>
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

export default LoginForm;
