import csv
with open('Buildings.csv', newline='') as csvfile:
     spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
     listOfLists = []
     counter = 0
     print(type(listOfLists[0]) == "list")
     '''for row in spamreader:
         for x in row:
             listOfLists[counter].append(x)
         counter += 1
     print(listOfLists)
'''
