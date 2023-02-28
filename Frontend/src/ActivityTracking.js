import React, { useState } from "react";
import "./ActivityTracking.css";
import { AppBar, Toolbar, Typography } from '@material-ui/core';
import TextField from '@mui/material/TextField';
import { blue, blueGrey } from "@material-ui/core/colors";




const ActivityTracking = ({dogName}) => {

  return (
    <div>
        <div style={{marginTop: '2%'}} className="container">
            <div>
            <div class="ui menu" >
            <h2 class="ui icon header">
            <i class="paw icon"></i>
  Activity Tracker for puppers!!
</h2>
            </div>
            </div>
           <div class="ui segment">
              <h2 class="ui right floated header">Pupper Name</h2>
              <h3>Choose a date to display the activity of pupper</h3>
              <div class="ui calendar" id="calendar">
                <div class="ui input left icon">
                   <i class="calendar icon"></i>
                   <input type="text" placeholder="Date/Time"></input>
                </div>
              </div>
              <div class="ui clearing divider">
              </div>
              <div class="ui form">
                <div class="field">
                   <label>Activity Done by the pupper</label>
                   <textarea></textarea>
                </div>
              </div>
              <div>
              <button class="ui primary basic button" style={{ height: '10%', width: '10%' , marginLeft: '90%', marginTop: '1%'}}>Delete</button>
              </div>
            </div>
            <div class="ui segment">
              <h2 class="ui right floated header">Suggestions for the Pupper</h2>
              <div class="ui clearing divider">
              </div>
              <div class="ui styled fluid accordion">
                <div class="title">
                  <i class="dropdown icon"></i>
                   Walking or Running period to be spent by the pupper
                 </div>
                  <div class="content">
                   <p class="transition hidden">A dog is a type of domesticated animal. Known for its loyalty and faithfulness, it can be found as a welcome guest in many households across the world.</p>
                  </div>
                <div class="title">
                  <i class="dropdown icon"></i>
                   Calories to be burnt per day
                </div>
                  <div class="content">
                  <p class="transition hidden">There are many breeds of dogs. Each breed varies in size and temperament. Owners often select a breed of dog that they find to be compatible with their own lifestyle and desires from a companion.</p>
                  </div>
                  <div class="title">
                  <i class="dropdown icon"></i>
                   Activities that can be included for the pupper
                   </div>
                  <div class="content">
                   <p>Three common ways for a prospective owner to acquire a dog is from pet shops, private owners, or shelters.</p>
                   <p>A pet shop may be the most convenient way to buy a dog. Buying a dog from a private owner allows you to assess the pedigree and upbringing of your dog before choosing to take it home. Lastly, finding your dog from a shelter, helps give a good home to a dog who may not find one so readily.</p>
                  </div>
               </div>
            </div>
        </div>
    </div>
  );
}

export default ActivityTracking;