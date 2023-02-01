import React from "react";
import Slider from "infinite-react-carousel";


function Slide() {
  const settings = {
    autoplay: true,
    autoplaySpeed: 5000,
    dots: true,
    virtualList: true,
    duration: 20,
    arrows:true,
  };
  return (
    <div>
      
      <Slider {...settings}>
        <div>
        <div>
          <img
          height= "300px"
          object-fit= "cover"
          margin-left= "auto"
          margin-right="auto"
          display="block"
          src="./images/feeding-dog.jpg"
            alt="Feeding Dog"
          />
          </div>
          <h5>
            Food Tracking
          </h5>
          <p>
            Get your dog's nutrition right and keep track of it among multiple accounts!
          </p>
        </div>
        <div>
          <img
          src="./images/feeding-dog.jpg"
          margin-left= "auto"
          margin-right="auto"
          height= "300px"
          object-fit= "cover"
          display="block"
            alt="Feeding Dog"
          />
         <h5>
            Food Tracking
          </h5>
          <p>
            Get your dog's nutrition right and keep track of it among multiple accounts!
          </p>
        </div>
        <div>
          <img
          src="./images/feeding-dog.jpg"
          margin-left= "auto"
          margin-right="auto"
          height= "300px"
          object-fit= "cover"
          display="block"
            alt="Feeding Dog"
          />
          <h5>
            Food Tracking
          </h5>
          <p>
            Get your dog's nutrition right and keep track of it among multiple accounts!
          </p>
        </div>
      </Slider>
    </div>
  );
}

export default Slide;