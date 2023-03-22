import React from "react";
import Tooltip from "@uiw/react-tooltip";
import HeatMap from "@uiw/react-heat-map";
import { useState, useEffect } from 'react';
import axios from "axios";
import "./ActivityTracking.css";

const ActivityTracking = ({ dogName, dogId }) => {
    let today = new Date().toISOString().slice(0, 16);

    const [routineActive, setRoutineActive] = useState(false);
    const [trackActive, setTrackActive] = useState(false);
    const [quantity, setQuantity] = useState(0);
    const [success, setSuccess] = useState(false);
    const [trackSuccess, setTrackSuccess] = useState(false);
    const [trackedData, setTrackedData] = useState([]);
    const [mins, setMins] = useState(0);
    const [class1, setClass1] = useState(0);
    const [class2, setClass2] = useState(0);
    const [class3, setClass3] = useState(0);
    const [class4, setClass4] = useState(0);
    const [loading, setLoading] = useState(true);
    const [recoLoading, setRecoLoading] = useState(true);
    const [underEx, setUnderEx] = useState(false);
    const [wellEx, setWellEx] = useState(false);
    const [overEx, setOverEx] = useState(false);

    function handleToggle() {
        setRoutineActive(!routineActive);
    }
    function handleTrackToggle() {
        setTrackActive(!trackActive);
    }
    function onChange() {
        setTrackSuccess(false);
        setSuccess(false);
        setQuantity(0);
    }

    const params = new URLSearchParams({
        dogId: dogId
    }).toString();

    const url = "http://localhost:8080/pawsitivelywell/dog/getActivityRoutine?" + params;
    const trackedDataUrl = "http://localhost:8080/pawsitivelywell/dog/getTrackedActivity?" + params;

    useEffect(() => {
        setSuccess(false);
        setTrackSuccess(false);
        setWellEx(false);
        setUnderEx(false);
        setOverEx(false);
        axios.get(url).then((response) => {
            if (response.data) {
                setQuantity(response.data.minutes);
                setMins(Math.round((response.data.minutes) + 1));
                setClass1(Math.round((response.data.minutes) / 4 + 1));
                setClass2(Math.round((response.data.minutes) / 2 + 1));
                setClass3(Math.round((3 * (response.data.minutes)) / 4 + 1));
                setClass4(Math.round((response.data.minutes) * 2 + 1));
                setRecoLoading(false);
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
                    const i = storedData.findIndex(e => e.date == formatteddate)
                    if (i > -1) {
                        storedData[i] = { "date": formatteddate, "count": (obj.minutes) + storedData[i].count }
                    } else
                        storedData.push({ "date": formatteddate, "count": obj.minutes })
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
        data.append("routine", "{\"minutes\":" + event.target.form[1].value + "}")
        const url = "http://localhost:8080/pawsitivelywell/dog/saveActivityRoutine"

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

    function trackActivity(event) {
        let TS = Math.floor(new Date(event.target.form[2].value).getTime() / 1000)
        var data = new FormData();
        data.append("dogId", dogId);
        data.append("data", "{\"minutes\":" + event.target.form[1].value + ",\"timestamp\":" + TS + "}");
        const url = "http://localhost:8080/pawsitivelywell/dog/trackActivity"

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

    function setInsights(){
        if(!loading && !recoLoading){
          let avg = 0;
          for(let data in trackedData) {
            avg +=trackedData[data].count
          }
          avg = avg/trackedData.length;
          if(avg>0){
          if(avg<class3)
            setUnderEx(true);
          else if(class3<=avg && avg<=mins)
            setWellEx(true);
          else
            setOverEx(true);
          }
        }
      }

    var colors = {}
    colors[0] = "#e3fde4"
    colors[class1] = "#4ae54a"
    colors[class2] = "#30cb00"
    colors[class3] = "#0f9200"
    colors[mins] = "#006203"
    colors[class4] = "#18392b"
    colors[500] = "#000"

    return (
        <div className="container">
            <div className="heading">
                <h1>{dogName}'s Activity Tracking</h1>
            </div>
            
            <div className="card-element">
                <SuggestionCard dogId={dogId} dogName={dogName} />
            </div>
            <div style={{ margin: "1vw", border: "2px solid #5B42F3", padding: "1vw", backgroundColor: "rgb(201, 224, 242)" }}>
                <button className={"collapsible" + routineActive ? "active" : ""} onClick={handleToggle}>
                    <h2>Activity Routine</h2>
                </button>
                <div className={routineActive ? "activeContent" : "content"}>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Daily exercise time (In minutes)</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="number"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="0"
                                        defaultValue={quantity}
                                        key={quantity}
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
                            <span className="success-alert">Dog Activity routine updated successfully!</span>
                        </div>}
                    </form>
                </div>
            </div>
            <div style={{ margin: "1vw", border: "2px solid #5B42F3", padding: "1vw", backgroundColor: "rgb(201, 224, 242)" }}>
                <button className={"collapsible" + trackActive ? "active" : ""} onClick={handleTrackToggle}>
                    <h2>Track Activity</h2>
                </button>
                <div className={trackActive ? "activeContent" : "content"}>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Exercise time (In minutes)</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="number"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="0"
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Time of exercise</label>
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
                                    className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); trackActivity(e); }} value="Save" />
                            </div>
                        </fieldset>
                        {trackSuccess && <div>
                            <span className="success-alert">Dog activity tracked successfully!</span>
                        </div>}
                    </form>
                </div>
            </div>
            <div style={{ margin: "1vw", border: "2px dashed #5B42F3", padding: "1vw" }} onMouseMove={setInsights}>
                {!loading && !recoLoading && <HeatMap
                    value={trackedData}
                    startDate={new Date("2023/02/01")}
                    monthLabels={['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']}
                    space={4}
                    rectSize={14}
                    height="16.5em"
                    width="100%"
                    rectProps={{
                        rx: 5,
                    }}
                    panelColors={
                        colors
                    }
                    legendCellSize={0}
                    legendRender={(props) => <rect {...props} y={props.y + 10} rx={5} />}
                    rectRender={(props, data) => {
                        return (
                            <Tooltip
                                key={props.key}
                                placement="top"
                                content={`${data.date}: ${data.count || 0} minutes`}
                            >
                                <rect {...props} />
                            </Tooltip>
                        );
                    }}
                />}
                <div style={{ float: "left", backgroundColor: "#0f9200", height: "1em", width: "1em" }}></div>
                <div style={{ float: "left", backgroundColor: "transparent", height: "0.3em", width: "0.3em" }}></div>
                <div style={{ float: "left", backgroundColor: "#006203", height: "1em", width: "1em" }}></div>
                <p>&nbsp;: Keep getting those perfect greens
                </p>
                <div style={{ float: "left", backgroundColor: "#18392b", height: "1em", width: "1em" }}></div>
                <div style={{ float: "left", backgroundColor: "transparent", height: "0.3em", width: "0.3em" }}></div>
                <div style={{ float: "left", backgroundColor: "#000", height: "1em", width: "1em" }}></div>
                <p>&nbsp;: Make sure {dogName} gets enough rest and continue if they're excited for high activity levels
                </p>
                <div style={{ float: "left", backgroundColor: "#30cb00", height: "1em", width: "1em" }}></div>
                <p>&nbsp;: Increase the activity time so that {dogName} can stay healthy
                </p>
            </div>
        {wellEx && <div className="suggestion-card" style={{backgroundColor: "#4BB543"}}>
            <img src='./images/activedog.png' style={{height:"8vw", float:"left", marginRight:"1vw", marginBottom:"1vw", paddingBottom:"1vw"}}></img>
            <br/>
            <h2 style={{float:"inherit", color:"black", fontFamily:"serif", marginTop:"-1vw"}}>Well done!</h2>
            <p style={{float:"left", fontWeight: "bold", fontSize: "15pt"}}>{dogName}'s exercise routine is right on track.</p>
            <br/>
            <br/>
            <br/>
            </div>}
        {underEx && <div className="suggestion-card" style={{backgroundColor: "#FFCC00"}}>
            <img src='./images/lonelydog.png' style={{height:"8vw", float:"left", marginRight:"1vw", marginBottom:"1vw", paddingBottom:"1vw"}}></img>
            <br/>
            <h2 style={{float:"inherit", color:"black", fontFamily:"serif", marginTop:"-1vw"}}>Warning!</h2>
            <p style={{float:"left", fontWeight: "bold", fontSize: "15pt"}}>{dogName} doesn't seem to have good exercise.</p>
            <br/>
            <br/>
            <br/>
            </div>}
        {overEx && <div className="suggestion-card" style={{backgroundColor: "#FFCC00"}}>
            <img src='./images/tireddog.png' style={{height:"8vw", float:"left", marginRight:"1vw", marginBottom:"1vw", paddingBottom:"1vw"}}></img>
            <br/>
            <h2 style={{float:"inherit", color:"black", fontFamily:"serif", marginTop:"-1vw"}}>Warning!</h2>
            <p style={{float:"left", fontWeight: "bold", fontSize: "15pt"}}>{dogName} is being overexercised! Make sure {dogName} is not too tired.</p>
            <br/>
            <br/>
            <br/>
            </div>}
        </div>
    );
}



function SuggestionCard({ dogId, dogName }) {

    const [isLoading, setLoading] = useState(true);
    const [mins, setMinutes] = useState();
    const [miles, setMiles] = useState();
    const [pup, setPup] = useState();
    const [isPup, setIsPup] = useState(false);
    const params = new URLSearchParams({
        dogId: dogId
    }).toString();
    const url = "http://localhost:8080/pawsitivelywell/dog/recommendedActivity?" + params;
    useEffect(() => {
        axios.get(url).then((response) => {
            if (response.data) {
                setLoading(false);
                if (response.data.pup) {
                    setIsPup(true);
                    setPup(response.data.pup);
                } else {
                    setIsPup(false);
                    setMinutes(response.data.minutes);
                    setMiles(response.data.miles);
                }
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }, [dogId]);
    if (!isLoading) {
        return (
            <div className="suggestion-card" style={{ marginTop: "1vw" }}>
                <h2>{dogName}'s Recommended Activity Routine</h2>
                <img src='./images/dogwalk.png' style={{height:"8vw", float:"left", marginRight:"1vw", marginBottom:"1vw", paddingBottom:"1vw"}}></img>
                <br/>
                {isPup && <p><h2 style={{float:"inherit", color:"black", fontFamily:"serif"}}>{pup} miles </h2> of walk per day according to their energy</p>}
                {!isPup && <div><h2 style={{float:"inherit", color:"black", fontFamily:"serif"}}>{mins} minutes</h2><p style={{float:"left"}}> or {miles} miles of walk per day</p></div>}
                <br/>
                <br/>
            </div>
        );
    } else {
        return (
            <div className="suggestion-card">Loading...</div>
        )
    }
}

export default ActivityTracking;