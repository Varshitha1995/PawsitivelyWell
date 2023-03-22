import React, { useState, useEffect } from "react";
import axios from "axios";

const InfoPage = ({ dogName }) => {
    const [dataList, setDataList] = useState();
    const [loading, setLoading] = useState(true);

    const params = new URLSearchParams({
        type: "vet"
    }).toString();
    const url = "http://localhost:8080/pawsitivelywell/info?" + params;
    useEffect(() => {
        axios.get(url).then((response) => {
            if (response.data) {
                setDataList(response.data)
                setLoading(false);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }, []);


    function onChange(event) {
        setLoading(true);
        const params = new URLSearchParams({
            type: event.target.value
        }).toString();
        const url = "http://localhost:8080/pawsitivelywell/info?" + params;
        axios.get(url).then((response) => {
            if (response.data) {
                setDataList(response.data)
                setLoading(false);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }

    return (
        <div style={{ marginLeft: "1vw" }}>
            <div className="heading">
                <h1>Information Page</h1>
            </div>
            <div>
                <form className="form-horizontal form-loanable">
                    <fieldset>
                        <div className="form-group has-feedback required">
                            <label htmlFor="login-password" className="col-sm-5" style={{ float: "left", width: "4vw", fontSize: "1em" }}>Type: </label>
                            <div className="col-sm-7">
                                <span className="form-control-feedback" aria-hidden="true"></span>
                                <div className="login-password-wrapper" style={{ float: "left" }}>
                                    <select style={{ paddingRight: "2em", paddingBottom: "0.5em", paddingTop: "0.5em", borderRadius: "5px", float: "left", marginRight: "1vw", fontSize: "1em" }} defaultValue="Veterinary hospital" key="vet" onChange={onChange}>
                                        <option className="form-control" key="vet" value="vet">Veterinary Hospitals / Clinics</option>
                                        <option className="form-control" key="daycare" value="daycare">Dog Boarding / Day Care</option>
                                        <option className="form-control" key="grooming" value="grooming">Grooming Services</option>
                                        <option className="form-control" key="dogpark" value="dogpark">Dog Parks</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div className="form-group has-feedback required">
                            <label htmlFor="login-password" className="col-sm-5" style={{ float: "left", width: "5vw", fontSize: "1em" }}>Location: </label>
                            <div className="col-sm-7">
                                <span className="form-control-feedback" aria-hidden="true"></span>
                                <div className="login-password-wrapper">
                                    <select style={{ paddingRight: "2em", paddingBottom: "0.5em", paddingTop: "0.5em", borderRadius: "5px", float: "left", fontSize: "1em" }} defaultValue="Windsor" key="Windsor" disabled>
                                        <option className="form-control" key="Windsor" value="Windsor">Windsor</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div>
                {!loading && dataList.map((data) => <SuggestionCard key={data.Name} name={data.Name} address={data.Address} contact={data.Contact} />)}
            </div>
        </div>
    );
}


function SuggestionCard({ name, address, contact }) {
    var img;
    var path = './infoImages',
        imgs = ['1.png', '2.png', '3.png', '4.png', '5.png', '6.png', '7.png', '8.png', '9.png'],
        i = Math.floor(Math.random() * imgs.length);
    img = path + '/' + imgs[i]

    return (
        <div className="suggestion-card" style={{ marginTop: "1vw", paddingBottom: "0px" }}>
            <h2 style={{ float: "inherit", color: "black", fontFamily: "serif", marginBottom: "0px", paddingTop: "0px" }}>{name}</h2>
            <img src={img} style={{ height: "8vw", float: "left", marginRight: "1vw", marginBottom: "1vw", paddingBottom: "1vw" }}></img>
            <br />
            <h3 style={{ float: "inherit", color: "black", fontFamily: "serif", marginTop: "0px", paddingTop: "0px" }}>{address}</h3>
            {contact && <div><h4 style={{ float: "inherit", color: "black", fontFamily: "serif", marginBottom: "0px", paddingBottom: "0px" }}>{contact}</h4></div>}
            {!contact && <div style={{marginBottom:"2vw"}}></div>}
            <br />
            <br />
        </div>
    );
}

export default InfoPage;