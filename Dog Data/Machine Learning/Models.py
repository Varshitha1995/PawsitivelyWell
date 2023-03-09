from flask import Flask, request, jsonify
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder
import pandas as pd

# Load the data
data = pd.read_csv('Final Data.csv')

# Encode the Breed column using LabelEncoder
le = LabelEncoder()
data['Breed_Encoded'] = le.fit_transform(data['Breed'])

# Split the data into features (X) and target (y)
X = data[['Breed_Encoded','Age','Height','Weight']]
y = data['Health_Problems']

# Train a random forest classifier
clf = RandomForestClassifier(n_estimators=100, random_state=42)
clf.fit(X, y)
