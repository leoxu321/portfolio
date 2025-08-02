
import nltk

from nltk.sentiment.vader import SentimentIntensityAnalyzer

from nltk.corpus import stopwords

from nltk.tokenize import word_tokenize

from nltk.stem import WordNetLemmatizer

sia = SentimentIntensityAnalyzer()

import networkx as nx

import matplotlib.pyplot as plt

InterDict5 = {
    "ANDY":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "JOE":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "BARNEY":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "PAUL":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "PHIL":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "KYLE":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "SAM":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "FARMAR":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "ROSS":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "DAN":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "CHARLIE":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "PIERS":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "VIC":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0},
    "WILL":{"JOE":0,"WILL":0,"BARNEY":0,"PAUL":0,"PHIL":0,"KYLE":0,"SAM":0,"FARMAR":0,"ROSS":0,"DAN":0,"CHARLIE":0,"PIERS":0,"VIC":0,"ANDY":0}

}

InterDict6 = {
    
"JAMIE":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"FI":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"FRAN":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"JULIE":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"BELINDA":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"JAYDE":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"LAUREN":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"BETH":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"GEORGINA":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"CHAVALA":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"KATE":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"BEKI":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"GEORGIE":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
"ABBY":{"FI":0,"FRAN":0,"JULIE":0,"BELINDA":0,"JAYDE":0,"LAUREN":0,"BETH":0,"GEORGINA":0,"CHAVALA":0,"KATE":0,"BEKI":0,"GEORGIE":0,"ABBY":0,"JAMIE":0},
}




