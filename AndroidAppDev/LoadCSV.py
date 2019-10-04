import csv
with open('Buildings.csv', newline='') as csvfile:
     spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
     listOfLists = []
     for row in spamreader:
         listOfLists.append(row)
         print(listOfLists)
        #print(row)
