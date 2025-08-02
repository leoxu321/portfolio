
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

def delSelfLoops(dictionary):
    for i, j in dictionary.items():
        for key in j:
            if i == key:
                dictionary[i][key] = 0

def print_dictionary(dictionary):
    for k, v in dictionary.items():
        print("'"+ k + "'" + ": {", end="")
        for sub_k, sub_v in v.items():
            if sub_k != "ANDY":
                print("'" + sub_k + "': " + str(sub_v), end=", ")
            else:
                print("'" + sub_k + "': " + str(sub_v), end="")

        print("},")

#GROUP +1
def add_value_all(dictionary, value):
    for k, v in dictionary.items():
        for sub_k in v:
            v[sub_k] += value

def subtract_value_all(dictionary, value):
    for k, v in dictionary.items():
        for sub_k in v:
            v[sub_k] -= value

# add value for PERSON -> GROUP
def add_valueP(dictionary, key, value):
    for k in dictionary:
        if key in dictionary[k]:
            dictionary[k][key] += value

def sub_valueP(dictionary, key, value):
    for k in dictionary:
        if key in dictionary[k]:
            dictionary[k][key] -= value


def add_both_value(dictionary, key, key2,value):
    dictionary[key][key2] += value
    dictionary[key2][key] += value

# add value for PERSON -> PERSON
def add_value(dictionary, key, key2,value):
    dictionary[key][key2] += value

def sub_value(dictionary, key, key2,value):
    dictionary[key][key2] -= value

# GROUP -> PERSON 
def addPGroup(dictionary, key, value):
     for k, v in dictionary.items():
        if key == k:
            for sub_k in v:
                 v[sub_k] += value

def subPGroup(dictionary, key, value):
     for k, v in dictionary.items():
        if key == k:
            for sub_k in v:
                 v[sub_k] -= value

#SPECIFIC GROUP -> PERSON +1
def grouptoPerson(dictionary,key,array,value):
    for name in array:
         dictionary[name][key] += value

#PERSON -> SPECIFIC GROUP +1
def add_group(dictionary,key,array,value):
    for name in array:
         dictionary[key][name] += value

def add_mutual(dictionary,array,value):
    for i in range(len(array)):
        for j in range(i + 1, len(array)):
            add_both_value(dictionary, array[i], array[j], value)


