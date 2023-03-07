import React from "react";
import Tooltip from "@uiw/react-tooltip";
import HeatMap from "@uiw/react-heat-map";
import { useState, useEffect } from 'react';
import axios from "axios";
import "./MedicineTracking.css"

const MedicineTracking = ({ dogName, dogId }) => {
    let today = new Date().toISOString().slice(0, 16);

    const [routineActive, setRoutineActive] = useState(false);
    const [success, setSuccess] = useState(false);
    const [vName, setVName] = useState();
    const [frequency, setFrequency] = useState();
    const [trackActive, setTrackActive] = useState(false);
    const [trackSuccess, setTrackSuccess] = useState(false);

    const [trackedData, setTrackedData] = useState([]);
    const [loading, setLoading] = useState(true);

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

    const url = "http://localhost:8080/pawsitivelywell/dog/getVaccinationRoutine?" + params;
    const trackedDataUrl = "http://localhost:8080/pawsitivelywell/dog/getTrackedVaccination?" + params;

    useEffect(() => {
        setSuccess(false);
        setTrackSuccess(false);
        setVName();
        setFrequency();
        axios.get(url).then((response) => {
            if (response.data) {
                setVName(response.data.vName);
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
                    storedData.push({ "date": formatteddate, "vName": obj.vName })
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
        data.append("routine", "{\"vName\":\"" + event.target.form[1].value + "\", \"frequency\":" + event.target.form[2].value + "}")
        const url = "http://localhost:8080/pawsitivelywell/dog/saveVaccinationRoutine"

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

    function trackVaccination(event) {
        let TS = Math.floor(new Date(event.target.form[2].value).getTime() / 1000)
        var data = new FormData();
        data.append("dogId", dogId);
        data.append("data", "{\"vName\":\"" + event.target.form[1].value + "\",\"timestamp\":" + TS + "}");
        const url = "http://localhost:8080/pawsitivelywell/dog/trackVaccination"

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

    function ListTrackedVaccines(props) {
        return (
            <li className="table-row">
                <div className="col col-1" data-label="Vaccine">{props.vName}</div>
                <div className="col col-2" data-label="Date">{props.date}</div>
            </li>
        )
    }

    return (
        <div className="container">
            <div className="heading">
                <h1>{dogName}'s Medicine Tracking</h1>
            </div>
            <div style={{ margin: "1vw", border: "2px solid #5B42F3", padding: "1vw", backgroundColor: "rgb(201, 224, 242)" }}>
                <button className={"collapsible" + routineActive ? "active" : ""} onClick={handleToggle}>
                    <h2>Vaccine Routine</h2>
                </button>
                <div className={routineActive ? "activeContent" : "content"}>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Vaccine Name</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="text"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="vaccine"
                                        defaultValue={vName}
                                        key={vName}
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Frequency per year</label>
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
                            <span className="success-alert">Dog vaccine routine updated successfully!</span>
                        </div>}
                    </form>
                </div>
            </div>
            <div style={{ margin: "1vw", border: "2px solid #5B42F3", padding: "1vw", backgroundColor: "rgb(201, 224, 242)" }}>
                <button className={"collapsible" + trackActive ? "active" : ""} onClick={handleTrackToggle}>
                    <h2>Track Vaccination</h2>
                </button>
                <div className={trackActive ? "activeContent" : "content"}>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Vaccine Name</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="text"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="vaccine"
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Date of vaccination</label>
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
                                    className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); trackVaccination(e); }} value="Save" />
                            </div>
                        </fieldset>
                        {trackSuccess && <div>
                            <span className="success-alert">Dog vaccination tracked successfully!</span>
                        </div>}
                    </form>
                </div>
            </div>
            <ul className="responsive-table">
                <li className="table-header">
                    <div className="col col-1">Vaccine Name</div>
                    <div className="col col-2">Date</div>
                </li>
                {!loading && trackedData.map((data) => <ListTrackedVaccines key={data.vName + data.date} vName={data.vName} date={data.date} />)}
            </ul>
            <div className="card-element">
                <SuggestionCard dogId={dogId} dogName={dogName} />
            </div>
            <div className="card-element">
                <SymptomSuggestionCard dogId={dogId} dogName={dogName} />
            </div>
        </div>
    );
}

function SuggestionCard({ dogId, dogName }) {
    return (
        <div className="suggestion-card" style={{ marginTop: "2vw" }}>
            <h3>{dogName}'s Recommended Vaccination Routine</h3>
            <p>Rabies vaccine is recommended every year</p>
            <p>Leptospirosis, Lyme, Canine influenza are also a vital part of {dogName}'s care routine</p>
            <p>DAP is recommended only for pups upto 3 months</p>
        </div>
    );
}

function SymptomSuggestionCard({ dogId, dogName }) {
    return (
        <div className="suggestion-card" style={{ marginTop: "-1vw" }}>
            <h3>Symptoms to look out for in {dogName}</h3>
            <p>{dogName} is susceptible to the following issues:</p>
            <p>---To be retrieved from ML model---</p>
        </div>
    );
}

export default MedicineTracking;