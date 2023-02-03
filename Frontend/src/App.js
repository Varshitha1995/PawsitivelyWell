import React from "react";
import {useState} from 'react';
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import Avatar from "@material-ui/core/Avatar";
import BCarousel from "./BCarousel";
import { Icon, Menu } from "semantic-ui-react";
import LoginForm from "./LoginForm";

const App = () => {
  const [isShowLogin, setIsShowLogin] = useState(true);
  const handleLoginClick = () => {
        setIsShowLogin((isShowLogin) => !isShowLogin);
  };
  return (
    <div>
      <Ribbon />
      {/* <LoginForm isShowLogin={isShowLogin} /> */}
      <div style={{ height: 60 }}></div>
      {/* <Cards /> */}
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
    color: "#001F3F",
  },
  subtitle: {
    flexGrow: 1,
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(4),
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
  const [isShowLogin, setIsShowLogin] = useState(true);
  const handleLoginClick = () => {
        setIsShowLogin((isShowLogin) => !isShowLogin);
  };

  const handleClick = () =>{
    handleLoginClick()
  }

  return (
    <div className={classes.root}>
      <AppBar /* style={{ background: "#7FDBFF" }} */>
        <Toolbar>
          <img
            src="./logo-no-background.png"
            alt="Logo"
            className={classes.logo}
          />
          <Typography variant="h6" className={classes.appName}>
            Pawsitively Well!
          </Typography>
          {/* <Menu secondary>
            <Menu.Item name="home" />
            <Menu.Item name="messages" />
            <Menu.Item name="friends" />
          </Menu> */}
          {/* <Typography variant="subtitle1" className={classes.subtitle}>The purrrfect app for your pupper!</Typography> */}
          <Avatar className={classes.userIcon} onClick={handleClick} src="/broken-image.jpg"></Avatar>

          {/* <Icon fitted name="user" /> */}
        </Toolbar>
      </AppBar>
      <LoginForm isShowLogin={isShowLogin} />
      <div>
        <BCarousel />
        <div
          style={{
            marginLeft: "20%",
            marginRight: "20%",
            marginTop: 50,
            marginBottom: 50,
          }}
        >
          <div
            className="ui segment"
            style={{ backgroundColor: "rgb(223, 208, 226)" }}
          >
            <h1 className="ui header">100% Satisfaction Guarantee</h1>
            As a brand with integrity, Loyall® Signature is truly confident in
            the quality of the products we carefully craft. Customer <br />
            satisfaction is of utmost importance to our brand, therefore we
            guarantee 100% satisfaction to ensure that you and your pet will be{" "}
            <br />
            delighted with your purchase. If for any reason you are not 100%
            satisfied, we will refund your purchase. Go ahead and try Loyall®
            Signature today.
          </div>
          <div
            className="ui segment"
            style={{ backgroundColor: "rgb(201, 224, 242)" }}
          >
            <h1 className="ui header">Excellence in Nutrition</h1>
            Our top-notch nutrition team utilizes ingredient knowledge collected
            from over 100 years of animal nutrition expertise. Our formulas are{" "}
            <br />
            designed in-house and supported by cutting-edge industry research.
            With Loyall® Signature you get the full package science, <br />
            industry-leading technology and passion, with the commitment to
            always do the right thing.
          </div>
          <div
            className="ui segment"
            style={{ backgroundColor: "rgb(196, 221, 218)" }}
          >
            <h1 className="ui header">Our Values</h1>
            We are driven by the passion of the bond developed between you and
            your pet. This bond exemplifies the impressive and unique connection{" "}
            <br />
            that exists between a person and their pet. We see the importance of
            this bond that can be understood through simple gestures, such as a{" "}
            <br />
            loving look. For this bond to last an enduring lifetime, we provide
            unmatched access to better nutrition for your dogs’ and cats’ health
            and well being.
          </div>
        </div>
      </div>
    </div>
  );
};

export default App;
