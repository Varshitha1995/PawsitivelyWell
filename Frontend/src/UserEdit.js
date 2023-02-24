import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import axios from "axios";
import "./loginStyles.css";

const UserEdit = (props) => {
    const [isLoading, setLoading] = useState(true);
    const [firstName, setFirstName] = useState();
    const [lastName, setLastName] = useState();
    const [emailId, setEmailId] = useState();
    const [success, setSuccess] = useState(false);
    const [password, setPassword] = useState();
    const params = new URLSearchParams({
        emailId: props.emailId
    }).toString();
    const url = "http://localhost:8080/pawsitivelywell/user/getDetails?" + params;
    axios.get(url).then((response) => {
        if (response.data) {
            setEmailId(props.emailId);
            setFirstName(response.data.firstName);
            setLastName(response.data.lastName);
            setPassword(response.data.password);
            setLoading(false);
        }
    })
        .catch(function (error) {
            console.log(error);
        });

    const onClose=()=>{
        setSuccess(false);
        props.onClose();
    }


    function onSubmit(event) {
        var data = new FormData();
        if (event.target.form[1].value != "")
            data.append('firstName', event.target.form[1].value)
        else
            data.append('firstName', firstName)
        if (event.target.form[2].value != "")
            data.append('lastName', event.target.form[2].value)
        else
            data.append('lastName', lastName)
        data.append('emailId', emailId)
        if (event.target.form[4].value != "")
            data.append('password', event.target.form[4].value)
        else
            data.append('password', password)
        const url = "http://localhost:8080/pawsitivelywell/user/updateUser"

        axios.post(url, data, {

        }).then((response) => {
            if (response.data) {
                setSuccess(true);
                console.log("Update success")
            } else {
                setSuccess(false);
                console.log("Update failed")
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }
    if (!isLoading) {
        return (
            <Modal
                show={props.showModal}
                onHide={onClose}
                // onSubmit={this.onSubmit}
                className="loginBox"
            >
                <Modal.Header closeButton={true} className="modalHeader">
                    <h3>Edit User Details</h3>
                </Modal.Header>
                <Modal.Body>
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
                                                placeholder={firstName}
                                            // onChange={this.handleChange}
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
                                                placeholder={lastName}
                                            // onChange={this.handleChange}
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
                                                style={{ cursor: "not-allowed" }}
                                                placeholder={emailId}
                                                readOnly
                                            // onChange={this.handleEmailChange}
                                            />
                                        </div>
                                    </div>
                                    {/* {!this.state.validEmail && <div>
              <span className="alert">Invalid Email!</span>
              </div>} */}
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
                                                    placeholder="*******"
                                                //   onChange={this.handleChange}
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
                                        className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); onSubmit(e); }} value="Update Account" />
                                </div>

                                {/* {this.state.invalidMsg && <div>
              <span className="alert">EmailId already exists!</span>
              </div>} */}
                                {success && <div>
                                    <span className="success-alert">User Details Updated</span>
                                </div>}
                            </form>

                        </div>
                    </div>
                </Modal.Body>
            </Modal>
        );
    } else {
        return (
            <Modal
                show={props.showModal}
                onHide={props.onClose}
                // onSubmit={this.onSubmit}
                className="loginBox"
            >
                <div className="App">Loading...</div>
            </Modal>
        );
    }
}

export default UserEdit;