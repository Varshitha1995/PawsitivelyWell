import React from "react";
import { useState } from 'react';
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import BCarousel from "./BCarousel";
import LoginForm from "./LoginForm";

const App = () => {
  return (
    <div>
      <Ribbon />
      <div style={{ height: 60 }}></div>
    </div>
  );
};
const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    position: "sticky",
  },
  appName: {
    flexGrow: 1,
    marginLeft: theme.spacing(2),
    color: "white",
  },
  subtitle: {
    flexGrow: 1,
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(1),
    paddingTop: "2vh",
    fontSize: "3vw",
    fontWeight: "600",
    // color: "#3f51b5",
    //color: "#7FDBFF",
    color: "darkblue",
    textAlign: "center",
    opacity: "1.0",
  },
  userIcon: {
    margin: theme.spacing(1),
    cursor: "pointer",
  },
  logo: {
    width: 55,
    height: 55,
    marginRight: theme.spacing(2),
  },
}));

const Ribbon = () => {
  const classes = useStyles();

  const [showModal, setShowModal] = useState(false);
  const close = () => {
    setShowModal((showModal) => !showModal)
  }
  const open = () => {
    setShowModal((showModal) => !showModal)
  }


  return (
    <div className={classes.root}>
      <AppBar>
        <Toolbar>
          <img
            src="./logo-no-background.png"
            alt="Logo"
            className={classes.logo}
          />
          <Typography variant="h6" className={classes.appName}>
            Pawsitively Well!
          </Typography>
          <Avatar className={classes.userIcon} onClick={open} src="/broken-image.jpg"></Avatar>
          <LoginForm showModal={showModal} onClose={close} />
        </Toolbar>
      </AppBar>
      <div>
        <BCarousel />
        <div style={{ width: "100%" }}>
          <Typography variant="subtitle1" className={classes.subtitle} style={{ textShadow: "0 0 5px blue, 0 0 2px lightblue" }} >The purrrfect app for your pupper!</Typography>
          <Typography variant="subtitle1" className={classes.subtitle} style={{ color: "darkblue" }}>Curated with love by</Typography>
          <br />
          <center>
            <img src="./WeDoTech.png" alt="WeDoTech logo" style={{ alignItems: "center", height: "10vw" }} />
          </center>
          <br />
          <br />
        </div>
      </div>
    </div >
  );
};

export default App;
