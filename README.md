# SMA-ProjectCompute
M2 IISC - CY Cergy Paris Université
## Table of content
1. Overview
2. How to use it
3. Documentation
4. V1 flaws and bugs

## Overview
This program aims at computing an integral on a given interval, for a given function. The system computes the integral via multiple agents, each returning part of the result. For more precise information, you can get the subject here :
[link to the subject.](https://depinfo.u-cergy.fr/~pl/wiki/?ModuleSMA "sujet SMA")
The program is written in Java, and we use the JADE framework. 
This project can be see on [github](https://github.com/Aladdine95/SMA-ProjectCompute).

## How to use it
Use `java jade.Boot -gui [list agents]` to launch the JADE graphical user interface. You can then use the GUI to create the compute agents you want.
You will also need a testParallelAgent. When you create this agent, you have to pass some parameters: the higher bound of the interval, the lower bound of the interval, and the step, which defines the accuracy of the approximation.
If you do not create the compute agents, the program should create some for you.

## Documentation
The source code is commented and the Javadoc is generated. If you have to
generate it again, you can
type `javadoc -d doc src\*` given that every source file is in the src directory. The `-d doc` option sets the name of the directory in which the Javadoc will be generated.

## Known issues and bugs
If there are no agents during the creation of testParallelAgent, some computeAgents are created, but to use them, we have to create another Agent.
