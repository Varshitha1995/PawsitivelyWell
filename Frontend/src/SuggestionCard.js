import React, {useState, useEffect} from 'react';
import axios from "axios";
import "./FoodTracking.css"

function SuggestionCard({ dogId, dogName }) {

  const [isLoading, setLoading] = useState(true);
  const [cups, setCups] = useState();
  const [cals, setCals] = useState();
  const params = new URLSearchParams({
    dogId: dogId
}).toString();
const url = "http://localhost:8080/pawsitivelywell/dog/recommendedFood?" + params;
useEffect(() => {
    axios.get(url).then((response) => {
        if (response.data) {
            setLoading(false);
            setCups(response.data.cups);
            setCals(response.data.cals);
        }
    })
        .catch(function (error) {
            console.log(error);
        });
}, [dogId]);
if (!isLoading) {
  return (
    <div className="suggestion-card" /* style={{marginTop:"1vw"}} */>
      <h2>{dogName}'s Feeding Recommendation</h2>
      <img src='./images/dogbowl.avif' style={{height:"8vw", float:"left", marginRight:"1vw", marginBottom:"1vw", paddingBottom:"1vw"}}></img>
      <h2 style={{float:"inherit", color:"black", fontFamily:"serif"}}>{cups} cup(s)</h2><p style={{float:"left"}}> or {cals} calories of food per day</p>
      <br/>
      <br/>
    </div>
  );
}else {
  return (
      <div className="suggestion-card">Loading...</div>
  )
}
}

export default SuggestionCard;