InterDictEP2 = {
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

# sub_value(InteractionDictEP3,"SAM", "PAUL", 1)
# sub_value(InteractionDictEP3,"SAM", "JOE", 1)
# add_value(InteractionDictEP3,"ANDY","ROSS", 1)
# add_valueP(InteractionDictEP3, "ANDY", 1)
# sub_value(InteractionDictEP3, "WILL", "PAUL", 1)
# sub_value(InteractionDictEP3, "WILL", "JOE", 1)
# sub_value(InteractionDictEP3, "PAUL", "ANDY", 2)
# sub_value(InteractionDictEP3, "VIC", "PAUL", 2)
# sub_value(InteractionDictEP3, "VIC", "JOE", 2)
# add_value(InteractionDictEP3, "JOE", "PAUL", 2)
# add_value(InteractionDictEP3, "PAUL", "PAUL", 2)
# sub_valueP(InteractionDictEP3, "PAUL",2)
# sub_valueP(InteractionDictEP3, "JOE",2)
# add_value_all(InteractionDictEP3, 1)
# add_value(InteractionDictEP3, "BARNEY", "ANDY", 1)
# add_value(InteractionDictEP3, "ANDY", "BARNEY", 1)
# addPGroup(InteractionDictEP3, 'ANDY', 1)
# add_mutual(InteractionDictEP3,["DAN","VIC","ANDY"], 1)
# add_valueP(InteractionDictEP3, "VIC", 1)
# add_value(InteractionDictEP3, "PIERS", "WILL", 1)
# add_value(InteractionDictEP3, "WILL", "PIERS", 1)
# add_mutual(InteractionDictEP3,["WILL","PIERS","ANDY"], 1)
# add_value_all(InteractionDictEP3,1)
# add_value(InteractionDictEP3, "BARNEY", "DAN", 1)
# add_valueP(InteractionDictEP3, "ANDY", 1)
# add_value(InteractionDictEP3, "SAM", "ANDY", 1)
# add_value(InteractionDictEP3, "WILL", "ANDY", 1)
# add_value(InteractionDictEP3, "ANDY", "WILL", 1)
# add_value(InteractionDictEP3, "VIC", "DAN", 1)
# add_value(InteractionDictEP3, "DAN", "VIC", 1)
# add_value(InteractionDictEP3, "WILL", "ANDY", 2)
# add_value(InteractionDictEP3, "ANDY", "WILL", 2)
# add_value(InteractionDictEP3, "CHARLIE", "SAM", 1)
# add_value(InteractionDictEP3, "CHARLIE", "BARNEY", 1)
# add_value(InteractionDictEP3, "BARNEY", "ROSS", 1)
# add_value(InteractionDictEP3, "ROSS", "BARNEY", 4)

# add_value(InteractionDictEP3, "DAN", "SAM", 1)
# add_value(InteractionDictEP3, "VIC", "SAM", 1)
# add_value(InteractionDictEP3, "ROSS", "SAM", 1)

# add_value(InteractionDictEP3, "DAN", "BARNEY", 1)
# add_value(InteractionDictEP3, "VIC", "BARNEY", 1)
# add_value(InteractionDictEP3, "ROSS", "BARNEY", 1)

# sub_value(InteractionDictEP3, "WILL", "ANDY", 2)
# sub_value(InteractionDictEP3, "PHIL", "ANDY", 2)
# sub_value(InteractionDictEP3, "SAM", "ANDY", 2)
# sub_value(InteractionDictEP3, "CHARLIE", "ANDY", 2)

# sub_valueP(InteractionDictEP3, "ANDY", 2)
# sub_value(InteractionDictEP3, "WILL", "ANDY", 2)
# add_value(InteractionDictEP3, "CHARLIE", "VIC", 1)
# add_valueP(InteractionDictEP3, "BARNEY", 1)
# add_value(InteractionDictEP3, "DAN", "BARNEY",1)
# add_value(InteractionDictEP3, "ROSS", "BARNEY", 1)
# add_value(InteractionDictEP3, "BARNEY", "ROSS", 1)

# add_value_all(InteractionDictEP3, 1)
# sub_valueP(InteractionDictEP3,"ANDY", 1)
# add_value(InteractionDictEP3, "FARMAR", "BARNEY", 1)
# add_value(InteractionDictEP3, "BARNEY", "FARMAR", 1)

# add_valueP(InteractionDictEP3, "BARNEY", 2)
# add_value(InteractionDictEP3, "VIC", "BARNEY", 1)
# add_value(InteractionDictEP3, "DAN", "BARNEY", 1)

# add_value_all(InteractionDictEP3, 1)
# sub_valueP(InteractionDictEP3,"ANDY", 1)

# add_both_value(InteractionDictEP3, "PAUL", "JOE", 5)

# sub_valueP(InteractionDictEP3, "PAUL",7)
# sub_valueP(InteractionDictEP3, "JOE",7)

# delSelfLoops(InteractionDictEP3)
# print_dictionary(InteractionDictEP3)

InterDictEP4 = {
    'JAMIE': {'FI': -1, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -2, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'FI': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 0, 'JAYDE': 0, 'LAUREN': -1, 'BETH': -1, 'GEORGINA': 0, 'CHAVALA': 1, 'KATE': -6, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': -2, },
    'FRAN': {'FI': 1, 'FRAN': 0, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 4, 'KATE': 1, 'BEKI': 4, 'GEORGIE': 1, 'ABBY': 6, 'JAMIE': 1, },
    'JULIE': {'FI': 1, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 1, 'BEKI': 1, 'GEORGIE': 1, 'ABBY': 0, 'JAMIE': 1, },
    'BELINDA': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 0, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -3, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'JAYDE': {'FI': 0, 'FRAN': 0, 'JULIE': 0, 'BELINDA': 0, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 0, 'KATE': 0, 'BEKI': 0, 'GEORGIE': 0, 'ABBY': 0, 'JAMIE': 0, },
    'LAUREN': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -3, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'BETH': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': -2, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 0, },
    'GEORGINA': {'FI': 0, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 1, 'KATE': -3, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': -1, },
    'CHAVALA': {'FI': 1, 'FRAN': 2, 'JULIE': 0, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 0, 'KATE': 1, 'BEKI': 1, 'GEORGIE': 1, 'ABBY': 3, 'JAMIE': 1, },
    'KATE': {'FI': -3, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 2, 'CHAVALA': 1, 'KATE': 0, 'BEKI': 0, 'GEORGIE': 1, 'ABBY': 2, 'JAMIE': 0, },
    'BEKI': {'FI': 1, 'FRAN': 3, 'JULIE': 1, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 0, 'BEKI': 0, 'GEORGIE': 1, 'ABBY': 3, 'JAMIE': 1, },
    'GEORGIE': {'FI': 1, 'FRAN': 1, 'JULIE': 1, 'BELINDA': 2, 'JAYDE': 0, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 2, 'CHAVALA': 1, 'KATE': -1, 'BEKI': 1, 'GEORGIE': 0, 'ABBY': 2, 'JAMIE': 1, },
    'ABBY': {'FI': 1, 'FRAN': 3, 'JULIE': -3, 'BELINDA': 1, 'JAYDE': 0, 'LAUREN': 1, 'BETH': 1, 'GEORGINA': 1, 'CHAVALA': 1, 'KATE': 1, 'BEKI': 2, 'GEORGIE': 1, 'ABBY': 0, 'JAMIE': 1, },
}

InteractionDictEP7 = {
    'BARNEY': {'JOE': 3, 'WILL': 3, 'BARNEY': 0, 'PAUL': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 3, 'ROSS': 4, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 2, 'ANDY': 3},
    'PHIL': {'JOE': 3, 'WILL': 4, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 0, 'KYLE': 4, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 4, 'PIERS': 3, 'VIC': 2, 'ANDY': 3},
    'KYLE': {'JOE': 3, 'WILL': 4, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 4, 'KYLE': 0, 'SAM': 5, 'FARMAR': 3, 'ROSS': 3, 'DAN': 5, 'CHARLIE': 4, 'PIERS': 3, 'VIC': 1, 'ANDY': 3},
    'SAM': {'JOE': 2, 'WILL': 2, 'BARNEY': 3, 'PAUL': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 0, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': -3, 'ANDY': 2},
    'FARMAR': {'JOE': 3, 'WILL': 3, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 0, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 1, 'ANDY': 3},
    'ROSS': {'JOE': 3, 'WILL': 3, 'BARNEY': 4, 'PAUL': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 3, 'ROSS': 0, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 3, 'ANDY': 3},
    'DAN': {'JOE': 3, 'WILL': 3, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 2, 'FARMAR': 3, 'ROSS': 3, 'DAN': 0, 'CHARLIE': 3, 'PIERS': 3, 'VIC': 1, 'ANDY': 3},
    'CHARLIE': {'JOE': 3, 'WILL': 4, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 4, 'KYLE': 4, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 0, 'PIERS': 3, 'VIC': 1, 'ANDY': 3},
    'PIERS': {'JOE': 3, 'WILL': 3, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 3, 'KYLE': 3, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 3, 'PIERS': 0, 'VIC': 2, 'ANDY': 3},
    'VIC': {'JOE': -2, 'WILL': -2, 'BARNEY': 0, 'PAUL': -2, 'PHIL': -2, 'KYLE': -2, 'SAM': -2, 'FARMAR': -1, 'ROSS': -2, 'DAN': 2, 'CHARLIE': -3, 'PIERS': -2, 'VIC': 0, 'ANDY': -2},
    'WILL': {'JOE': 3, 'WILL': 0, 'BARNEY': 3, 'PAUL': 3, 'PHIL': 4, 'KYLE': 4, 'SAM': 3, 'FARMAR': 3, 'ROSS': 3, 'DAN': 3, 'CHARLIE': 4, 'PIERS': 3, 'VIC': 2, 'ANDY': 3},
}

# add_value(InteractionDictEP7, "ROSS", "BARNEY", 1)
# add_value(InteractionDictEP7, "BARNEY", "ROSS", 1)
# subPGroup(InteractionDictEP7, "VIC", 1)
# add_value(InteractionDictEP7, "ROSS", "VIC", 1)
# subPGroup(InteractionDictEP7, "SAM", 1)
# sub_value(InteractionDictEP7, "VIC", "SAM", 1)
# sub_value(InteractionDictEP7, "SAM", "VIC", 1)
# subPGroup(InteractionDictEP7, "VIC", 2)
# sub_valueP(InteractionDictEP7, "VIC", 1)
# sub_value(InteractionDictEP7, "VIC", "FARMAR", 1)
# sub_value(InteractionDictEP7, "FARMAR", "VIC", 1)
# add_mutual(InteractionDictEP7,["WILL", "CHARLIE", "KYLE", "PHIL"], 1)
# add_value_all(InteractionDictEP7, 1)
# sub_value(InteractionDictEP7, "VIC", "KYLE", 1)
# sub_value(InteractionDictEP7, "KYLE", "VIC", 1)
# add_value(InteractionDictEP7, "SAM", "BARNEY", 1)
# sub_value(InteractionDictEP7, "SAM", "VIC", 1)
# subPGroup(InteractionDictEP7, "VIC", 1)
# sub_value(InteractionDictEP7, "VIC", "CHARLIE", 1)
# sub_value(InteractionDictEP7, "CHARLIE", "VIC", 1)
# sub_value(InteractionDictEP7, "KYLE", "VIC", 1)
# add_value_all(InteractionDictEP7, 1)

# sub_value(InteractionDictEP7, "VIC", "SAM", 1)
# sub_value(InteractionDictEP7, "SAM", "VIC", 1)
# subPGroup(InteractionDictEP7, "VIC", 1)
# sub_value(InteractionDictEP7, "VIC", "SAM", 2)
# sub_value(InteractionDictEP7, "SAM", "VIC", 2)
# sub_value(InteractionDictEP7, "DAN", "SAM", 1)
# sub_value(InteractionDictEP7, "DAN", "VIC", 1)
# add_value(InteractionDictEP7, "VIC", "KYLE", 1)
# add_value(InteractionDictEP7, "KYLE", "VIC", 1)

# add_value(InteractionDictEP7, "VIC", "SAM", 2)
# add_value(InteractionDictEP7, "VIC", "DAN", 2)

# add_value(InteractionDictEP7, "KYLE", "DAN", 2)
# add_value(InteractionDictEP7, "KYLE", "SAM", 2)

# add_value(InteractionDictEP7, "VIC", "DAN", 2)
# add_value(InteractionDictEP7, "VIC", "SAM", 2)
# add_value(InteractionDictEP7, "VIC", "FARMAR", 2)
# add_value(InteractionDictEP7, "VIC", "BARNEY", 2)

# add_value(InteractionDictEP7, "SAM", "VIC", 1)
# add_value_all(InteractionDictEP7, 1)


# delSelfLoops(InteractionDictEP7)
# print_dictionary(InteractionDictEP7)

InterDictEP8 = {
    'JAMIE': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 2, 'LAUREN': 1.5, 'BETH': 2.5, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 0, },
    'FI': {'FI': 0, 'JULIE': 0, 'BELINDA': 2, 'LAUREN': 3.5, 'BETH': 3.5, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 1, },
    'FRAN': {'FI': 0, 'JULIE': 0, 'BELINDA': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 0, 'ABBY': 0, 'JAMIE': 0, },
    'JULIE': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 2, 'LAUREN': 3.5, 'BETH': 2.5, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 1, },
    'BELINDA': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 0, 'LAUREN': 4.5, 'BETH': 3.5, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 4, 'ABBY': 1, 'JAMIE': 2, },
    'JAYDE': {'FI': 0, 'JULIE': 0, 'BELINDA': 0, 'LAUREN': 0, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 0, 'ABBY': 0, 'JAMIE': 0, },
    'LAUREN': {'FI': 0.5, 'JULIE': 1, 'BELINDA': 3.5, 'LAUREN': 0, 'BETH': 6.0, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 4.5, 'ABBY': 1, 'JAMIE': 2, },
    'BETH': {'FI': 0.5, 'JULIE': 0, 'BELINDA': 3, 'LAUREN': 6.5, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': -0.5, 'GEORGIE': 4, 'ABBY': 2, 'JAMIE': 3, },
    'GEORGINA': {'FI': -0.5, 'JULIE': 1, 'BELINDA': 10, 'LAUREN': 8.5, 'BETH': 8.5, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': 1.5, 'GEORGIE': 10, 'ABBY': 2, 'JAMIE': 3, },
    'CHAVALA': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 2, 'LAUREN': 2.5, 'BETH': 3.5, 'GEORGINA': 0, 'CHAVALA': 0, 'BEKI': -0.5, 'GEORGIE': 4, 'ABBY': 1, 'JAMIE': 2, },
    'BEKI': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 2, 'LAUREN': 2.5, 'BETH': 2.5, 'GEORGINA': 1, 'CHAVALA': 0, 'BEKI': 0, 'GEORGIE': 4, 'ABBY': 1, 'JAMIE': 1, },
    'GEORGIE': {'FI': -0.5, 'JULIE': 0, 'BELINDA': 3, 'LAUREN': 4.5, 'BETH': 3.5, 'GEORGINA': 1, 'CHAVALA': 2, 'BEKI': 1.5, 'GEORGIE': 0, 'ABBY': 1, 'JAMIE': 2, },
    'ABBY': {'FI': 0.5, 'JULIE': 1, 'BELINDA': 3, 'LAUREN': 3.5, 'BETH': 4.5, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 0.5, 'GEORGIE': 3, 'ABBY': 0, 'JAMIE': 2, },
}

InterDictEP9 = {
    'JAMIE': {'FI': -2, 'JULIE': -1, 'BELINDA': 4, 'LAUREN': 1, 'BETH': 0, 'GEORGINA': -3, 'CHAVALA': -2.5, 'BEKI': -2, 'GEORGIE': -3, 'ABBY': -3, 'JAMIE': 0},
    'FI': {'FI': 0, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1, 'ABBY': 1, 'JAMIE': 2},
    'JULIE': {'FI': 2, 'JULIE': 0, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 4, 'GEORGINA': 4, 'CHAVALA': 2, 'BEKI': 2, 'GEORGIE': 2, 'ABBY': 1, 'JAMIE': 2},
    'BELINDA': {'FI': 2, 'JULIE': 1, 'BELINDA': 0, 'LAUREN': 5, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1, 'ABBY': 1, 'JAMIE': 6},
    'LAUREN': {'FI': 1, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 0, 'BETH': 6, 'GEORGINA': 0, 'CHAVALA': 2, 'BEKI': 3, 'GEORGIE': 0, 'ABBY': 0, 'JAMIE': -1},
    'BETH': {'FI': 1, 'JULIE': 3, 'BELINDA': 4, 'LAUREN': 7, 'BETH': 0, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': 3, 'GEORGIE': 1, 'ABBY': 0, 'JAMIE': 1},
    'GEORGINA': {'FI': 2, 'JULIE': 6, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 0, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1, 'ABBY': 1, 'JAMIE': 2},
    'CHAVALA': {'FI': 2, 'JULIE': 2, 'BELINDA': 4, 'LAUREN': 6, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 0, 'BEKI': 3, 'GEORGIE': 1, 'ABBY': 1, 'JAMIE': 1.5},
    'BEKI': {'FI': 2, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 6, 'BETH': 5, 'GEORGINA': 1, 'CHAVALA': 2, 'BEKI': 0, 'GEORGIE': 1, 'ABBY': 1, 'JAMIE': 1},
    'GEORGIE': {'FI': 2, 'JULIE': 2, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 4, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 0, 'ABBY': 1, 'JAMIE': 2},
    'ABBY': {'FI': 2, 'JULIE': 1, 'BELINDA': 4, 'LAUREN': 4, 'BETH': 3, 'GEORGINA': 1, 'CHAVALA': 1, 'BEKI': 2, 'GEORGIE': 1, 'ABBY': 0, 'JAMIE': 2},
}

InteractionDictEP10 = {
    'BARNEY': {'WILL': 4, 'BARNEY': 0, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3},
    'PHIL': {'WILL': 4, 'BARNEY': 2, 'PHIL': 0, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 3, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 4},
    'KYLE': {'WILL': 4, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 0, 'SAM': 2, 'FARMAR': 2, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3},
    'SAM': {'WILL': 5, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 0, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3},
    'FARMAR': {'WILL': 7, 'BARNEY': 4, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 2},
    'ROSS': {'WILL': 5, 'BARNEY': 1, 'PHIL': 2, 'KYLE': 1, 'SAM': 1, 'FARMAR': 0, 'ROSS': 0, 'DAN': 1, 'CHARLIE': 1, 'PIERS': 2, 'VIC': 3},
    'DAN': {'WILL': 4, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': -1, 'ROSS': 2, 'DAN': 0, 'CHARLIE': 2, 'PIERS': 2, 'VIC': 3},
    'CHARLIE': {'WILL': 5, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 0, 'ROSS': 2, 'DAN': 2, 'CHARLIE': 0, 'PIERS': 2, 'VIC': 3},
    'PIERS': {'WILL': 7, 'BARNEY': 2, 'PHIL': 2, 'KYLE': 2, 'SAM': 2, 'FARMAR': 2, 'ROSS': 3, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 0, 'VIC': 4},
    'VIC': {'WILL': 3, 'BARNEY': 2, 'PHIL': 3, 'KYLE': 2, 'SAM': 2, 'FARMAR': -4, 'ROSS': 3, 'DAN': 2, 'CHARLIE': 2, 'PIERS': 3, 'VIC': 0},
    'WILL': {'WILL': 0, 'BARNEY': 0, 'PHIL': 0, 'KYLE': 0, 'SAM': 1, 'FARMAR': 3, 'ROSS': 2, 'DAN': 0, 'CHARLIE': 0, 'PIERS': 3, 'VIC': 0},
}


InterDictEP11 = {
    'JAMIE': {'FI': 13, 'JULIE': 11.0, 'BELINDA': 12.5, 'LAUREN': 14.0, 'BETH': 13.0, 'GEORGINA': 11.5, 'CHAVALA': 12.5, 'BEKI': 11, 'GEORGIE': 13, 'ABBY': 10, 'JAMIE': 0},
    'FI': {'FI': 0, 'JULIE': 12.0, 'BELINDA': 10.0, 'LAUREN': 15, 'BETH': 13.5, 'GEORGINA': 11.5, 'CHAVALA': 10.5, 'BEKI': 11, 'GEORGIE': 14.5, 'ABBY': 10, 'JAMIE': 10.0},
    'JULIE': {'FI': 14.0, 'JULIE': 0, 'BELINDA': 10.5, 'LAUREN': 14.5, 'BETH': 13.5, 'GEORGINA': 13.5, 'CHAVALA': 12.0, 'BEKI': 12, 'GEORGIE': 15.0, 'ABBY': 10, 'JAMIE': 8.5},
    'BELINDA': {'FI': 13, 'JULIE': 11.0, 'BELINDA': 0, 'LAUREN': 14.0, 'BETH': 14.0, 'GEORGINA': 11.5, 'CHAVALA': 11.5, 'BEKI': 11, 'GEORGIE': 12.5, 'ABBY': 10, 'JAMIE': 10.5},
    'LAUREN': {'FI': 15.5, 'JULIE': 13.5, 'BELINDA': 11.0, 'LAUREN': 0, 'BETH': 13.5, 'GEORGINA': 13.0, 'CHAVALA': 13.5, 'BEKI': 15.0, 'GEORGIE': 16.0, 'ABBY': 10, 'JAMIE': 9.5},
    'BETH': {'FI': 14.5, 'JULIE': 13.0, 'BELINDA': 11.5, 'LAUREN': 14.0, 'BETH': 0, 'GEORGINA': 13.5, 'CHAVALA': 12.5, 'BEKI': 13.0, 'GEORGIE': 16.0, 'ABBY': 10, 'JAMIE': 10.0},
    'GEORGINA': {'FI': 13.5, 'JULIE': 12.5, 'BELINDA': 10.5, 'LAUREN': 14.5, 'BETH': 14.5, 'GEORGINA': 0, 'CHAVALA': 11.5, 'BEKI': 11, 'GEORGIE': 13.5, 'ABBY': 10, 'JAMIE': 11.5},
    'CHAVALA': {'FI': 12.5, 'JULIE': 11.5, 'BELINDA': 10.5, 'LAUREN': 15.0, 'BETH': 13.5, 'GEORGINA': 11.5, 'CHAVALA': 0, 'BEKI': 13.5, 'GEORGIE': 13.5, 'ABBY': 10, 'JAMIE': 12.0},
    'BEKI': {'FI': 13, 'JULIE': 12.0, 'BELINDA': 10.0, 'LAUREN': 15.5, 'BETH': 14, 'GEORGINA': 11, 'CHAVALA': 12.5, 'BEKI': 0, 'GEORGIE': 14.5, 'ABBY': 10, 'JAMIE': 11.0},
    'GEORGIE': {'FI': 14.5, 'JULIE': 13.5, 'BELINDA': 9.5, 'LAUREN': 15.5, 'BETH': 15, 'GEORGINA': 11.5, 'CHAVALA': 11.5, 'BEKI': 13.5, 'GEORGIE': 0, 'ABBY': 10, 'JAMIE': 10.0},
    'ABBY': {'FI': 12, 'JULIE': 10.0, 'BELINDA': 10, 'LAUREN': 11, 'BETH': 11, 'GEORGINA': 10, 'CHAVALA': 10, 'BEKI': 10, 'GEORGIE': 12, 'ABBY': 0, 'JAMIE': 10},
}

# add_mutual(InterDictEP11, ["BEKI", "JULIE", "GEORGIE"], 1)
# add_value(InterDictEP11, "JAMIE", "BELINDA", 1)
# add_value(InterDictEP11, "BELINDA", "JAMIE", 1)
# add_mutual(InterDictEP11, ["CHAVALA", "JULIE", "GEORGIE","GEORGINA","FI"], 0.5)
# add_value(InterDictEP11, "JAMIE", "CHAVALA", 0.5)
# add_value(InterDictEP11, "CHAVALA", "JAMIE", 0.5)
# sub_valueP(InterDictEP11, "JULIE", 0.5)
# add_mutual(InterDictEP11, ["JAMIE", "BETH", "LAUREN", "BELINDA", "GEORGINA"], 0.5)
# sub_value(InterDictEP11,"BETH", "JAMIE", 1)
# sub_value(InterDictEP11,"LAUREN", "JAMIE", 1)
# sub_value(InterDictEP11,"BELINDA", "JAMIE", 1)
# sub_value(InterDictEP11,"JULIE", "JAMIE", 1)

# sub_value(InterDictEP11,"LAUREN", "JAMIE", 1)
# sub_value(InterDictEP11,"BELINDA", "JAMIE", 1)
# sub_value(InterDictEP11,"JULIE", "JAMIE", 1)

# add_value(InterDictEP11, "JULIE", "FI", 0.5)
# add_value(InterDictEP11, "FI", "JULIE", 0.5)

# sub_value(InterDictEP11,"BETH", "JAMIE", 1)
# sub_value(InterDictEP11,"JAMIE", "BETH", 1)
# sub_value(InterDictEP11,"FI", "JAMIE", 1)

# add_value(InterDictEP11, "BETH", "FI", 0.5)
# add_value(InterDictEP11, "FI", "BETH", 0.5)

# add_group(InterDictEP11, "GEORGIE", ["BELINDA","CHAVALA"], -0.5)
# add_group(InterDictEP11, "BETH", ["BELINDA","CHAVALA"], -0.5)
# add_group(InterDictEP11, "LAUREN", ["BELINDA","CHAVALA"], -0.5)

# add_group(InterDictEP11,"BELINDA", ["GEORGIE","BETH","LAUREN"], -0.5)
# add_group(InterDictEP11,"CHAVALA", ["GEORGIE","BETH","LAUREN"], -0.5)

# grouptoPerson(InterDictEP11,"JAMIE", ["GEORGIE","BETH","LAUREN"], -1)

# add_mutual(InterDictEP11, ["CHAVALA","JAMIE","BELINDA"], 0.5)
# add_value(InterDictEP11, "JULIE", "GEORGINA", 1)
# add_group(InterDictEP11,"JULIE", ["CHAVALA","BELINDA"], 0.5)
# add_group(InterDictEP11,"JULIE", ["GEORGIE","BETH","LAUREN","JAMIE"], -0.5)

# add_mutual(InterDictEP11, ["CHAVALA", "GEORGIE", "LAUREN", "BEKI"], 0.5)
# grouptoPerson(InterDictEP11,"BEKI", ["CHAVALA", "GEORGIE", "LAUREN"], 1)
# add_value(InterDictEP11, "JAMIE", "CHAVALA", 0.5)

# add_value(InterDictEP11, "FI", "GEORGIE", 1)
# add_value(InterDictEP11, "GEORGIE", "FI", 1)

# add_valueP(InterDictEP11, "FI", 1)
# add_valueP(InterDictEP11, "GEORGIE", 1)

# add_valueP(InterDictEP11, "GEORGIE", 1)

# grouptoPerson(InterDictEP11,"JAMIE", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE"], -0.5)
# grouptoPerson(InterDictEP11,"BELINDA", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE"], -0.5)

# grouptoPerson(InterDictEP11,"JAMIE", ["BETH", "LAUREN"], -1)
# grouptoPerson(InterDictEP11,"BELINDA", ["LAUREN", "BETH"], -1)

# grouptoPerson(InterDictEP11,"JAMIE", ["BETH", "LAUREN"], -1)
# add_group(InterDictEP11,"JAMIE", ["BETH", "LAUREN"], -1)

# grouptoPerson(InterDictEP11,"BETH", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA"], 1)
# grouptoPerson(InterDictEP11,"LAUREN", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA"], 1)
# add_group(InterDictEP11,"JAMIE", ["BETH", "LAUREN"], 0.5)

# add_group(InterDictEP11,"BETH", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA","JAMIE"], 0.5)
# add_group(InterDictEP11,"LAUREN", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA","JAMIE"], 0.5)

# add_group(InterDictEP11,"GEORGIE", ["BETH", "LAUREN"], 1)
# grouptoPerson(InterDictEP11,"GEORGIE", ["BETH", "LAUREN"], 1)

# add_both_value(InterDictEP11,"JAMIE","LAUREN",1)
# add_both_value(InterDictEP11, "LAUREN", "FI",1)

# grouptoPerson(InterDictEP11,"JAMIE", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BETH","LAUREN"], 0.5)
# grouptoPerson(InterDictEP11,"BELINDA", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BETH","LAUREN"], 0.5)
# add_both_value(InterDictEP11,"JAMIE", "BELINDA", 0.5)

# add_group(InterDictEP11,"BETH", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA","JAMIE", "LAUREN"], 0.5)
# add_value_all(InterDictEP11, 1)

# grouptoPerson(InterDictEP11,"JAMIE", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BETH","LAUREN"], 1)

# add_group(InterDictEP11,"BETH", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA","JAMIE"], 1)
# add_group(InterDictEP11,"LAUREN", ["BEKI", "CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE", "BELINDA","JAMIE"], 1)

# add_mutual(InterDictEP11, ["FI","JULIE","GEORGIE","GEORGINA"],1)

# add_both_value(InterDictEP11, "LAUREN", "JULIE", 1)

# add_value_all(InterDictEP11, 1)

# add_valueP(InterDictEP11, "BETH",1)

# add_both_value(InterDictEP11, "BETH", "JAMIE", 1)

# add_value_all(InterDictEP11, 1)

# add_mutual(InterDictEP11, ["BEKI","LAUREN","BETH"],1)
# grouptoPerson(InterDictEP11,"BEKI", ["CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE","JAMIE","BELINDA"], 1)
# grouptoPerson(InterDictEP11,"LAUREN", ["CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE","JAMIE","BELINDA"], 1)
# grouptoPerson(InterDictEP11,"BETH", ["CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE","JAMIE","BELINDA"], 1)

# grouptoPerson(InterDictEP11,"LAUREN", ["CHAVALA", "GEORGINA", "JULIE", "FI","GEORGIE","JAMIE","BELINDA","BEKI","BETH"], 1)


# add_value_all(InterDictEP11, 1)

# grouptoPerson(InterDictEP11,"BETH", ["BEKI","JAMIE","LAUREN","BELINDA"],1)
# grouptoPerson(InterDictEP11,"CHAVALA", ["BEKI","JAMIE","LAUREN","BELINDA"],1)
# grouptoPerson(InterDictEP11,"JULIE", ["BEKI","JAMIE","LAUREN","BELINDA"],1)
# grouptoPerson(InterDictEP11,"GEORGIE", ["BEKI","JAMIE","LAUREN","BELINDA"],1)
# grouptoPerson(InterDictEP11,"GEORGINA", ["BEKI","JAMIE","LAUREN","BELINDA"],1)
# grouptoPerson(InterDictEP11,"FI", ["BEKI","JAMIE","LAUREN","BELINDA"],1)
# add_both_value(InterDictEP11,"GEORGINA","GEORGINA",1)
# add_both_value(InterDictEP11,"JULIE","GEORGIE",1)
# add_both_value(InterDictEP11,"JULIE","GEORGINA",1)
# grouptoPerson(InterDictEP11,"CHAVALA", ["JULIE","GEORGIE","GEORGINA"],1)
# add_group(InterDictEP11,"CHAVALA",["JULIE","GEORGIE","GEORGINA"], 1)
# grouptoPerson(InterDictEP11,"BETH", ["CHAVALA","JULIE","GEORGIE","GEORGINA"],1)
# add_group(InterDictEP11,"BETH",["CHAVALA","JULIE","GEORGIE","GEORGINA"], 1)

# grouptoPerson(InterDictEP11,"FI", ["BETH","GEORGIE","GEORGINA","BELINDA","FI","LAUREN","JULIE","CHAVALA","BEKI","ABBY","JAMIE"],1)

# add_mutual(InterDictEP11,["BETH","GEORGIE","GEORGINA","BELINDA","FI","LAUREN","JULIE","CHAVALA","BEKI","ABBY","JAMIE"],1)

# add_mutual(InterDictEP11,["BETH","GEORGIE","GEORGINA","BELINDA","FI","LAUREN","JULIE","CHAVALA","BEKI","ABBY","JAMIE"],1)

# grouptoPerson(InterDictEP11,"LAUREN", ["BETH","GEORGIE","GEORGINA","BELINDA","FI","LAUREN","JULIE","CHAVALA","BEKI","ABBY","JAMIE"],1)
# add_mutual(InterDictEP11,["BETH","GEORGIE","GEORGINA","BELINDA","FI","LAUREN","JULIE","CHAVALA","BEKI","ABBY","JAMIE"],1)
# add_value_all(InterDictEP11, 1)

# add_both_value(InterDictEP11,"CHAVALA", "LAUREN",1)
# add_both_value(InterDictEP11,"BEKI", "LAUREN",1)
# add_both_value(InterDictEP11,"BEKI", "CHAVALA",1)

# grouptoPerson(InterDictEP11,"JULIE", ["BETH","GEORGIE","GEORGINA","BELINDA","FI","LAUREN","JULIE","CHAVALA","BEKI","ABBY","JAMIE"],0.5)
# add_value_all(InterDictEP11, 2)

# delSelfLoops(InterDictEP11)
# print_dictionary(InterDictEP11)


InteractionDictEP12 = {
    'BARNEY': {'WILL': 8, 'BARNEY': 0, 'PHIL': 8, 'KYLE': 9, 'SAM': 11, 'FARMAR': 9, 'ROSS': 11, 'DAN': 9, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 11},
    'PHIL': {'WILL': 8, 'BARNEY': 9, 'PHIL': 0, 'KYLE': 10, 'SAM': 10, 'FARMAR': 9, 'ROSS': 11, 'DAN': 9, 'CHARLIE': 8, 'PIERS': 10, 'VIC': 10},
    'KYLE': {'WILL': 8, 'BARNEY': 8, 'PHIL': 8, 'KYLE': 0, 'SAM': 10, 'FARMAR': 9, 'ROSS': 12, 'DAN': 9, 'CHARLIE': 8, 'PIERS': 9, 'VIC': 9},
    'SAM': {'WILL': 8, 'BARNEY': 11, 'PHIL': 8, 'KYLE': 10, 'SAM': 0, 'FARMAR': 9, 'ROSS': 12, 'DAN': 9, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 11},
    'FARMAR': {'WILL': 8, 'BARNEY': 11, 'PHIL': 9, 'KYLE': 11, 'SAM': 11, 'FARMAR': 0, 'ROSS': 13, 'DAN': 10, 'CHARLIE': 8, 'PIERS': 10, 'VIC': 11},
    'ROSS': {'WILL': 8, 'BARNEY': 9, 'PHIL': 8, 'KYLE': 12, 'SAM': 11, 'FARMAR': 9, 'ROSS': 0, 'DAN': 10, 'CHARLIE': 9, 'PIERS': 10, 'VIC': 10},
    'DAN': {'WILL': 7, 'BARNEY': 8, 'PHIL': 7, 'KYLE': 9, 'SAM': 9, 'FARMAR': 8, 'ROSS': 10, 'DAN': 0, 'CHARLIE': 8, 'PIERS': 8, 'VIC': 9},
    'CHARLIE': {'WILL': 7, 'BARNEY': 7, 'PHIL': 6, 'KYLE': 8, 'SAM': 7, 'FARMAR': 6, 'ROSS': 10, 'DAN': 9, 'CHARLIE': 0, 'PIERS': 7, 'VIC': 7},
    'PIERS': {'WILL': 8, 'BARNEY': 9, 'PHIL': 8, 'KYLE': 9, 'SAM': 9, 'FARMAR': 8, 'ROSS': 11, 'DAN': 8, 'CHARLIE': 7, 'PIERS': 0, 'VIC': 9},
    'VIC': {'WILL': 8, 'BARNEY': 10, 'PHIL': 8, 'KYLE': 9, 'SAM': 11, 'FARMAR': 9, 'ROSS': 10, 'DAN': 9, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 0},
    'WILL': {'WILL': 0, 'BARNEY': 8, 'PHIL': 8, 'KYLE': 9, 'SAM': 9, 'FARMAR': 8, 'ROSS': 10, 'DAN': 8, 'CHARLIE': 7, 'PIERS': 9, 'VIC': 9},
}