InteractionDictEP1 = {
    'ANDY': {'JOE': -2, 'WILL': -1, 'BARNEY': -1, 'PAUL': -8, 'PHIL': -1, 'KYLE': 1, 'SAM': 0, 'FARMAR': 0, 'ROSS': -1, 'DAN': 0, 'CHARLIE': -1, 'PIERS': 0, 'VIC': -1, 'ANDY': 0},
    'JOE': {'JOE': 0, 'WILL': 2, 'BARNEY': 2, 'PAUL': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': -2, 'ANDY': 2},
    'BARNEY': {'JOE': 2, 'WILL': 2, 'BARNEY': 0, 'PAUL': 3, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': 2},
    'PAUL': {'JOE': 0, 'WILL': 1, 'BARNEY': 1, 'PAUL': 0, 'PHIL': 1, 'KYLE': 3, 'SAM': 1, 'FARMAR': 1, 'ROSS': 1, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': -8},
    'PHIL': {'JOE': -1, 'WILL': 2, 'BARNEY': 2, 'PAUL': 1, 'PHIL': 0, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': -1},
    'KYLE': {'JOE': 1, 'WILL': 2, 'BARNEY': 2, 'PAUL': 2, 'PHIL': 2, 'KYLE': 0, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 3, 'VIC': 2, 'ANDY': 2},
    'SAM': {'JOE': 1, 'WILL': 2, 'BARNEY': 2, 'PAUL': 2, 'PHIL': 2, 'KYLE': 4, 'SAM': 0, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': 0},
    'FARMAR': {'JOE': 0, 'WILL': 2, 'BARNEY': 2, 'PAUL': 1, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': 1},
    'ROSS': {'JOE': 2, 'WILL': 2, 'BARNEY': 2, 'PAUL': 3, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 0, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': 2},
    'DAN': {'JOE': 1, 'WILL': 2, 'BARNEY': 2, 'PAUL': 3, 'PHIL': 2, 'KYLE': 4, 'SAM': 3, 'FARMAR': 2, 'ROSS': 2, 'DAN': 0, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, 'ANDY': 3},
    'CHARLIE': {'JOE': 1, 'WILL': 3, 'BARNEY': 2, 'PAUL': 2, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 0, 'PIERS': 2, 'VIC': 2, 'ANDY': 1},
    'PIERS': {'JOE': 0, 'WILL': 2, 'BARNEY': 2, 'PAUL': 0, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 0, 'VIC': 2, 'ANDY': 3},
    'VIC': {'JOE': 0, 'WILL': 2, 'BARNEY': 2, 'PAUL': 0, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 3, 'VIC': 0, 'ANDY': 2},
    'WILL': {'JOE': 0, 'WILL': 0, 'BARNEY': 2, 'PAUL': 1, 'PHIL': 2, 'KYLE': 4, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 3, 'PIERS': 2, 'VIC': 2, 'ANDY': 0}
}

InteractionDictEP2 = {
    'JAMIE': {'FI': 3, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 3, 'JAYDE': -1, 'LAUREN': 4, 'BETH': 4, 'GEORGINA': 5, 'CHAVALA': 1, 'KATE': 3, 'BEKI': 1, 'GEORGIE': 3, 'ABBY': 3, 'JAMIE': 0, },
    'FI': {'FI': 0, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 4, 'JAYDE': 0, 'LAUREN': 4, 'BETH': 4, 'GEORGINA': 6, 'CHAVALA': 1, 'KATE': 4, 'BEKI': 2, 'GEORGIE': 4, 'ABBY': 0, 'JAMIE': 4, },
    'FRAN': {'FI': 0, 'FRAN': 0, 'JULIE': 0, 'BELINDA': -1, 'JAYDE': 0, 'LAUREN': -1, 'BETH': -1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 0, 'BEKI': 3, 'GEORGIE': -1, 'ABBY': 5, 'JAMIE': -1, },
    'JULIE': {'FI': 0, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 0, 'JAYDE': 0, 'LAUREN': -1, 'BETH': -1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -1, 'BEKI': 2, 'GEORGIE': -1, 'ABBY': -2, 'JAMIE': -1, },
    'BELINDA': {'FI': 4, 'FRAN': 0, 'JULIE': 1, 'BELINDA': 0, 'JAYDE': 0, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 5, 'CHAVALA': 1.5, 'KATE': 3, 'BEKI': 2.5, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 3, },
    'JAYDE': {'FI': 2, 'FRAN': 0, 'JULIE': 1, 'BELINDA': 0, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 3, 'CHAVALA': 2, 'KATE': 0, 'BEKI': 3, 'GEORGIE': 0, 'ABBY': 1, 'JAMIE': 0, },
    'LAUREN': {'FI': 4, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 3, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 3, 'GEORGINA': 5, 'CHAVALA': 1.5, 'KATE': 3, 'BEKI': 2, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 3, },
    'BETH': {'FI': 4, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 3, 'JAYDE': 0, 'LAUREN': 4, 'BETH': 0, 'GEORGINA': 4, 'CHAVALA': 1, 'KATE': 3, 'BEKI': 1, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 3, },
    'GEORGINA': {'FI': 4, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 3, 'JAYDE': 0, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 0, 'CHAVALA': 1, 'KATE': 3, 'BEKI': 2, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 3, },
    'CHAVALA': {'FI': 0, 'FRAN': 0, 'JULIE': 0, 'BELINDA': -0.5, 'JAYDE': 0, 'LAUREN': -0.5, 'BETH': -1, 'GEORGINA': 2, 'CHAVALA': 0, 'KATE': -1, 'BEKI': 3, 'GEORGIE': -0.5, 'ABBY': -1, 'JAMIE': -1, },
    'KATE': {'FI': 1, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 3, 'JAYDE': -1, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 5, 'CHAVALA': 1, 'KATE': 0, 'BEKI': 2, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 3, },
    'BEKI': {'FI': 0, 'FRAN': 0, 'JULIE': 0, 'BELINDA': -0.5, 'JAYDE': 0, 'LAUREN': -1, 'BETH': -1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -1, 'BEKI': 0, 'GEORGIE': -1, 'ABBY': 1, 'JAMIE': -1, },
    'GEORGIE': {'FI': 4, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 3, 'JAYDE': 0, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 5, 'CHAVALA': 1.5, 'KATE': 3, 'BEKI': 2, 'GEORGIE': 0, 'ABBY': 0.5, 'JAMIE': 3, },
    'ABBY': {'FI': -1, 'FRAN': 3, 'JULIE': -2, 'BELINDA': -2, 'JAYDE': 0, 'LAUREN': -2, 'BETH': -2, 'GEORGINA': 0, 'CHAVALA': 0, 'KATE': -2, 'BEKI': 2, 'GEORGIE': -1.5, 'ABBY': 0, 'JAMIE': -2, }
}

InteractionDictEP3 = {
    'ANDY': {'JOE': -1, 'WILL': 18, 'BARNEY': 18, 'PAUL': -1, 'PHIL': 10, 'KYLE': 10, 'SAM': 11, 'FARMAR': 10, 'ROSS': 12, 'DAN': 12, 'CHARLIE': 10, 'PIERS': 12, 'VIC': 14, 'ANDY': 0},
    'JOE': {'JOE': 0, 'WILL': 8, 'BARNEY': 14, 'PAUL': 6, 'PHIL': 8, 'KYLE': 8, 'SAM': 9, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 4},
    'BARNEY': {'JOE': -3, 'WILL': 8, 'BARNEY': 0, 'PAUL': -3, 'PHIL': 8, 'KYLE': 8, 'SAM': 9, 'FARMAR': 10, 'ROSS': 12, 'DAN': 10, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 6},
    'PAUL': {'JOE': 2, 'WILL': 8, 'BARNEY': 14, 'PAUL': 0, 'PHIL': 8, 'KYLE': 8, 'SAM': 9, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 0},
    'PHIL': {'JOE': -3, 'WILL': 8, 'BARNEY': 14, 'PAUL': -3, 'PHIL': 0, 'KYLE': 8, 'SAM': 9, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 0},
    'KYLE': {'JOE': -3, 'WILL': 8, 'BARNEY': 14, 'PAUL': -3, 'PHIL': 8, 'KYLE': 0, 'SAM': 9, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 4},
    'SAM': {'JOE': -5, 'WILL': 8, 'BARNEY': 14, 'PAUL': -5, 'PHIL': 8, 'KYLE': 8, 'SAM': 0, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 2},
    'FARMAR': {'JOE': -3, 'WILL': 8, 'BARNEY': 16, 'PAUL': -3, 'PHIL': 8, 'KYLE': 8, 'SAM': 9, 'FARMAR': 0, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 4},
    'ROSS': {'JOE': -3, 'WILL': 8, 'BARNEY': 26, 'PAUL': -3, 'PHIL': 8, 'KYLE': 8, 'SAM': 11, 'FARMAR': 8, 'ROSS': 0, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 10, 'ANDY': 4},
    'DAN': {'JOE': -3, 'WILL': 8, 'BARNEY': 20, 'PAUL': -3, 'PHIL': 8, 'KYLE': 8, 'SAM': 11, 'FARMAR': 8, 'ROSS': 8, 'DAN': 0, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 14, 'ANDY': 6},
    'CHARLIE': {'JOE': -3, 'WILL': 8, 'BARNEY': 16, 'PAUL': -3, 'PHIL': 8, 'KYLE': 8, 'SAM': 11, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 0, 'PIERS': 8, 'VIC': 12, 'ANDY': 0},
    'PIERS': {'JOE': -3, 'WILL': 12, 'BARNEY': 14, 'PAUL': -3, 'PHIL': 8, 'KYLE': 8, 'SAM': 9, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 0, 'VIC': 10, 'ANDY': 6},
    'VIC': {'JOE': -7, 'WILL': 8, 'BARNEY': 18, 'PAUL': -7, 'PHIL': 8, 'KYLE': 8, 'SAM': 11, 'FARMAR': 8, 'ROSS': 8, 'DAN': 12, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 0, 'ANDY': 6},
    'WILL': {'JOE': -5, 'WILL': 0, 'BARNEY': 14, 'PAUL': -5, 'PHIL': 8, 'KYLE': 8, 'SAM': 9, 'FARMAR': 8, 'ROSS': 8, 'DAN': 8, 'CHARLIE': 8, 'PIERS': 12, 'VIC': 10, 'ANDY': 4},
}

InteractionDictEP4 = {
    'JAMIE': {'FI': -1, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -2, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'FI': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 0, 'LAUREN': -1, 'BETH': -1, 'GEORGINA': 0, 'CHAVALA': 1, 'KATE': -6, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': -2, },
    'FRAN': {'FI': 1, 'FRAN': 0, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 4, 'KATE': 1, 'BEKI': 4, 'GEORGIE': 1, 'ABBY': 6, 'JAMIE': 1, },
    'JULIE': {'FI': 1, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 1, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 1, 'BEKI': 1, 'GEORGIE': 1, 'ABBY': 0, 'JAMIE': 1, },
    'BELINDA': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -3, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'LAUREN': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -3, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'BETH': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -2, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'GEORGINA': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 1, 'KATE': -3, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': -1, },
    'CHAVALA': {'FI': 1, 'FRAN': 2, 'JULIE': 0, 'BELINDA': 1, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 0, 'KATE': 1, 'BEKI': 1, 'GEORGIE': 1, 'ABBY': 3, 'JAMIE': 1, },
    'KATE': {'FI': -3, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 2, 'CHAVALA': 1, 'KATE': 0, 'BEKI': 0, 'GEORGIE': 1, 'ABBY': 2, 'JAMIE': 0, },
    'BEKI': {'FI': 1, 'FRAN': 3, 'JULIE': 1, 'BELINDA': 1, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 0, 'BEKI': 0, 'GEORGIE': 1, 'ABBY': 3, 'JAMIE': 1, },
    'GEORGIE': {'FI': 1, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 2, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 2, 'CHAVALA': 1, 'KATE': -1, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 1, },
    'ABBY': {'FI': 1, 'FRAN': 3, 'JULIE': -3, 'BELINDA': 1, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 1, 'BEKI': 2, 'GEORGIE': 1, 'ABBY': 0, 'JAMIE': 1, },
}


InteractionDictEP5 = {
    "BARNEY":{"JOE":0,"WILL":3.5,"BARNEY":0,"PAUL":0,"PHIL":5,"KYLE":1.5,"SAM":5,"FARMAR":4,"ROSS":3,"DAN":3.5,"CHARLIE":3,"PIERS":3.5,"VIC":7,"ANDY":0},
    "PHIL":{"JOE":0,"WILL":2.5,"BARNEY":5,"PAUL":0,"PHIL":0,"KYLE":0.5,"SAM":4,"FARMAR":4,"ROSS":3,"DAN":3.5,"CHARLIE":2,"PIERS":4.5,"VIC":7,"ANDY":0},
    "KYLE":{"JOE":0,"WILL":2.5,"BARNEY":2.5,"PAUL":0,"PHIL":5.5,"KYLE":0,"SAM":5.5,"FARMAR":3.5,"ROSS":4.5,"DAN":2.5,"CHARLIE":4.5,"PIERS":2.5,"VIC":2,"ANDY":0},
    "SAM":{"JOE":0,"WILL":4,"BARNEY":5,"PAUL":0,"PHIL":5,"KYLE":4.5,"SAM":0,"FARMAR":6,"ROSS":4,"DAN":9,"CHARLIE":4,"PIERS":6,"VIC":11,"ANDY":0},
    "FARMAR":{"JOE":0,"WILL":4.5,"BARNEY":6,"PAUL":0,"PHIL":6,"KYLE":5,"SAM":8,"FARMAR":0,"ROSS":4,"DAN":7.5,"CHARLIE":3,"PIERS":4.5,"VIC":11,"ANDY":0},
    "ROSS":{"JOE":0,"WILL":2.5,"BARNEY":3,"PAUL":0,"PHIL":4,"KYLE":3.5,"SAM":4,"FARMAR":3,"ROSS":0,"DAN":4.5,"CHARLIE":3,"PIERS":3.5,"VIC":5,"ANDY":0},
    "DAN":{"JOE":0,"WILL":5,"BARNEY":6,"PAUL":0,"PHIL":6,"KYLE":4.5,"SAM":9,"FARMAR":6,"ROSS":5,"DAN":0,"CHARLIE":5,"PIERS":7,"VIC":14,"ANDY":0},
    "CHARLIE":{"JOE":0,"WILL":0,"BARNEY":1,"PAUL":0,"PHIL":2,"KYLE":3.5,"SAM":3,"FARMAR":1,"ROSS":3,"DAN":2,"CHARLIE":0,"PIERS":1,"VIC":3,"ANDY":0},
    "PIERS":{"JOE":0,"WILL":2.5,"BARNEY":4,"PAUL":0,"PHIL":6,"KYLE":5,"SAM":6,"FARMAR":4,"ROSS":3,"DAN":5.5,"CHARLIE":2,"PIERS":0,"VIC":8,"ANDY":0},
    "VIC":{"JOE":0,"WILL":5.5,"BARNEY":6,"PAUL":0,"PHIL":6,"KYLE":2,"SAM":9,"FARMAR":6,"ROSS":5,"DAN":12.5,"CHARLIE":4,"PIERS":7.5,"VIC":0,"ANDY":0},
    "WILL":{"JOE":0,"WILL":0,"BARNEY":1,"PAUL":0,"PHIL":1,"KYLE":1,"SAM":1,"FARMAR":2,"ROSS":2,"DAN":2,"CHARLIE":1,"PIERS":3,"VIC":2,"ANDY":0}
}


InteractionDictEP6 = {
    "JAMIE":{"FI":5.5,"FRAN":2.5,"JULIE":1,"BELINDA":4.5,"LAUREN":9.5,"BETH":2.5,"GEORGINA":0.5,"CHAVALA":0.5,"BEKI":1.5,"GEORGIE":4.5,"ABBY":1.5,"JAMIE":0},
    "FI":{"FI":0,"FRAN":-1,"JULIE":2,"BELINDA":3.5,"LAUREN":10,"BETH":3,"GEORGINA":3,"CHAVALA":2,"BEKI":2,"GEORGIE":3,"ABBY":2,"JAMIE":1},
    "FRAN":{"FI":8,"FRAN":0,"JULIE":0.5,"BELINDA":5,"LAUREN":8.5,"BETH":2.5,"GEORGINA":0.5,"CHAVALA":0.5,"BEKI":0.5,"GEORGIE":3,"ABBY":4.5,"JAMIE":2.5},
    "JULIE":{"FI":7,"FRAN":1,"JULIE":0,"BELINDA":3.5,"LAUREN":11.5,"BETH":2,"GEORGINA":2,"CHAVALA":1,"BEKI":2,"GEORGIE":2.5,"ABBY":1,"JAMIE":0},
    "BELINDA":{"FI":4.5,"FRAN":2,"JULIE":3,"BELINDA":0,"LAUREN":12,"BETH":5,"GEORGINA":3,"CHAVALA":2,"BEKI":2,"GEORGIE":5.5,"ABBY":2,"JAMIE":3},
    "LAUREN":{"FI":7,"FRAN":0.5,"JULIE":3.5,"BELINDA":6,"LAUREN":0,"BETH":3.5,"GEORGINA":4.5,"CHAVALA":4,"BEKI":3.5,"GEORGIE":4.5,"ABBY":3.5,"JAMIE":2.5},
    "BETH":{"FI":3,"FRAN":-0.5,"JULIE":2.5,"BELINDA":6,"LAUREN":11,"BETH":0,"GEORGINA":2.5,"CHAVALA":2.5,"BEKI":2.5,"GEORGIE":4.5,"ABBY":2.5,"JAMIE":2.5},
    "GEORGINA":{"FI":6.5,"FRAN":-0.5,"JULIE":2.5,"BELINDA":5.5,"LAUREN":14,"BETH":3.5,"GEORGINA":0,"CHAVALA":3.5,"BEKI":2.5,"GEORGIE":5,"ABBY":2.5,"JAMIE":0.5},
    "CHAVALA":{"FI":7.5,"FRAN":-0.5,"JULIE":2.5,"BELINDA":5,"LAUREN":13.5,"BETH":3.5,"GEORGINA":2.5,"CHAVALA":0,"BEKI":3.5,"GEORGIE":7.5,"ABBY":3.5,"JAMIE":1.5},
    "BEKI":{"FI":7,"FRAN":-1.5,"JULIE":2.5,"BELINDA":4.5,"LAUREN":12.5,"BETH":4.5,"GEORGINA":3.5,"CHAVALA":2.5,"BEKI":0,"GEORGIE":5,"ABBY":2.5,"JAMIE":0.5},
    "GEORGIE":{"FI":7.5,"FRAN":4.5,"JULIE":-1,"BELINDA":5.5,"LAUREN":11.5,"BETH":2.5,"GEORGINA":3.5,"CHAVALA":2.5,"BEKI":2.5,"GEORGIE":0,"ABBY":3.5,"JAMIE":1.5},
    "ABBY":{"FI":8,"FRAN":3.5,"JULIE":-2.5,"BELINDA":5.5,"LAUREN":13,"BETH":3.5,"GEORGINA":3.5,"CHAVALA":3.5,"BEKI":3.5,"GEORGIE":7.5,"ABBY":0,"JAMIE":1.5},
}


InteractionDictEP7 = {
    'BARNEY': {'WILL': 3, 'BARNEY': 0, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 3, 'ROSS': 4, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 2},
    'PHIL': {'WILL': 4, 'BARNEY': 3, 'PHIL': 0, 'KYLE': 4, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 4, 'PIERS': 3, 'VIC': 2},
    'KYLE': {'WILL': 4, 'BARNEY': 3, 'PHIL': 4, 'KYLE': 0, 'SAM': 5, 'FARMAR': 3, 'ROSS': 3, 'DAN': 5, 'CHARLIE': 4, 'PIERS': 3, 'VIC': 1},
    'SAM': {'WILL': 2, 'BARNEY': 3, 'PHIL': 2, 'KYLE': 2, 'SAM': 0, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': -3},
    'FARMAR': {'WILL': 3, 'BARNEY': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 0, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 1},
    'ROSS': {'WILL': 3, 'BARNEY': 4, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 3, 'ROSS': 0, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 3},
    'DAN': {'WILL': 3, 'BARNEY': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 2, 'FARMAR': 3, 'ROSS': 3, 'DAN': 0, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 1},
    'CHARLIE': {'WILL': 4, 'BARNEY': 3, 'PHIL': 4, 'KYLE': 4, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 0, 'PIERS': 3, 'VIC': 1},
    'PIERS': {'WILL': 3, 'BARNEY': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 0, 'VIC': 2},
    'VIC': {'WILL': -2, 'BARNEY': 0, 'PHIL': -2, 'KYLE': -2, 'SAM': -2, 'FARMAR': -1, 'ROSS': -2, 'DAN': 2, 'CHARLIE': -3, 'PIERS': -2, 'VIC': 0},
    'WILL': {'WILL': 0, 'BARNEY': 3, 'PHIL': 4, 'KYLE': 4, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 4, 'PIERS': 3, 'VIC': 2},
}


InteractionDictEP8 = {
    'JAMIE': {'FI': 0, 'JULIE': 0, 'BELINDA': 2,  'LAUREN': 2, 'BETH': 3, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 0, },
    'FI': {'FI': 0, 'JULIE': 0, 'BELINDA': 2,  'LAUREN': 3.5, 'BETH': 3.5, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 1, },
    'JULIE': {'FI': 0, 'JULIE': 0, 'BELINDA': 2,  'LAUREN': 4, 'BETH': 3, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 1, },
    'BELINDA': {'FI': 0, 'JULIE': 0, 'BELINDA': 0,  'LAUREN': 5, 'BETH': 4, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 4, 'ABBY': 1, 'JAMIE': 2, },
    'LAUREN': {'FI': 0.5, 'JULIE': 1, 'BELINDA': 3.5,  'LAUREN': 0, 'BETH': 6.0, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 4.5, 'ABBY': 1, 'JAMIE': 2, },
    'BETH': {'FI': 0.5, 'JULIE': 0, 'BELINDA': 3,  'LAUREN': 6.5, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': -0.5, 'GEORGIE': 4, 'ABBY': 2, 'JAMIE': 3, },
    'GEORGINA': {'FI': 0, 'JULIE': 1, 'BELINDA': 10,  'LAUREN': 9, 'BETH': 9, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 10, 'ABBY': 2, 'JAMIE': 3, },
    'CHAVALA': {'FI': 0, 'JULIE': 0, 'BELINDA': 2,  'LAUREN': 3, 'BETH': 4, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 4, 'ABBY': 1, 'JAMIE': 2, },
    'BEKI': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 2,  'LAUREN': 2.5, 'BETH': 2.5, 'GEORGINA': 1, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 4, 'ABBY': 1, 'JAMIE': 1, },
    'GEORGIE': {'FI': 0, 'JULIE': 0, 'BELINDA': 3,  'LAUREN': 5, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 2, 'BEKI': 2, 'GEORGIE': 0, 'ABBY': 1, 'JAMIE': 2, },
    'ABBY': {'FI': 1, 'JULIE': 1, 'BELINDA': 3,  'LAUREN': 4, 'BETH': 5, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 1, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 2, },
}

InteractionDictEP9 = {
    'JAMIE': {'FI': -2, 'JULIE': -1, 'BELINDA': 4, 'LAUREN': 1, 'BETH': 0, 'GEORGINA': -3, 'CHAVALA': -2.5, 'BEKI': -2, 'GEORGIE': -3,'JAMIE': 0, },
    'FI': {'FI': 0, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1,  'JAMIE': 2, },
    'JULIE': {'FI': 2, 'JULIE': 0, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 4, 'GEORGINA': 4, 'CHAVALA': 2, 'BEKI': 2, 'GEORGIE': 2,  'JAMIE': 2, },
    'BELINDA': {'FI': 2, 'JULIE': 1, 'BELINDA': 0, 'LAUREN': 5, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1,  'JAMIE': 6, },
    'LAUREN': {'FI': 1, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 0, 'BETH': 6, 'GEORGINA': 0, 'CHAVALA': 2, 'BEKI': 3, 'GEORGIE': 0, 'JAMIE': -1, },
    'BETH': {'FI': 1, 'JULIE': 3, 'BELINDA': 4, 'LAUREN': 7, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': 3, 'GEORGIE': 1,'JAMIE': 1, },
    'GEORGINA': {'FI': 2, 'JULIE': 6, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1,  'JAMIE': 2, },
    'CHAVALA': {'FI': 2, 'JULIE': 2, 'BELINDA': 4, 'LAUREN': 6, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 0, 'BEKI': 3, 'GEORGIE': 1,  'JAMIE': 1.5, },
    'BEKI': {'FI': 2, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 6, 'BETH': 5, 'GEORGINA': 1, 'CHAVALA': 2, 'BEKI': 0, 'GEORGIE': 1,  'JAMIE': 1, },
    'GEORGIE': {'FI': 2, 'JULIE': 2, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 0,  'JAMIE': 2, },
}

InteractionDictEP10 = {
    'BARNEY': {'WILL': 4, 'BARNEY': 0, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3, },
    'PHIL': {'WILL': 4, 'BARNEY': 2, 'PHIL': 0, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 3, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 4, },
    'KYLE': {'WILL': 4, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 0, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3, },
    'SAM': {'WILL': 5, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 0, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3, },
    'FARMAR': {'WILL': 7, 'BARNEY': 4, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2, },
    'ROSS': {'WILL': 5, 'BARNEY': 1, 'PHIL': 2, 'KYLE': 1, 'SAM': 1, 'FARMAR': 0, 'ROSS': 0, 'DAN': 1, 'CHARLIE': 1, 'PIERS': 2, 'VIC': 3, },
    'DAN': {'WILL': 4, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': -1, 'ROSS': 2, 'DAN': 0, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3, },
    'CHARLIE': {'WILL': 5, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 0, 'PIERS': 2, 'VIC': 3, },
    'PIERS': {'WILL': 7, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 3, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 0, 'VIC': 4, },
    'VIC': {'WILL': 3, 'BARNEY': 2, 'PHIL': 3, 'KYLE': 2, 'SAM': 2, 'FARMAR': -4, 'ROSS': 3, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 3, 'VIC': 0, },
    'WILL': {'WILL': 0, 'BARNEY': 0, 'PHIL': 0, 'KYLE': 0, 'SAM': 1, 'FARMAR': 3, 'ROSS': 2, 'DAN': 0, 'CHARLIE': 0, 'PIERS': 3, 'VIC': 0, },
}

InteractionDictEP11 = {
    'JAMIE': {'FI': 13, 'JULIE': 11.0, 'BELINDA': 12.5, 'LAUREN': 14.0, 'BETH': 13.0, 'GEORGINA': 11.5, 'CHAVALA': 12.5, 'BEKI': 11, 'GEORGIE': 13,  'JAMIE': 0},
    'FI': {'FI': 0, 'JULIE': 12.0, 'BELINDA': 10.0, 'LAUREN': 15, 'BETH': 13.5, 'GEORGINA': 11.5, 'CHAVALA': 10.5, 'BEKI': 11, 'GEORGIE': 14.5,  'JAMIE': 10.0},
    'JULIE': {'FI': 14.0, 'JULIE': 0, 'BELINDA': 10.5, 'LAUREN': 14.5, 'BETH': 13.5, 'GEORGINA': 13.5, 'CHAVALA': 12.0, 'BEKI': 12, 'GEORGIE': 15.0,  'JAMIE': 8.5},
    'BELINDA': {'FI': 13, 'JULIE': 11.0, 'BELINDA': 0, 'LAUREN': 14.0, 'BETH': 14.0, 'GEORGINA': 11.5, 'CHAVALA': 11.5, 'BEKI': 11, 'GEORGIE': 12.5,  'JAMIE': 10.5},
    'LAUREN': {'FI': 15.5, 'JULIE': 13.5, 'BELINDA': 11.0, 'LAUREN': 0, 'BETH': 13.5, 'GEORGINA': 13.0, 'CHAVALA': 13.5, 'BEKI': 15.0, 'GEORGIE': 16.0,  'JAMIE': 9.5},
    'BETH': {'FI': 14.5, 'JULIE': 13.0, 'BELINDA': 11.5, 'LAUREN': 14.0, 'BETH': 0, 'GEORGINA': 13.5, 'CHAVALA': 12.5, 'BEKI': 13.0, 'GEORGIE': 16.0,  'JAMIE': 10.0},
    'GEORGINA': {'FI': 13.5, 'JULIE': 12.5, 'BELINDA': 10.5, 'LAUREN': 14.5, 'BETH': 14.5, 'GEORGINA': 0, 'CHAVALA': 11.5, 'BEKI': 11, 'GEORGIE': 13.5,  'JAMIE': 11.5},
    'CHAVALA': {'FI': 12.5, 'JULIE': 11.5, 'BELINDA': 10.5, 'LAUREN': 15.0, 'BETH': 13.5, 'GEORGINA': 11.5, 'CHAVALA': 0, 'BEKI': 13.5, 'GEORGIE': 13.5,  'JAMIE': 12.0},
    'BEKI': {'FI': 13, 'JULIE': 12.0, 'BELINDA': 10.0, 'LAUREN': 15.5, 'BETH': 14, 'GEORGINA': 11, 'CHAVALA': 12.5, 'BEKI': 0, 'GEORGIE': 14.5,  'JAMIE': 11.0},
    'GEORGIE': {'FI': 14.5, 'JULIE': 13.5, 'BELINDA': 9.5, 'LAUREN': 15.5, 'BETH': 15, 'GEORGINA': 11.5, 'CHAVALA': 11.5, 'BEKI': 13.5, 'GEORGIE': 0,  'JAMIE': 10.0},
}

InteractionDictEP12 = {
    'BARNEY': {'WILL': 8, 'BARNEY': 0, 'PHIL': 8, 'KYLE': 9, 'SAM': 11, 'FARMAR': 9, 'ROSS': 11, 'DAN': 9, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 11, },
    'PHIL': {'WILL': 8, 'BARNEY': 9, 'PHIL': 0, 'KYLE': 10, 'SAM': 10, 'FARMAR': 9, 'ROSS': 11, 'DAN': 9, 'CHARLIE': 8, 'PIERS': 10, 'VIC': 10, },
    'KYLE': {'WILL': 8, 'BARNEY': 8, 'PHIL': 8, 'KYLE': 0, 'SAM': 10, 'FARMAR': 9, 'ROSS': 12, 'DAN': 9, 'CHARLIE': 8, 'PIERS': 9, 'VIC': 9, },
    'SAM': {'WILL': 8, 'BARNEY': 11, 'PHIL': 8, 'KYLE': 10, 'SAM': 0, 'FARMAR': 9, 'ROSS': 12, 'DAN': 9, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 11, },
    'FARMAR': {'WILL': 8, 'BARNEY': 11, 'PHIL': 9, 'KYLE': 11, 'SAM': 11, 'FARMAR': 0, 'ROSS': 13, 'DAN': 10, 'CHARLIE': 8, 'PIERS': 10, 'VIC': 11, },
    'ROSS': {'WILL': 8, 'BARNEY': 9, 'PHIL': 8, 'KYLE': 12, 'SAM': 11, 'FARMAR': 9, 'ROSS': 0, 'DAN': 10, 'CHARLIE': 9, 'PIERS': 10, 'VIC': 10, },
    'DAN': {'WILL': 7, 'BARNEY': 8, 'PHIL': 7, 'KYLE': 9, 'SAM': 9, 'FARMAR': 8, 'ROSS': 10, 'DAN': 0, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 9, },
    'CHARLIE': {'WILL': 7, 'BARNEY': 7, 'PHIL': 6, 'KYLE': 8, 'SAM': 7, 'FARMAR': 6, 'ROSS': 10, 'DAN': 9, 'CHARLIE': 0, 'PIERS': 7, 'VIC': 7, },
    'PIERS': {'WILL': 8, 'BARNEY': 9, 'PHIL': 8, 'KYLE': 9, 'SAM': 9, 'FARMAR': 8, 'ROSS': 11, 'DAN': 8, 'CHARLIE': 7, 'PIERS': 0, 'VIC': 9, },
    'VIC': {'WILL': 8, 'BARNEY': 10, 'PHIL': 8, 'KYLE': 9, 'SAM': 11, 'FARMAR': 9, 'ROSS': 10, 'DAN': 9, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 0, },
    'WILL': {'WILL': 0, 'BARNEY': 8, 'PHIL': 8, 'KYLE': 9, 'SAM': 9, 'FARMAR': 8, 'ROSS': 10, 'DAN': 8, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 9, },
}


# def count(InvovledArray,value):
#     for key in InvovledArray:
#         for key2 in InvovledArray:
#             if key != key2:
#                 InterDict[key][key2] += value

# with open(r"C:\Users\Andrew Hall\Desktop\Social networks Project\Ep5-Tokenized.txt", "r") as f:
#     lines = []
#     actors = []
#     for line in f:
#         line.replace
#         line = line.replace(":"," ").strip()
#         if not line:  
#             if lines:
#                 conversation = " "
#                 score = 0
#                 for sentence in lines:
#                     conversation += sentence
#                 print(conversation)
#                 sentiment_score = sia.polarity_scores(conversation)
#                 if sentiment_score['compound'] < 0:
#                     score = -1
#                 else:
#                     score = 1

#                 for key in InterDict:
#                     for string in lines:
#                         if key in string:
#                             if key not in actors:
#                                 actors.append(key)
#                 if actors:
#                     count(actors,score)

#                 actors = []
#                 lines = []

#         else:
#             lines.append(line)



InterDict = InteractionDictEP1
def generateInteractionGraph(InterDict):
    G = nx.Graph()

    # Add nodes and edges with weights
    for node, neighbors in InterDict.items():
        for neighbor, weight in neighbors.items():
            if node != neighbor:
                if weight !=0:
                    G.add_edge(node, neighbor, weight=weight)


   # Get node positions
    pos = nx.spring_layout(G, k=0.99, iterations=20,weight='weight')

    # Create a figure and axis
    fig, ax = plt.subplots(dpi=250)

    # Draw positive edges in blue
    nx.draw_networkx_edges(G, pos, edgelist=[(u, v) for u, v, d in G.edges(data=True) if d['weight'] >= 0.5],
                           edge_color='blue', width=2.0, ax=ax)

    # Draw negative edges in red
    nx.draw_networkx_edges(G, pos, edgelist=[(u, v) for u, v, d in G.edges(data=True) if d['weight'] < 0.5],
                           edge_color='red', width=2.0, style='dashed', ax=ax)
    
    # # Draw negative edges in red
    # nx.draw_networkx_edges(G, pos, edgelist=[(u, v) for u, v, d in G.edges(data=True) if d['weight'] == 0],
    #                        edge_color='grey', style='dashed', width=2.0, ax=ax)

    # Draw nodes
    nx.draw_networkx_nodes(G, pos, node_color='skyblue', node_size=1400, ax=ax)

    # Draw node labels
    nx.draw_networkx_labels(G, pos, font_size=10, font_family='sans-serif', ax=ax)

    # Set plot title
    plt.title("Men\'s Relationship Graph #6 (EP12)")

    # Remove axis
    ax.set_xticks([])
    ax.set_yticks([])

    # Show plot
    plt.show()
    

print(InterDict)

generateInteractionGraph(InterDict)

