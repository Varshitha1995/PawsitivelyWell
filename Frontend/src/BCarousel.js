import Carousel from "react-bootstrap/Carousel";
import React from "react";

function BCarousel() {
  return (
    <Carousel style={{ marginTop: 60 }}>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/feeding-dog.jpg"
          height="700"
          width="100%"
          alt="First slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>Food Tracking</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
            Find out what's best to feed your dog and keep track of it. Never double feed again!
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/run.jpg"
          height="700"
          width="100%"
          alt="Second slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>Activity Tracking</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
            Get suggestions on how much exercise your pupper needs. Be reminded of spending quality time!
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/vaccine.png"
          height="700"
          width="100%"
          alt="Third slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>Medicine and Vaccination Tracking</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
            Never miss out on a vaccination appointment again! Track and be reminded of any medicines for your pawsome friend
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/sick-dog.jpg"
          height="700"
          width="100%"
          alt="Third slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>Health Recommendations</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
          Find the symptoms to look out for in your best friend when they're feeling down.
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/grooming.jpg"
          height="700"
          width="100%"
          alt="Third slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>Grooming Tracking</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
            Keep a note of their grooming needs so they look their best always!
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/dogs.jpg"
          height="700"
          width="100%"
          alt="Third slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>Easy to share</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
            Each user account can track multiple dogs and each dog profile can be share with multiple accounts for easy sharing!
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={2500}>
        <img
          className="d-block"
          src="./carousel/placeholder.jpg"
          height="700"
          width="100%"
          alt="Third slide"
        />
        <Carousel.Caption style={{ backgroundColor:"rgb(201, 224, 242)", marginBottom:"2vh", border: "2px dashed darkblue"}}>
          <h2 style={{ color: "black"}}>We care about your friend</h2>
          <h3 style={{ color: "black", marginTop: "-2vh", paddingBottom:"1vh"}}>
            We look into every user's feedback to improve our application. We are rooting for the paws!
          </h3>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}

export default BCarousel;
