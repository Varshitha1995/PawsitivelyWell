from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder
import pandas as pd
from flask import Flask, jsonify, request
from flask_cors import CORS, cross_origin

# Load the data
data = pd.read_csv('Final Data.csv')

# Encode the Breed column using LabelEncoder
le = LabelEncoder()
data['Breed_Encoded'] = le.fit_transform(data['Breed'])

# Split the data into features (X) and target (y)
X = data[['Breed_Encoded', 'Age', 'Weight']]
y = data['Health_Problems']

# Train a random forest classifier
clf = RandomForestClassifier(n_estimators=100, random_state=42)
clf.fit(X, y)

# Initialize the Flask application
app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'

# Define a route for the predict endpoint
@app.route('/predict', methods=['POST'])
@cross_origin()
def predict():
    # Get the input data from the request body
    data = request.get_json()

    # Get the input values
    breed = data['breed']
    age = data['age']
    weight = data['weight']

    # Encode the breed using LabelEncoder

    breed_encoded = le.transform([breed])[0]

    # Predict the health problem
    if int(age) < 3:
        pred = 'Ear Infections'
    else:
        pred = clf.predict([[breed_encoded, age, weight]])[0]

    # Return the predicted value as a JSON response
    return jsonify({'prediction': pred})


# Run the Flask application
if __name__ == '__main__':
    app.run(debug=True, port=8011)
