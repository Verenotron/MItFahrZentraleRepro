import json
import os

pfad_zur_datei = os.path.dirname(__file__)
dateipfad = os.path.join(pfad_zur_datei, "uebersetzungen.txt")

with open(dateipfad) as file:
    text = file.readlines()

sprachen = text[0].strip('\n').split(',')
meinDict = {}

for line in text[1:]:
    key = line.split(':')[0]
    values = line.split(':')[1].strip('\n').split(',')
    meinDict[key] = values

for index in range(len(sprachen)):
    sprache = sprachen[index]
    dictMitEinerSprache = {key : value[index] for key, value in meinDict.items()}

    with open(f"./src/locales/{sprache}"+".json", "w") as json_file:
        jsonstring = json.dump(dictMitEinerSprache, json_file, indent=len(dictMitEinerSprache), ensure_ascii=False) 
        #ensureAscii damit Umlaute übernommen werden
    