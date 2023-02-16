import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import React, { useState, useEffect } from "react";
import "./Dashboard.css";
import "./sidebar.css";
import { useLocation } from 'react-router-dom';
import { Navigate } from "react-router-dom";
import axios from "axios";


const Dashboard = () => {
  const [isLoading, setLoading] = useState(true);
  const [dogs, setDogs] = useState([]);
  let validSession = false;
  let emailId = null;
  const location = useLocation();
  if (location.state != null) {
    validSession = location.state.validSession;
    emailId = location.state.emailId;
  }
  const params = new URLSearchParams({
    emailId: emailId
  }).toString();
  const url = "http://localhost:8080/pawsitivelywell/user/getdogs?" + params;
  useEffect(() => {
    axios.get(url).then((response) => {
      if (response.data) {
        setDogs(response.data);
        console.log(dogs);
        setLoading(false);
      }
    })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  if (isLoading) {
    return <div className="App">Loading...</div>;
  }

  function ListDogs(props) {
    var path = './dogImages',
      imgs = ['dog1.png', 'dog2.png', 'dog3.png', 'dog4.png', 'dog5.png'],
      i = Math.floor(Math.random() * imgs.length);
    console.log(path + imgs[i]);
    return (
      <a id="Dogs" href="javascript:void(0)">
        <Avatar src={path + '/' + imgs[i]} className="rightIcon" style={{ height: '80%', width: '80%' }}></Avatar>{props.value}
      </a>);
  }

  if (validSession && !isLoading) {
    return (
      <>
        <div className="root">
          <AppBar>
            <Toolbar>
              <img
                src="./logo-no-background.png"
                alt="Logo"
                width="55vh"
                height="55vh"
                className="logo"
              />
              <Typography variant="h6" className="appName" style={{ marginLeft: '2%', flexGrow: 1 }} >
                Pawsitively Well!
              </Typography>
              {/* <Menu secondary>
            <Menu.Item name="home" />
            <Menu.Item name="messages" />
            <Menu.Item name="friends" />
          </Menu> */}
              {/* <Typography variant="subtitle1" className={classes.subtitle}>The purrrfect app for your pupper!</Typography> */}
              <a href="/" style={{ float: 'right', color: 'white', paddingRight: '2px' }}>Logout</a>
              <Avatar className="userIcon" src="/userIcon.png" style={{ margin: '1%', cursor: 'pointer' }}></Avatar>
            </Toolbar>
          </AppBar>
        </div>
        <div className="vertical-menu">
          <a id="Food-Tracking" href="javascript:void(0)" style={{ paddingTop: '5vh' }}>
            <Avatar src="./images/food.png" className="leftIcon" style={{ height: '80%', width: '80%' }}></Avatar>Food
          </a>
          <a id="Activity-Tracking" href="javascript:void(0)">
            <Avatar src="./images/activity.png" className="leftIcon" style={{ height: '80%', width: '80%' }}></Avatar>Activities
          </a>
          <a id="Medicine-And-Vaccine-Tracking" href="javascript:void(0)">
            <Avatar src="./images/vaccine.png" className="leftIcon" style={{ height: '80%', width: '80%' }}></Avatar>Medicines
          </a>
          <a id="Grooming-Tracking" href="javascript:void(0)">
            <Avatar src="./images/grooming.jpg" className="leftIcon" style={{ height: '80%', width: '80%' }}></Avatar>Grooming
          </a>
          <a id="Information" href="javascript:void(0)">
            <Avatar src="./images/info.jpg" className="leftIcon" style={{ height: '80%', width: '80%' }}></Avatar>Info
          </a>
          <a id="Edit" href="javascript:void(0)">
            <Avatar src="./images/edit.png" className="leftIcon" style={{ height: '80%', width: '80%' }}></Avatar>Edit
          </a>
        </div>
        <div className="center-page" >
          <img src="./images/healthTracking.jpg" width="100%" />
          <h3 style={{ textAlign: 'center' }}>Under Construction</h3>
        </div>
        <div className="vertical-menu-right">
          {dogs.map((dog) =>
            <ListDogs key={dog.dogName} value={dog.dogName} />
          )}
          <a id="AddDog" href="javascript:void(0)">
            <Avatar src="./images/add_dog.png" className="rightIcon" style={{ height: '80%', width: '80%' }}></Avatar>Add
          </a>
        </div>

      </>
    );
  }
  else {
    return (
      <>
        <Navigate to="/" replace={true} />
        Login!
      </>
    );
  }
}


export default Dashboard