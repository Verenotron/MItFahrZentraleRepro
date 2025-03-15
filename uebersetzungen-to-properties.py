def readCSV():
    with open("./uebersetzungen.csv") as file:
        allLines = file.readlines()
    
    return allLines

def generate_propertiesFiles():
    allLines = readCSV()
    anzLanguages = len(allLines[0].split(";")) - 1
    aktSpracheDict = {}

    for i in range(1, anzLanguages + 1):
        for line in allLines:
            if line.startswith("#") or line == "\n":
                continue
            splittedLine = line.strip("\n").split(";")
            aktSpracheDict[splittedLine[0]] = splittedLine[i]

        aktLanguage = allLines[0].strip("\n").split(";")[i]
        with open(f"./src/main/resources/lang/messages_{aktLanguage}.properties","w") as file:
                for key, value in aktSpracheDict.items():
                    file.write(f"{key}={value}\n")
        file.close()

if __name__ == "__main__":
     generate_propertiesFiles()