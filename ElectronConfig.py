import math
import sys

elecLevString = []
vertical = []
elecLevel = []

def orbitals(electrons):
    if electrons <= 2:
        return 1
    levels = 0
    numElectrons = 0;
    for i in range(1, electrons + 100):
        for j in range(2):
            if numElectrons >= electrons:
                return levels

            levels += 1
            numElectrons += 2 * i * i

    return -1

def makeArrayList(possibleLevels):
    global vertical
    for i in range(1, possibleLevels+1):
        temp = []
        for x in range(i):
            if x == 0:
                temp.append(2)
                continue
            temp.append(temp[-1] + 4)
        vertical.append(temp)

def clearUseless():
    global vertical
    for i in range(len(vertical)):
        temp = vertical[-1-i]
        if len(temp) > i + 1:
            for x in range(i+1, len(temp)):
                del temp[i+1]

        vertical[-1-i] = temp

def printConfig():
    global vertical
    for i in range(len(vertical)):
        horizontal = vertical[i]
        for x in range(len(horizontal)):
            print(horizontal[x], end =  "  ")
            
        print(" ")

def possibleElec():
    global vertical
    electrons = 0
    for v in vertical:
        electrons += sum(v)
        #for x in range(len(horizontal)):
        #    electrons += horizontal[x]

    return electrons

def finalConfig(electrons):
    global vertical
    once = True
    possElec = possibleElec();
    end = int(math.ceil(len(vertical)/2.0))
    for i in range(end):
        horizontal = vertical[-1-i]
        temp = horizontal[-1]
        if possElec - temp > electrons:
            del horizontal[-1]
            possElec -= temp
        elif possElec - temp == electrons:
            del horizontal[-1]
            possElec -= temp
            once = False
        elif once and possElec - temp < electrons:
            horVal = horizontal[-1]
            tempVal = horVal - possElec + electrons
            horizontal[-1] = tempVal
            possElec = electrons
            once = False

    #remove empty lists
    vertical = [x for x in vertical if x]
        

def makeElecLevel():
    global vertical
    global elecLevel
    for i in range(len(vertical)):
        sumElec = 0
        horizontal = vertical[i]
        for x in range(len(horizontal)):
            sumElec += horizontal[x]
        
        elecLevel.insert(i, sumElec)

def printElecLevel():
    global elecLevel
    for i in range(len(elecLevel)):
        n = i+1
        temp = elecLevel[i]
        print("Level ", n, " ", temp, " electrons")

def makeStringArrayList():
    global vertical, elecLevString
    for i in range(len(vertical)):
        k = i + 1
        tempStr = ""
        horizontal = vertical[i]
        for x in range(len(horizontal)):
            temp = horizontal[x]
            tempStr += str(x) + " "

        elecLevString.append(tempStr)

def lta(atomicNo):
    if atomicNo < 221:
        makeAList()

def makeAList():
    global elecLevString, vertical
    a = "Electron Configuration with oribtal names"
    elecLevString.append(a)
    for i in range(len(vertical)):
        tempStr = ""
        temp = vertical[i]
        for x in range(len(temp)):
            tempStr = tempStr + str(i+1)
            if x == 0:
                tempStr += "s"
            elif x == 1:
                tempStr += "p"
            elif x == 2:
                tempStr += "d"
            elif x == 3:
                tempStr += "f"
            elif x == 4:
                tempStr += "g"
            tempInt = temp[x]
            tempStr += str(tempInt) + "  "

        elecLevString.append(tempStr)


atomicNo = int(sys.argv[1])
enlevels = orbitals(atomicNo)
makeArrayList(enlevels)
clearUseless()
finalConfig(atomicNo)
makeElecLevel()
output = lta(atomicNo)

if(atomicNo < 221):
    for x in elecLevString:
        print(x)
else:
    printConfig()

printElecLevel()


        
