const _ = require('lodash');

var exports = module.exports = {};
let elecLevString = [];
let vertical = [];
let elecLevel = [];

function orbitals(electrons){
    if(electrons <= 2) {
        return 1;
    }
    let levels = 0;
    let numElectrons = 0;
    for(i of _.range(1, electrons + 100)) {
        for(j of _.range(2)) {
            if(numElectrons >= electrons) {
                return levels;
            }

            levels += 1;
            numElectrons += 2 * i * i;
        }
    }
    return -1;
}

function makeArrayList(possibleLevels) {
    for(let i of _.range(1, possibleLevels+1)) {
        temp = [];
        for(x of _.range(i)) {
            if(x == 0) {
                temp.push(2);
                continue;
            }
            temp.push(temp[temp.length -1] + 4);
        }
        vertical.push(temp);
    }
}

function clearUseless(){
    for(let i of _.range(vertical.length) ){
        let temp = vertical[vertical.length -1-i];
        if(temp.length > i + 1) {
            for(let x of _.range(i+1, temp.length)) {
                temp.splice(i+1, 1);
            }
        }

        vertical[vertical.length -1 -i] = temp;
    }
}

function printConfig(){
    for(i of _.range(vertical.length)) {
        horizontal = vertical[i];
	for(let x of _.range(horizontal.length)) {
            process.stdout.write(horizontal[x] + "  ");//, end =  "  ");
	}
        console.log(" ");
    }
}

function possibleElec(){
    let electrons = 0;
    for(let v of vertical) {
        electrons += v.reduce((a, b) => a + b, 0);
    }

    return electrons;
}

function finalConfig(electrons){
    let once = true;
    let possElec = possibleElec();
    let end = Math.ceil(vertical.length/2.0);
    for(let i of _.range(end)) {
        horizontal = vertical[vertical.length -1 -i];
        temp = horizontal[horizontal.length-1];
        if(possElec - temp > electrons) {
	    horizontal.splice(horizontal.length -1, 1);
            possElec -= temp;
        }
        else if(possElec - temp == electrons) {
	    horizontal.splice(horizontal.length -1, 1);
            possElec -= temp;
            once = false;
        }
        else if(once && possElec - temp < electrons) {
            horVal = horizontal[horizontal.length -1];
            tempVal = horVal - possElec + electrons;
            horizontal[horizontal.length -1] = tempVal;
            possElec = electrons;
            once = false;
        }

    }
    //remove empty lists
    vertical = vertical.filter(a => { if(a.length != 0) return a;});
}


function makeElecLevel() {
    for(let i of _.range(vertical.length)) {
        let sumElec = 0;
        let horizontal = vertical[i];
        for(x of _.range(horizontal.length)) {
            sumElec += horizontal[x];
        }
        elecLevel.splice(i, 0, sumElec);
    }
}

function printElecLevel() {
    for(let i of _.range(elecLevel.length)) {
        n = i+1;
        temp = elecLevel[i];
        console.log("Level " +  n + " " + temp + " electrons");
    }
}

function makeStringArrayList() {
    for(let i of _.range(vertical.length)) {
        let k = i + 1;
        let tempStr = "";
        let horizontal = vertical[i];
        for(let x of _.range(horizontal.length)) {
            temp = horizontal[x];
            tempStr += str(x) + " ";
        }

        elecLevString.push(tempStr);
    }
}

function lta(atomicNo) {
    if(atomicNo < 221) {
        makeAList();
    }
}

function makeAList(){
    let a = "Electron Configuration with oribtal names";
    elecLevString.push(a);
    for(let i of _.range(vertical.length)) {
        let tempStr = "";
        let temp = vertical[i];
        for(let x of _.range(temp.length)) {
            tempStr = tempStr + (i+1);
            if(x == 0) {
                tempStr += "s";
            }
            else if(x == 1) {
                tempStr += "p";
            }
            else if(x == 2) {
                tempStr += "d";
            }
            else if(x == 3) {
                tempStr += "f";
            }
            else if(x == 4) {
                tempStr += "g";
            }
            tempInt = temp[x];
            tempStr += tempInt + "  ";
        }
        elecLevString.push(tempStr);
    }
}

function getConfiguration(atomicNo) {
    elecLevString = [];
    vertical = [];
    elecLevel = [];
    let enlevels = orbitals(atomicNo);
    makeArrayList(enlevels);
    //printConfig();
    clearUseless();
    finalConfig(atomicNo);
    makeElecLevel();
    output = lta(atomicNo);
    
    if(atomicNo < 221) {
	for(let x of elecLevString) {
	    console.log(x);
	}
    }
    else {
	printConfig();
    }
    
    printElecLevel();
}

getConfiguration(10);
getConfiguration(100);
