/*
 * Decompiled with CFR 0_115.
 */
package my.elecConfig.lib;

import java.io.PrintStream;
import java.util.ArrayList;

public class elecConfig {
    public static ArrayList elecLevString = new ArrayList();
    public static ArrayList vertical = new ArrayList();
    public static ArrayList elecLevel = new ArrayList();

    public static String[] main(int atomicNo) {
        elecLevString.clear();
        vertical.clear();
        elecLevel.clear();
        int enlevels = elecConfig.orbitals(atomicNo);
        elecConfig.makeArrayList(enlevels);
        elecConfig.clearUseless();
        elecConfig.finalConfig(atomicNo);
        elecConfig.makeElecLevel();
        elecConfig.makeStringArrayList();
        return elecConfig.lta(atomicNo);
    }

    public static int orbitals(int electrons) {
        if (electrons <= 2) {
            return 1;
        }
        int levels = 0;
        int numElectrons = 0;
        for (int i = 1; i < electrons + 100; ++i) {
            for (int j = 0; j < 2; ++j) {
                if (numElectrons >= electrons) {
                    return levels;
                }
                ++levels;
                numElectrons += 2 * i * i;
            }
        }
        return -1;
    }

    public static void makeArrayList(int possibleLevels) {
        for (int i = 1; i <= possibleLevels; ++i) {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int x = 0; x < i; ++x) {
                if (x == 0) {
                    temp.add(new Integer(2));
                    continue;
                }
                Integer temporary = (Integer)temp.get(temp.size() - 1);
                int tempInt = temporary;
                int sum = tempInt + 4;
                temp.add(new Integer(sum));
            }
            vertical.add(temp);
        }
    }

    public static void clearUseless() {
        for (int i = 0; i < vertical.size(); ++i) {
            ArrayList temp = new ArrayList();
            temp = (ArrayList)vertical.get(vertical.size() - 1 - i);
            if (temp.size() > i + 1) {
                int size = temp.size();
                for (int x = i + 1; x < size; ++x) {
                    temp.remove(i + 1);
                }
            }
            vertical.set(vertical.size() - 1 - i, temp);
        }
    }

    public static void print() {
        for (int i = 0; i < vertical.size(); ++i) {
            ArrayList horizontal = (ArrayList)vertical.get(i);
            for (int x = 0; x < horizontal.size(); ++x) {
                Integer temp = (Integer)horizontal.get(x);
                int tempInt = temp;
                System.out.print("" + tempInt + "  ");
            }
            System.out.println();
        }
    }

    public static int possibleElec() {
        int electrons = 0;
        for (int i = 0; i < vertical.size(); ++i) {
            ArrayList horizontal = (ArrayList)vertical.get(i);
            for (int x = 0; x < horizontal.size(); ++x) {
                Integer temp = (Integer)horizontal.get(x);
                int tempInt = temp;
                electrons += tempInt;
            }
        }
        return electrons;
    }

    public static void finalConfig(int electrons) {
        boolean once = true;
        int possElec = 0;
        possElec = elecConfig.possibleElec();
        int end = 0;
        end = vertical.size() % 2 == 0 ? vertical.size() / 2 : vertical.size() / 2 + 1;
        for (int i = 0; i < end; ++i) {
            ArrayList horizontal = (ArrayList)vertical.get(vertical.size() - 1 - i);
            Integer temp = (Integer)horizontal.get(horizontal.size() - 1);
            int tempInt = temp;
            if (possElec - tempInt > electrons) {
                horizontal.remove(horizontal.size() - 1);
                possElec -= tempInt;
            } else if (possElec - tempInt == electrons) {
                horizontal.remove(horizontal.size() - 1);
                possElec -= tempInt;
                once = false;
            } else if (once && possElec - tempInt < electrons) {
                Integer horVal = (Integer)horizontal.get(horizontal.size() - 1);
                int intHorVal = horVal;
                Integer tempVal = new Integer(intHorVal - possElec + electrons);
                horizontal.set(horizontal.size() - 1, tempVal);
                possElec = electrons;
                once = false;
            }
            if (horizontal != null) continue;
            vertical.remove(vertical.size() - 1 - i);
        }
    }

    public static void makeElecLevel() {
        for (int i = 0; i < vertical.size(); ++i) {
            int sumElec = 0;
            ArrayList horizontal = (ArrayList)vertical.get(i);
            for (int x = 0; x < horizontal.size(); ++x) {
                Integer temp = (Integer)horizontal.get(x);
                int tempInt = temp;
                sumElec += tempInt;
            }
            elecLevel.add(i, new Integer(sumElec));
        }
    }

    public static void printElecLevel() {
        for (int i = 0; i < elecLevel.size(); ++i) {
            int n = i + 1;
            Integer temp = (Integer)elecLevel.get(i);
            int tempInt = temp;
            System.out.println("Level " + n + " " + tempInt + " electrons");
        }
    }

    public static void makeStringArrayList() {
        for (int i = 0; i < vertical.size(); ++i) {
            int k = i + 1;
            String temporary = "";
            ArrayList horizontal = (ArrayList)vertical.get(i);
            for (int x = 0; x < horizontal.size(); ++x) {
                Integer temp = (Integer)horizontal.get(x);
                temporary = temporary + temp.toString();
                temporary = temporary + " ";
            }
            elecLevString.add(temporary);
        }
    }

    public static String[] lta(int atomicNo) {
        if (atomicNo < 221) {
            elecConfig.makeAList();
        }
        String[] temp = new String[elecLevString.size()];
        for (int i = 0; i < temp.length; ++i) {
            temp[i] = (String)elecLevString.get(i);
        }
        return temp;
    }

    public static void makeAList() {
        String a = "Electron Configuration with orbital names";
        elecLevString.add(a);
        for (int i = 0; i < vertical.size(); ++i) {
            Integer tempObj = new Integer(i + 1);
            String tempString = "";
            ArrayList temp = new ArrayList();
            temp = (ArrayList)vertical.get(i);
            for (int x = 0; x < temp.size(); ++x) {
                tempString = tempString + tempObj.toString();
                if (x == 0) {
                    tempString = tempString + "s";
                } else if (x == 1) {
                    tempString = tempString + "p";
                } else if (x == 2) {
                    tempString = tempString + "d";
                } else if (x == 3) {
                    tempString = tempString + "f";
                } else if (x == 4) {
                    tempString = tempString + "g";
                }
                Integer tempInt = (Integer)temp.get(x);
                tempString = tempString + tempInt.toString();
                tempString = tempString + "  ";
            }
            elecLevString.add(tempString);
        }
    }
}
