import React from "react";
import { useState, useEffect } from 'react';
import axios from "axios";

const GroomingTracking = ({ dogId, dogName }) => {
    let today = new Date().toISOString().slice(0, 16);

    const [routineActive, setRoutineActive] = useState(false);
    const [success, setSuccess] = useState(false);
    const [trackActive, setTrackActive] = useState(false);
    const [trackSuccess, setTrackSuccess] = useState(false);
    const [frequency, setFrequency] = useState(0);
    const [trackedData, setTrackedData] = useState([]);
    const [loading, setLoading] = useState(true);


    var groomingTypes = [
        { value: 'Bath', text: 'Bath' },
        { value: 'Brush', text: 'Brush' },
        { value: 'Nail trim', text: 'Nail trim' },
        { value: 'Ear care', text: 'Ear care' },
        { value: 'Haircut', text: 'Haircut' },
    ]
    function handleToggle() {
        setRoutineActive(!routineActive);
    }
    function handleTrackToggle() {
        setTrackActive(!trackActive);
    }
    function onChange() {
        setTrackSuccess(false);
        setSuccess(false);
    }

    const params = new URLSearchParams({
        dogId: dogId
    }).toString();

    const url = "http://localhost:8080/pawsitivelywell/dog/getGroomingRoutine?" + params;
    const trackedDataUrl = "http://localhost:8080/pawsitivelywell/dog/getTrackedGrooming?" + params;

    useEffect(() => {
        setSuccess(false);
        setTrackSuccess(false);
        setFrequency();
        axios.get(url).then((response) => {
            if (response.data) {
                setFrequency(response.data.frequency);
            }
        })
            .catch(function (error) {
                console.log(error);
            });


        axios.get(trackedDataUrl).then((response) => {
            if (response.data) {
                var storedData = [];
                response.data.forEach(element => {
                    const obj = JSON.parse(element);
                    let formatteddate = new Intl.DateTimeFormat('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(obj.timestamp * 1000)
                    storedData.push({ "date": formatteddate, "type": obj.type })
                });
                setTrackedData(storedData);
                setLoading(false);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }, [dogId]);

    function saveRoutine(event) {
        var data = new FormData();
        data.append("dogId", dogId);
        data.append("routine", "{\"frequency\":" + event.target.form[1].value + "}")
        const url = "http://localhost:8080/pawsitivelywell/dog/saveGroomingRoutine"

        axios.post(url, data, {

        }).then((response) => {
            if (response.data) {
                setSuccess(true);
            } else {
                setSuccess(false);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }
    function trackGrooming(event) {
        let TS = Math.floor(new Date(event.target.form[2].value).getTime() / 1000)
        var data = new FormData();
        data.append("dogId", dogId);
        data.append("data", "{\"type\":\"" + event.target.form[1].value + "\",\"timestamp\":" + TS + "}");
        const url = "http://localhost:8080/pawsitivelywell/dog/trackGrooming"

        axios.post(url, data, {

        }).then((response) => {
            if (response.data) {
                setTrackSuccess(true);
            } else {
                setTrackSuccess(false);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }

    function ListTrackedGrooming(props) {
        return (
            <li className="table-row">
                <div className="col col-1" data-label="Type">{props.type}</div>
                <div className="col col-2" data-label="Date">{props.date}</div>
            </li>
        )
    }

    return (
        <div className="container">
            <div className="heading">
                <h1>{dogName}'s Grooming Tracking</h1>
            </div>
            <div className="card-element">
                <SuggestionCard dogId={dogId} dogName={dogName} />
            </div>
            <div style={{ margin: "1vw", border: "2px solid #5B42F3", padding: "1vw", backgroundColor: "rgb(201, 224, 242)" }}>
                <button className={"collapsible" + routineActive ? "active" : ""} onClick={handleToggle}>
                    <h2>Grooming Routine</h2>
                </button>
                <div className={routineActive ? "activeContent" : "content"}>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Groom every x week(s)</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="number"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="0"
                                        defaultValue={frequency}
                                        key={frequency}
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>

                            <div className="form-action">
                                <input
                                    type="submit" style={{ marginTop: "2vw" }}
                                    className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); saveRoutine(e); }} value="Save" />
                            </div>
                        </fieldset>
                        {success && <div>
                            <span className="success-alert">Dog grooming routine updated successfully!</span>
                        </div>}
                    </form>
                </div>
            </div>
            <div style={{ margin: "1vw", border: "2px solid #5B42F3", padding: "1vw", backgroundColor: "rgb(201, 224, 242)" }}>
                <button className={"collapsible" + trackActive ? "active" : ""} onClick={handleTrackToggle}>
                    <h2>Track Grooming</h2>
                </button>
                <div className={trackActive ? "activeContent" : "content"}>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Grooming Type</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <div className="login-password-wrapper">
                                        <select style={{ paddingRight: "2em", paddingBottom: "0.5em", paddingTop: "0.5em", borderRadius: "5px" }}>{groomingTypes.map((option, index) => (<option className="form-control" key={index} value={option.value}>{option.text}</option>))}</select>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Date of grooming</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="datetime-local"
                                        max={today}
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="0"
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="form-action">
                                <input
                                    type="submit" style={{ marginTop: "2vw" }}
                                    className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); trackGrooming(e); }} value="Save" />
                            </div>
                        </fieldset>
                        {trackSuccess && <div>
                            <span className="success-alert">Dog grooming tracked successfully!</span>
                        </div>}
                    </form>
                </div>
            </div>
            <ul className="responsive-table">
                <li className="table-header">
                    <div className="col col-1">Grooming Type</div>
                    <div className="col col-2">Date</div>
                </li>
                {!loading && trackedData.map((data) => <ListTrackedGrooming key={data.type + data.date} type={data.type} date={data.date} />)}
            </ul>
        </div>
    );
}

function SuggestionCard({ dogId, dogName }) {

    const [isLoading, setLoading] = useState(true);
    const [freq, setFreq] = useState();
    const params = new URLSearchParams({
        dogId: dogId
    }).toString();
    const url = "http://localhost:8080/pawsitivelywell/dog/recommendedGrooming?" + params;
    useEffect(() => {
        axios.get(url).then((response) => {
            if (response.data) {
                setLoading(false);
                setFreq(response.data);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }, [dogId]);
    if (!isLoading) {
        return (
            <div className="suggestion-card" style={{ marginTop: "1vw" }}>
                <h2>{dogName}'s Recommended Grooming Routine</h2>
                <img src='./images/groomIcon.png' style={{ height: "8vw", float: "left", marginRight: "1vw", marginBottom: "1vw", paddingBottom: "1vw" }}></img>
                <br />
                <div><h2 style={{ float: "inherit", color: "black", fontFamily: "serif" }}>{freq} week(s)</h2><p style={{ float: "left" }}>is the recommended duration between grooming sessions</p></div>
                <br />
                <br />
            </div>
        );
    } else {
        return (
            <div className="suggestion-card">Loading...</div>
        )
    }
}

export default GroomingTracking;