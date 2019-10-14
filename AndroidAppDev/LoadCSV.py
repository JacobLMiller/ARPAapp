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

        return geoData

def ListToDict(geoData):
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
        if(geoData[0][0].isalpha()):
            print("I shouldn't be here")
            continue
        geoDataDict[str(geoData[x][indexOfName])] = \
        (int(geoData[x][indexOfXValue]), int(geoData[x][indexOfYValue]))
        print("I was here")

    print(geoDataDict)


    return geoDataDict

def main():
    fileToOpen = 'Buildings.csv'

    geoData = ConvertCSVToList(fileToOpen)
    geoDataDict = ListToDict(geoData)

main()
