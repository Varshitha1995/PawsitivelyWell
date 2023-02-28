import React from "react";
import Tooltip from "@uiw/react-tooltip";
import HeatMap from "@uiw/react-heat-map";
import { useState } from 'react';
import SuggestionCard from './SuggestionCard';
import "./FoodTracking.css";

const value = [
  { date: "2016/01/11", count: 2 },
  ...[...Array(17)].map((_, idx) => ({
    date: `2016/01/${idx + 10}`,
    count: idx,
  })),
  ...[...Array(17)].map((_, idx) => ({
    date: `2016/02/${idx + 10}`,
    count: idx,
  })),
  { date: "2016/04/12", count: 2 },
  { date: "2016/05/01", count: 5 },
  { date: "2016/05/02", count: 5 },
  { date: "2016/05/03", count: 1 },
  { date: "2016/05/04", count: 11 },
  { date: "2016/05/08", count: 32 },
]; // This is constant values
const cellStyle = {
  fontSize: '16px',
  color: '#000000',
  border: 'solid 1px #555555',
};


const foodSuggestions = [
  {
    id: 1,
    name: 'Chicken and Rice',
    protein: '26%',
    fat: '16%',
    carbs: '58%',
    ingredients: 'Chicken, brown rice, carrots, green beans'
  },
  {
    id: 2,
    name: 'Salmon and Sweet Potato',
    protein: '24%',
    fat: '18%',
    carbs: '58%',
    ingredients: 'Salmon, sweet potato, spinach, blueberries'
  },
  {
    id: 3,
    name: 'Turkey and Quinoa',
    protein: '28%',
    fat: '14%',
    carbs: '58%',
    ingredients: 'Turkey, quinoa, peas, carrots'
  },
];

const FoodTracking = ({ dogName }) => {
  return (
    <div className="container">
      <h1>Food Tracking Section</h1>
      <h5>Dog name{dogName}</h5>

      <div className="card-element">
      {foodSuggestions.map(suggestion => (
          <SuggestionCard key={suggestion.id} suggestion={suggestion} />
        ))}
        </div>
        <div>
      <button id= "add-routine-button"className="button">Add Routine</button>
      <button id = "track-routine-button"className="button">Track Routine</button>
      <button id ="add-alert-button" className="button">Add Alert</button>
      </div>
      <HeatMap
        value={value}
        width={600}
        startDate={new Date("2016/01/01")} // this is constant value
        panelColors={{
          0: "#f7fbff",
          2: "#deebf7",
          4: "#c6dbef",
          10: "#9ecae1",
          20: "#6baed6",
          30: "#4292c6",
        }}
        background="#c6dbef"
        cellStyle={cellStyle}
        rectRender={(props, data) => {
          // if (!data.count) return <rect {...props} />;
          return (
            <Tooltip
              key={props.key}
              placement="top"
              content={`count: ${data.count || 0}`}
            >
              <rect {...props} />
            </Tooltip>
          );
        }}
      />
    </div>
  );
};

export default FoodTracking;
