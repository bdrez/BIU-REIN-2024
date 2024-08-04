# BIU-REIN-2024
Authors: Blimy Dresdner and Hannah Zahler

This project was developed starting in summer 2024 as part of the RE:IN gene networks project. Advised by Prof Hillel Kugler and PhD student Eitan Tannenbaum as part of the BIU-YU Summer Research program.

> *NOTE: this README assumes prior familiarity with [RE:IN](https://www.nature.com/articles/npjsba201610). For an explanation of the technical background (both biological and computational)

The goal of this project is to convert a BNET and AEON files from the [biodivine-boolean-models repository on GitHub](https://github.com/sybila/biodivine-boolean-models) into REIN files that can then be processed by the reasoning engine to verify the set of constraints and specifications for each model, ensuring they are satisfiable by checking if solutions can be found.

- I. The first tool requires you to input the path to the BNET file from GitHub into the code. It then automates the creation of a file that can be imported into bioLQM to determine the fixpoints of the model.

- II. The second tool 
II. The second tool works by comparing optional interactions in a .rein file and a corresponding excel file which contains numbers indicating how confident we are that an optional interaction will occur. It then prompts the user for the number of new filtered out .rein files they want. Based on this number and the number of optional interactions present in the original .rein file, our program calculates threshold numbers that will output a gradually decreasing (and uniform) number of optional interactions in each new output .rein file it creates. Our program also outputs a text file of commands to input into RE:IN so that they can be tested.


