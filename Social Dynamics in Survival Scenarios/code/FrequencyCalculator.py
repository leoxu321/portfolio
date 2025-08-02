import networkx as nx
import matplotlib.pyplot as plt

InterDict= {
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


InterDict1 = {
    
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


def count(InvovledArray):
    for key in InvovledArray:
        for key2 in InvovledArray:
            if key != key2:
                InterDict[key][key2] +=1


with open(r"C:\Users\Andrew Hall\Desktop\Social networks Project\Ep1-Tokenized.txt", "r",encoding="utf8") as f:
    lines = []
    actors = []
    for line in f:
        line.replace
        line = line.replace(":"," ").strip()
        print(line)
        if not line:  
            if lines:
                for key in InterDict:
                    for string in lines:
                        if key in string:
                            if key not in actors:
                                actors.append(key)
                if actors:
                    count(actors)
                actors = []
                lines = []
        else:
            lines.append(line)


def generateInteractionGraph(InterDict):
    G = nx.Graph()

    for node, neighbors in InterDict.items():
        for neighbor, weight in neighbors.items():
            if weight != 0:
                G.add_edge(node, neighbor, weight=weight)

    weights = [InterDict[edge[0]][edge[1]] for edge in G.edges()]

    pos = nx.spring_layout(G, k=0.99, iterations=20,weight='weight')

    fig, ax = plt.subplots(dpi=250)

    nx.draw(G, pos, with_labels=True, node_size=2000, node_color="skyblue", font_size=10, font_weight="bold", width=weights, edge_color="blue", alpha=1)
    
    ax.set_xticks([])
    ax.set_yticks([])

    plt.title('Interaction Graph Men\'s Island 6 (Episode 12 of the show)')
    plt.show()


generateInteractionGraph(InterDict)

