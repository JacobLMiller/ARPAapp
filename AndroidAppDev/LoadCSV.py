import csv

def ConvertCSVToList(fileToOpen):
    """This method will take any csv file and
    convert it to a list of lists, with rows being a list
    with each entry as elements. """

    with open(fileToOpen, newline='') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
        geoData = [[]]
        index = 0
        #print(isinstance(geoData, list))

        for row in spamreader:
            for x in row:
                geoData[index].append(x)
            index += 1
            geoData.append([])
        geoData.pop()

        return geoData

def ListToDict(geoData):
    geoDataDict = {}
    indexOfName = -1

    for x in range(len(geoData[0])):
        if (geoData[0][x] == "name"):
            indexOfName = x

    for x in range(len(geoData)):
        pass

    return geoDataDict

def main():
    fileToOpen = 'Buildings.csv'
    geoData = ConvertCSVToList(fileToOpen)
    geoDataDict = ListToDict(geoData)

main()
