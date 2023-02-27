import React from 'react';

function SuggestionCard({ suggestion }) {
  return (
    <div className="suggestion-card">
      <h3>{suggestion.name}</h3>
      <p>Protein: {suggestion.protein}, Fat: {suggestion.fat}, Carbs: {suggestion.carbs}</p>
      <p>Ingredients: {suggestion.ingredients}</p>
    </div>
  );
}

export default SuggestionCard;
