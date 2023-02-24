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
import FoodTracking from "./FoodTracking";
import ActivityTracking from "./ActivityTracking";
import MedicineTracking from "./MedicineTracking";
import GroomingTracking from "./GroomingTracking";
import InfoPage from "./InfoPage";
import EditPage from "./EditPage";
import UserEdit from "./UserEdit";
import AddDog from "./AddDog";


const Dashboard = () => {
  const [isLoading, setLoading] = useState(true);
  const [isFoodTracking, setFoodTracking] = useState(true);
  const [isActivity, setActivity] = useState(false);
  const [isMedicine, setMedicine] = useState(false);
  const [isGrooming, setGrooming] = useState(false);
  const [isInfo, setInfo] = useState(false);
  const [isEdit, setEdit] = useState(false);
  const [dogs, setDogs] = useState([]);
  const [selectedDog, setSelectedDog] = useState();
  const [selectedDogId, setSelectedDogId] = useState();
  const [showModal, setShowModal] = useState(false);
  const [showDogModal, setShowDogModal] = useState(false);

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
        if (response.data.length > 0){
          setSelectedDog(response.data[0].dogName);
          setSelectedDogId(response.data[0].dog_id);
        }
        setLoading(false);
      }
    })
      .catch(function (error) {
        console.log(error);
      });
  }, []);

  const close = () => {
    setShowModal((showModal) => !showModal)
  }
  const open = () => {
    setShowModal((showModal) => !showModal)
  }

  const dogClose = () => {
    setShowDogModal((showDogModal) => !showDogModal)
  }
  const dogOpen = () => {
    setShowDogModal((showDogModal) => !showDogModal)
  }

  if (isLoading) {
    return <div className="App">Loading...</div>;
  }

  function onOptionChange(e) {
    setSelectedDog(e.target.value)
    setSelectedDogId(e.target.name);
  }

  function ListDogs(props) {
      var img;
      if(props.photo == null){
        var path = './dogImages',
      imgs = ['dog1.png', 'dog2.png', 'dog3.png', 'dog4.png', 'dog5.png'],
      i = Math.floor(Math.random() * imgs.length);
        img = path + '/' + imgs[i]
      }else{
        const data = props.photo
        img = `data:image/jpeg;base64,${data}`
      }
    return (
      <label className="AddDog">
        <input type="radio" name={props.id} value={props.value} onChange={onOptionChange} />
        <img src={img} className="rightIcon" style={{ height: '80%', width: '80%' }} />{props.value}
      </label>);
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
              <Avatar className="userIcon" src="/userIcon.png" style={{ margin: '1%', cursor: 'pointer' }} onClick={open}/>
              <UserEdit showModal={showModal} emailId={emailId} onClose={close} />
            </Toolbar>
          </AppBar>
        </div>
        <div className="vertical-menu" style={{ paddingTop: '3vw' }}>
          <button id="Food-Tracking" className="button" onClick={() => { setFoodTracking(true); setActivity(false); setMedicine(false); setGrooming(false); setInfo(false); setEdit(false); }}>
            <Avatar src="./images/food.png" className="leftIcon" style={{ height: '80%', width: '80%' }} />
          </button><div style={{ textAlign: "center" }}>Food</div>
          <button id="Activity-Tracking" className="button" onClick={() => { setFoodTracking(false); setActivity(true); setMedicine(false); setGrooming(false); setInfo(false); setEdit(false); }}>
            <Avatar src="./images/activity.png" className="leftIcon" style={{ height: '80%', width: '80%' }} />
          </button><div style={{ textAlign: "center" }}>Activity</div>
          <button id="Medicine-And-Vaccine-Tracking" className="button" onClick={() => { setFoodTracking(false); setActivity(false); setMedicine(true); setGrooming(false); setInfo(false); setEdit(false); }}>
            <Avatar src="./images/vaccine.png" className="leftIcon" style={{ height: '80%', width: '80%' }} />
          </button><div style={{ textAlign: "center" }}>Medicines</div>
          <button id="Grooming-Tracking" className="button" onClick={() => { setFoodTracking(false); setActivity(false); setMedicine(false); setGrooming(true); setInfo(false); setEdit(false); }}>
            <Avatar src="./images/grooming.jpg" className="leftIcon" style={{ height: '80%', width: '80%' }} />
          </button><div style={{ textAlign: "center" }}>Grooming</div>
          <button id="Information" className="button" onClick={() => { setFoodTracking(false); setActivity(false); setMedicine(false); setGrooming(false); setInfo(true); setEdit(false); }}>
            <Avatar src="./images/info.jpg" className="leftIcon" style={{ height: '80%', width: '80%' }} />
          </button><div style={{ textAlign: "center" }}>Info</div>
          <button id="Edit" className="button" onClick={() => { setFoodTracking(false); setActivity(false); setMedicine(false); setGrooming(false); setInfo(false); setEdit(true); }}>
            <Avatar src="./images/edit.png" className="leftIcon" style={{ height: '80%', width: '80%' }} />
          </button><div style={{ textAlign: "center" }}>Edit</div>
        </div>
        <div className="center-page" >
          {/* <img src="./images/healthTracking.jpg" width="100%" />
          <h3 style={{ textAlign: 'center' }}>Under Construction</h3> */}
          {isFoodTracking && <FoodTracking dogName={selectedDog} />}
          {isActivity && <ActivityTracking dogName={selectedDog} />}
          {isMedicine && <MedicineTracking dogName={selectedDog} />}
          {isGrooming && <GroomingTracking dogName={selectedDog} />}
          {isInfo && <InfoPage dogName={selectedDog} />}
          {isEdit && <EditPage dogName={selectedDog} dogId={selectedDogId} emailId={emailId}/>}
        </div>
        <div className="vertical-menu-right">
          {dogs.map((dog) =>
            <ListDogs key={dog.dog_id} id={dog.dog_id} value={dog.dogName} photo={dog.photo}/>
          )}
          <button className="AddDog">
            <Avatar src="./images/add_dog.png" className="rightIcon" style={{ height: '80%', width: '80%' }} onClick={dogOpen}/>Add
          </button>
          <AddDog showModal={showDogModal} emailId={emailId} onClose={dogClose}/>
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