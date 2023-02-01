import React from "react";
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

const Cards = () =>{
  return(
    <div>
      <ActionAreaCard />
    </div>
  )
}

const useStyles = () => ({
  root: {
    flexGrow: 1,
    position: "absolute",
    top: 1600,
  },
  cardContent: {
    textAlign: "center",
  },

});

const ActionAreaCard = () =>{
  const classes = useStyles();
  //const [index, setIndex] = useState(0);

  /* useEffect(() => {
    const intervalId = setInterval(() => {
      console.log("Run every 5 seconds!");
    }, 5000); // Change interval in milliseconds
    return () => clearInterval(intervalId);
  }, []); */

  return (
    <Card /* sx={{ maxWidth: 345 }} */ className={classes.root}>
      <CardActionArea>
        <CardMedia
          component="img"
          height="340"
          src="./images/feeding-dog.jpg"
          // image="../public/images/feeding-dog.jpg"
          alt="feeding dog"
        />
        <CardContent>
          <Typography gutterBottom variant="h5" component="div" className={classes.cardContent}>
            Food Tracking
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Get your dog's nutrition right and keep track of it among multiple accounts!
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

export default Cards