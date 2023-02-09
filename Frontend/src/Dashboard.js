import { useEffect, useState } from "react";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import React from "react";
import LoginForm from "./LoginForm";
import "./Dashboard.css"

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
      <div style={{ marginTop: '10vh' }}><h4> Welcome to your dashboard! </h4></div>
      </>
    );
  }
}

export default Dashboard