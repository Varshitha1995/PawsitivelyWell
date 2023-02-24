import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import axios from "axios";
import "./loginStyles.css";

const AddDog = (props) => {
    const [success, setSuccess] = useState(false);
    const [mode, setMode] = useState("create");
    const [validAdd, setValidAdd] = useState(false);
    const [invalidMsg, setInvalidMsg] = useState(false);

    var breeds = [
        { value: '', text: '--Choose an option--' },
        { value: 'Husky', text: 'Husky' },
        { value: 'Shih Tzu', text: 'Shih Tzu' },
        { value: 'Labrador', text: 'Labrador' },
        { value: 'Pomeranian', text: 'Pomeranian' },
    ]

    const onClose = () => {
        setSuccess(false);
        setValidAdd(false);
        setInvalidMsg(false);
        props.onClose();
    }

    function onAddEvent(event) {
        var data = new FormData();
        console.log(String(event.target.form[1].value))
        data.append('dogId', event.target.form[1].value)
        data.append('emailId', props.emailId)
        const url = "http://localhost:8080/pawsitivelywell/dog/addDog"

        axios.post(url, data, {
            headers: {
                "mimeType": "multipart/form-data",
                "contentType": false,
            }
        }).then((response) => {
            console.log(response);
            if (response.data) {
                setValidAdd(true);
                setInvalidMsg(false);
                console.log("Dog added successfully")
            } else {
                setValidAdd(false);
                setInvalidMsg(true);
                console.log("Dog addition failed")
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }

    function onSubmit(event) {
        var data = new FormData();
        data.append('dogName', event.target.form[1].value)
        data.append('breed', event.target.form[2].value)
        data.append('emailId', props.emailId)
        data.append('age', event.target.form[3].value)
        data.append('weight', event.target.form[4].value)
        const url = "http://localhost:8080/pawsitivelywell/dog/createDog"

        axios.post(url, data, {

        }).then((response) => {
            if (response.data) {
                setSuccess(true);
                console.log("Dog created successfully")
            } else {
                setSuccess(false);
                console.log("Dog creation failed")
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }

    const renderAdd = () => {
        return (
            <div>
                <div>
                    <form className="form-horizontal form-loanable">
                        <fieldset>

                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Dog ID</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="number"
                                        name="dogId"
                                        id="dogId"
                                        className="form-control"
                                        placeholder="Enter DogID"
                                    // onChange={this.handleChange}
                                    />
                                </div>
                            </div>

                        </fieldset>
                        <div className="form-action">
                            <input
                                type="submit"
                                className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); onAddEvent(e); }} value="Add Dog" />
                        </div>

                        {invalidMsg && <div>
                            <span className="alert">Invalid dog Id!</span>
                        </div>}
                        {validAdd && <div>
                            <span className="success-alert">Dog Profile added successfully!</span>
                        </div>}
                    </form>
                    <p>Go to the Edit <img src="./images/edit.png" style={{ height: "2vw", width: "2vw" }} />  section to find the ID of the dog you want to add to your account.</p>

                </div>
                <a
                    href="#"
                    onClick={e => {
                        e.preventDefault();
                        setMode("create");
                    }}
                >
                    Create Dog profile
                </a>
            </div>
        );
    };

    const renderCreate = () => {
        return (
            <div>
                <form className="form-horizontal form-loanable">
                    <fieldset>
                        <div className="form-group has-feedback required">
                            <label htmlFor="login-email" className="col-sm-5">Dog Name</label>
                            <div className="col-sm-7">
                                <span className="form-control-feedback" aria-hidden="true"></span>
                                <input
                                    type="text"
                                    name="email"
                                    id="login-email"
                                    className="form-control"
                                    placeholder="Pupper"
                                    // onChange={this.handleEmailChange}
                                    required
                                />
                            </div>
                        </div>
                        <div className="form-group has-feedback required">
                            <label htmlFor="login-password" className="col-sm-5">Breed</label>
                            <div className="col-sm-7">
                                <span className="form-control-feedback" aria-hidden="true"></span>
                                <div className="login-password-wrapper">
                                    <select>{breeds.map((option, index) => (<option className="form-control" key={index} value={option.value}>{option.text}</option>))}</select>
                                </div>
                            </div>
                        </div>
                        <div className="form-group has-feedback required">
                            <label htmlFor="login-email" className="col-sm-5">Age</label>
                            <div className="col-sm-7">
                                <span className="form-control-feedback" aria-hidden="true"></span>
                                <input
                                    type="number"
                                    name="email"
                                    id="login-email"
                                    className="form-control"
                                    placeholder="0"
                                    // onChange={this.handleEmailChange}
                                    required
                                />
                            </div>
                        </div>
                        <div className="form-group has-feedback required">
                            <label htmlFor="login-email" className="col-sm-5">Weight (in kg)</label>
                            <div className="col-sm-7">
                                <span className="form-control-feedback" aria-hidden="true"></span>
                                <input
                                    type="number"
                                    step="any"
                                    name="email"
                                    id="login-email"
                                    className="form-control"
                                    placeholder="0"
                                    // onChange={this.handleEmailChange}
                                    required
                                />
                            </div>
                        </div>
                    </fieldset>
                    <div className="form-action">
                        <input
                            type="submit"
                            className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); onSubmit(e); }} value="Create Dog Profile" />
                    </div>
                    {success && <div>
                            <span className="success-alert">Dog Profile created successfully!</span>
                        </div>}
                </form>
                <a
                    href="#"
                    onClick={e => {
                        e.preventDefault();
                        setMode("add");
                    }}
                >
                    Add Dog Profile
                </a>
            </div>
        );
    };

    return (
        <div>
            <Modal
                show={props.showModal}
                onHide={onClose}
                //onSubmit={this.onSubmit}
                className="loginBox"
            >
                <Modal.Header closeButton={true} className="modalHeader">
                    <h3>{mode === "create" ? "Create Dog Profile" : "Add Existing Dog Profile"}</h3>
                </Modal.Header>
                <Modal.Body>
                    {mode === "create" ? (renderCreate()) : (renderAdd())}
                </Modal.Body>
            </Modal>
        </div>
    );
}

export default AddDog;