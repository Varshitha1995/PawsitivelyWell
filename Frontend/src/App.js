import React from "react";
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Avatar from '@material-ui/core/Avatar';


const App = () =>{
    return (
        <div>
            <Ribbon />
            <div style={{ height :60 }}></div>
            {/* <Cards /> */}
        </div>
    )
}
const useStyles = makeStyles((theme) => ({
    root: {
      flexGrow: 1,
      position: "absolute"
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
    },
    logo: {
        width: 55,
        height: 55,
        marginRight: theme.spacing(2),
      },
  }));


const Ribbon = () =>{
    const classes = useStyles();
  
    return (
      <div className={classes.root}>
        <AppBar /* style={{ background: "#7FDBFF" }} */>
          <Toolbar>
          <img src="./logo-no-background.png" alt="Logo" className={classes.logo} />
            <Typography variant="h6" className={classes.appName}>
              Pawsitively Well!
            </Typography>
            {/* <Typography variant="subtitle1" className={classes.subtitle}>The purrrfect app for your pupper!</Typography> */}
            <Avatar className={classes.userIcon} src="/broken-image.jpg"></Avatar>
          </Toolbar>
        </AppBar>
      </div>
    );
  }

export default App