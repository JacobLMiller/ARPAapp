import csv

def ConvertCSVToList(fileToOpen):
    """
    This method will take any csv file and
    convert it to a list of lists, with rows being a list
    with each entry as elements.
    """

    with open(fileToOpen, newline='') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
        geoData = [[]]
        index = 0
        #print(isinstance(geoData, list))

        for row in spamreader:
            geoData.append([])
            for x in row:
                geoData[index].append(x)
            index += 1
        geoData.pop()

        csvfile.close()

        return geoData

def ListToDict(geoData):
    """
    This method takes an output from ConvertCSVToList and puts out
    a Dictionary with the name of a site as a key (str) and the value
    associated with it as a tuple with x and y coordinates respectively
    """

    geoDataDict = {}
    indexOfName = -1
    indexOfXValue = -1
    indexOfYValue = -1

    for x in range(len(geoData[0])):
        if (geoData[0][x] == "name"):
            indexOfName = x
        elif(geoData[0][x] == "X"):
            indexOfXValue = x
        elif(geoData[0][x] == "Y"):
            indexOfYValue = x

    for x in range(len(geoData)):
        if(geoData[x][0].isalpha()):
            continue
        if(geoData[x][indexOfName] == "" or geoData[x][indexOfXValue] == ""):
            continue
        tempStr = geoData[x][indexOfName]
        geoDataDict[tempStr] = \
        (float(geoData[x][indexOfXValue]), float(geoData[x][indexOfYValue]))

    return geoDataDict

def main():
    fileToOpen = 'Buildings.csv'

    geoData = ConvertCSVToList(fileToOpen)
    geoDataDict = ListToDict(geoData)

    print(geoDataDict)

main()
