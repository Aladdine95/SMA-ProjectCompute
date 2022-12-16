# SMA-ProjectCompute
M2 IISC - CY Cergy Paris Université
## Table of content
1. Overview
2. How to use it
3. Documentation
4. Known issues and bugs

## Overview
This program aims at computing an integral on a given interval, for a given function. The system computes the integral via multiple agents, each returning part of the result. For more precise information, you can get the subject here :
[link to the subject.](https://depinfo.u-cergy.fr/~pl/wiki/?ModuleSMA "sujet SMA")
The program is written in Java, and we use the JADE framework. 
This project can be see on [github](https://github.com/Aladdine95/SMA-ProjectCompute).

## How to use it
Use `java jade.Boot -gui [list agents]` to launch the JADE graphical user interface. You can then use the GUI to create the compute agents you want.
You will also need a testParallelAgent. When you create this agent, you have to pass some parameters: the higher bound of the interval, the lower bound of the interval, and the step, which defines the accuracy of the approximation.
If you do not create the compute agents, the program should create some for you.

## Simulation scenario
### With GUI
Create a number of ComputeAgents with or without arguments("Inverse"(default), "Exponential", "NeperianLogarithm"). Create a TestParallelAgent(no arguments = Inverse function with 0-1 interval and 0.1 step, 3 arguments = Inverse function with given interval and step - Format:upperBound,lowerBound,step , 4 arguments = Given function, interval and step - Format: upperBound,lowerBound,step,functionName). The results will appear in the console.

### Create 3 ComputeAgents and 1 TestParallelAgent
-agents ca0:process.ComputeAgent(Inverse);ca1:process.ComputeAgent(Inverse);ca2:process.ComputeAgent(Inverse);tpa0:test.TestParallelAgent(1,2.71828182846,0.01,Inverse) --port 8080 -gui

### Create 1 TestParallelAgent that will automatically create ComputeAgents
-agents tpa0:test.TestParallelAgent(1,2.71828182846,0.01,Inverse) --port 8080 -gui

## Documentation
The source code is commented and the Javadoc is generated. If you have to
generate it again, you can
type `javadoc -d doc src\*` given that every source file is in the src directory. The `-d doc` option sets the name of the directory in which the Javadoc will be generated.

## Known issues and bugs
If there are no agents during the creation of testParallelAgent, some computeAgents are created, but to use them, we have to create another Agent.
