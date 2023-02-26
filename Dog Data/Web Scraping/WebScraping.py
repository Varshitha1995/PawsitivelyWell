# Importing necessary libraries
import re
import requests
import os
from datetime import datetime
from bs4 import BeautifulSoup
from bs4.element import Tag
import pandas as pd
from tqdm import tqdm_notebook as tqdm


#Breed Description

def get_description(breed_soup):
    try:
        first_part = breed_soup.find(
                'div', class_='breed-info__content-wrap'
        ).get_text().strip()
    except:
        first_part = ''
    
    try:
        second_part = breed_soup.find(
                'div', class_='breed-hero__footer'
        ).get_text().strip()
    except:
        second_part = ''
    
    description = ' '.join([first_part, second_part])
    
    description = description.replace(
        '\n', '').replace('\u200b', '').replace('\xa0', ' ')
    return description


#Breed Height, Longevity & Weight

def general_regex(text, var, mul=1):
    reg = re.compile('(\d+\.?\d*)')
    results = reg.findall(text)
    numbers = [float(value) * mul for value in results]
    if len(numbers) == 1:
        numbers = numbers * 2
    elif len(numbers) == 0:
        numbers = [0, 0]
    return {
        'min_{}'.format(var): min(numbers),
        'max_{}'.format(var): max(numbers)
    }

def get_height(height_span):
    ht_text = height_span.get_text()
    return general_regex(ht_text, 'height', 2.54)

def get_expectancy(expectancy_span):
    exp_text = expectancy_span.get_text()
    return general_regex(exp_text, 'expectancy') 


def get_weight(weight_span):
    wt_text = weight_span.get_text()
    return general_regex(wt_text, 'weight', 0.45359237) 
