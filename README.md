# BIU-REIN-2024
Authors: Blimy Dresdner and Hannah Zahler

This project was developed starting in summer 2024 as part of the RE:IN gene networks project. Advised by Prof Hillel Kugler and PhD student Eitan Tannenbaum as part of the BIU-YU Summer Research program.

> *NOTE: this README assumes prior familiarity with [RE:IN](https://www.nature.com/articles/npjsba201610). For an explanation of the technical background (both biological and computational)

The goal of this project is to convert a BNET and AEON files from the [biodivine-boolean-models repository on GitHub](https://github.com/sybila/biodivine-boolean-models) into REIN files that can then be processed by the reasoning engine to verify the set of constraints and specifications for each model, ensuring they are satisfiable by checking if solutions can be found.

- I. The first tool requires you to input the path to the BNET file from GitHub into the code. It then automates the creation of a file that can be imported into bioLQM to determine the fixpoints of the model.

- II. The second tool converts AEON and the outputted fixpoint files from bioLQM into REIN files, allowing them to be processed by reasoning engines. It extracts gene relationships from AEON files and fixpoints from the fixpoint file and formats and integrates this data. It then outputs a REIN file that can then be used in the reasoning engine for further analysis of Boolean network models.



-------
# Instructions
-Download the AEON and BNET file from the desired [biodivine-boolean-models repository on GitHub](https://github.com/sybila/biodivine-boolean-models) and save them.

-Run the bnetToBiolqmGUI.java code and select the BNET file from the GUI to convert it. Then, in the second GUI, select/ create an output file for the converted BNET file in the desired output location. 

-Open bioLQM in the terminal and ensure you have java version 8 downloaded (java -version) then make sure you are in the correct directory and enter java -jar bioLQM-0.6.1.jar then enter java -jar bioLQM-0.6.1.jar -if bnet "C:\Users\blimy\Downloads\demo0.bnet" -r fixpoints > "C:\Users\blimy\Downloads\fixpointOutput.txt" but replace the bnet file path and the output file path to the your desired path (you can enter this code and stop after fixpoints to see the fixpoints in the terminal.) 

-Open the output file with the fixpoints, and save it in encoding **UTF-8**

-Run the reinConverterGUI.java code and select the AEON file from the GUI, then select the newly converted fixpoint file from the second GUI, and then select/ create an output rein file in the desired output location.

-Take the new output file and load it to the Reasoning Engine to see the results.
