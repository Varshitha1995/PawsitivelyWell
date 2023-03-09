# Initialize the Flask application
app = Flask(__name__)

# Define a route for the predict endpoint
@app.route('/predict', methods=['POST'])
def predict():
    # Get the input data from the request body
    data = request.get_json()

    # Get the input values
    breed = data['breed']
    age = data['age']
    height = data['height']
    weight = data['weight']

    # Encode the breed using LabelEncoder
    breed_encoded = le.transform([breed])[0]

    # Predict the health problem
    if int(age) < 3:
        pred = 'Ear Infections'
    else:
        pred = clf.predict([[breed_encoded, age, height, weight]])[0]

    # Return the predicted value as a JSON response
    return jsonify({'prediction': pred})

# Run the Flask application
if __name__ == '__main__':
    app.run(debug=True, port=8011)