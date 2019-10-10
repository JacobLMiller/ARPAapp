import csv
with open('Buildings.csv', newline='') as csvfile:
     spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
     geoData = [[]]
     counter = 0
     print(isinstance(geoData, list))
     for row in spamreader:
         for x in row:
             geoData[counter].append(x)
         counter += 1
         geoData.append([])
    # print(listOfLists)
