import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import React from "react";
import "./Dashboard.css";
import "./sidebar.css"
import { AiFillHome, AiFillInfoCircle, AiFillMail, AiFillSetting } from "react-icons/ai";

class Dashboard extends React.Component {
  constructor() {
    super();
  }

  render() {
    /* const [showModal, setShowModal] = useState(false);
  const close = () => {
    setShowModal((showModal) => !showModal)
  }
  const open = () => {
    setShowModal((showModal) => !showModal)
  } */
    return (
      <>
       <div className="root">
        <AppBar /* style={{ background: "#7FDBFF" }} */>
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
          <Avatar className="userIcon" src="/broken-image.jpg" style={{ margin: '1%', cursor: 'pointer' }}></Avatar>
          {/* <LoginForm showModal={showModal} onClose = {close} /> */}
          {/* <Icon fitted name="user" /> */}
        </Toolbar>
      </AppBar>
      </div>
      <div className="vertical-menu">
        <a id="Food-Tracking" href="/">
        <Avatar src="./logo-no-background.png" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar>
         Food Tracking
        </a>
        <a id="Activity-Tracking" href="/about">
        <Avatar src="./logo-no-background.png" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar> Activity tracking
        </a>
        <a id="Medicine-And-Vaccine-Tracking"  href="/contact">
        <Avatar src="./logo-no-background.png" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar>Medicine And Vaccine Tracking
        </a>
        <a id="Grooming-Tracking" href="">
        <Avatar src="./logo-no-background.png" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar> Grooming Tracking
        </a>
        <a id="Information" href="">
        <Avatar src="./logo-no-background.png" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar> Information
        </a>
      </div>
      <div className="vertical-menu-right">
        <a id="home" href="/">
        <Avatar src="/broken-image.jpg" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar> Pet-1
        </a>
        <a id="about" href="/about">
        <Avatar src="/broken-image.jpg" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar>Pet-2
        </a>
        <a id="contact"  href="/contact">
        <Avatar src="/broken-image.jpg" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar> Pet-3
        </a>
        <a id="settings" href="">
        <Avatar src="/broken-image.jpg" style={{marginLeft:'32%', cursor:'pointer', marginTop:'10%'}}></Avatar>Pet-4
        </a>
      </div>
      <div className="center-page">
        <a>
          Hey There
        </a>
      </div>
      </>
    );
  }
}

export default Dashboard