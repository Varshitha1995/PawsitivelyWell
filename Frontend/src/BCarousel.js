import Carousel from "react-bootstrap/Carousel";
import React from "react";

function BCarousel() {
  return (
    <Carousel style={{ marginTop: 60 }}>
      <Carousel.Item interval={1000}>
        <img
          className="d-block"
          src="./images/feeding-dog.jpg"
          height={500}
          width={1500}
          alt="First slide"
        />
        <Carousel.Caption>
          <h3 style={{ color: "black" }}>First slide label</h3>
          <p style={{ color: "black" }}>
            Nulla vitae elit libero, a pharetra augue mollis interdum.
          </p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={1000}>
        <img
          className="d-block"
          src="./images/feeding-dog.jpg"
          height={500}
          width={1500}
          alt="Second slide"
        />
        <Carousel.Caption>
          <h3 style={{ color: "black" }}>Second slide label</h3>
          <p style={{ color: "black" }}>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
          </p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block"
          src="./images/feeding-dog.jpg"
          height={500}
          width={1500}
          alt="Third slide"
        />
        <Carousel.Caption>
          <h3 style={{ color: "black" }}>Third slide label</h3>
          <p style={{ color: "black" }}>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
          </p>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>
  );
}

export default BCarousel;
