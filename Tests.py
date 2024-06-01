#!/usr/bin/python3

import os
import sys
import time
from shutil import which

if __name__ == '__main__':

    if which("time") is None or which("/bin/zsh") is None:
        print('\033[93m'+ "Recommend to install time and zsh commands" + '\033[0m')


    tp_name = os.getcwd().split('/')[-1]
    assert(tp_name.startswith("TP"))

    files = [f for f in os.listdir('./Tests') if f.endswith('.txt')]
    files = sorted(files)

    for f in files :
        f = open("./Solutions/" + f.replace(".txt",".sol"), "w")
        f.close()


    files2 = [f for f in os.listdir('./Solutions') if f.endswith('.sol')]
    files2 = sorted(files2)

    os.system("javac ../*/*.java")


    for (f,f2) in zip(files, files2) :
        print("["+f+"] ", end = " ")
        sys.stdout.flush()
        os.system("java -classpath .. "+ tp_name+".Main Tests/\"" + f + "\"")
        time.sleep(1)
        if which("time") is not None and which("/bin/zsh") is not None:
            os.system("/bin/zsh -c 'time java -jar Solution.jar Tests/" + f + " " + "Solutions/"+ f2 + "'")
        else :
            os.system("java -jar Solution.jar Tests/" + f + " " + "Solutions/" + f2 + "")
        time.sleep(0.5)

    os.system("rm ../*/*.class")
