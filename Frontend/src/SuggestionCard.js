import React, {useState, useEffect} from 'react';
import axios from "axios";

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
    <div className="suggestion-card" style={{marginTop:"1vw"}}>
      <h3>{dogName}'s Recommended Feeding Routine</h3>
      <p>{cups} cup(s) or {cals} calories of food is recommended per day</p>
      <p>Distribute the food in 2-3 feedings</p>
      <p>For pregnant or nursing dogs, we recommended free-choice feeding</p>
    </div>
  );
}else {
  return (
      <div className="suggestion-card">Loading...</div>
  )
}
}

export default SuggestionCard;
