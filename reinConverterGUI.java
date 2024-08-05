import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;

public class reinConverterGUI {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Set<String> genes = new HashSet<>();
        List<String[]> relationships = new ArrayList<>();
        String[] geneList = new String[0];

        // Create a file chooser for the AEON file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        // Prompt user to select the AEON file
        System.out.println("Please select the AEON file:");
        int fileChosen = fileChooser.showOpenDialog(null);
        if (fileChosen == JFileChooser.APPROVE_OPTION) {
            File aeonFile = fileChooser.getSelectedFile();

            // Prompt user to select the FIXPOINT file
            System.out.println("Please select the FIXPOINT file:");
            fileChosen = fileChooser.showOpenDialog(null);
            if (fileChosen == JFileChooser.APPROVE_OPTION) {
                File fixpointFile = fileChooser.getSelectedFile();

                // Prompt user to select the output REIN file location
                System.out.println("Please select the output REIN file location:");
                fileChooser.setSelectedFile(new File("outputFile.rein"));
                fileChosen = fileChooser.showSaveDialog(null);
                if (fileChosen == JFileChooser.APPROVE_OPTION) {
                    File reinFile = fileChooser.getSelectedFile();
                    String newReinFilePath = reinFile.getAbsolutePath();
                    BufferedWriter reinWriter = new BufferedWriter(new FileWriter(newReinFilePath));

                    Scanner sc = new Scanner(aeonFile);
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine();
                        if (line.startsWith("$")) {
                            break;
                        }
                        String modifiedLine = line.replace("v_", "");
                        String[] parts = modifiedLine.split(" ");
                        if (parts.length >= 3) {
                            String one = parts[0];
                            String two = parts[1];
                            String three = parts[2];
                            genes.add(one);
                            genes.add(three);
                            relationships.add(new String[]{one, two, three});
                        }
                    }
                    sc.close();

                    // Write the genes into the REIN file
                    for (String gene : genes) {
                        reinWriter.write(gene + " (0..17); ");
                    }
                    reinWriter.newLine();

                    // give the user option to make all interaction optional 
                    Scanner user = new Scanner(System.in);
                    System.out.println("Would you like to make all interactions optional? (yes/no)");
                    String answer = user.nextLine();

                    // Write relationships to REIN file
                    if (answer.equalsIgnoreCase("yes")) {
                        for (String[] relationship : relationships) {
                            String one = relationship[0];
                            String two = relationship[1];
                            String three = relationship[2];
                            if (two.equals("->")) {
                                reinWriter.write(one + " " + three + " positive optional;");
                            } else if (two.equals("-|")) {
                                reinWriter.write(one + " " + three + " negative optional;");
                            } else if (two.equals("->?")) {
                                reinWriter.write(one + " " + three + " positive optional;");
                            } else if (two.equals("-|?")) {
                                reinWriter.write(one + " " + three + " negative optional;");
                            } else {
                                reinWriter.write(one + " " + three + " positive optional;");
                                reinWriter.newLine();
                                reinWriter.write(one + " " + three + " negative optional;");
                            }
                            reinWriter.newLine();
                        }
                    } else {
                        for (String[] relationship : relationships) {
                            String one = relationship[0];
                            String two = relationship[1];
                            String three = relationship[2];
                            if (two.equals("->")) {
                                reinWriter.write(one + " " + three + " positive;");
                            } else if (two.equals("-|")) {
                                reinWriter.write(one + " " + three + " negative;");
                            } else if (two.equals("->?")) {
                                reinWriter.write(one + " " + three + " positive optional;");
                            } else if (two.equals("-|?")) {
                                reinWriter.write(one + " " + three + " negative optional;");
                            } else {
                                reinWriter.write(one + " " + three + " positive;");
                                reinWriter.newLine();
                                reinWriter.write(one + " " + three + " negative;");
                            }
                            reinWriter.newLine();
                        }
                    }

                    // Process FIXPOINT file
                    Scanner scfp = new Scanner(fixpointFile);
                    if (scfp.hasNextLine()) {
                        String geneLine = scfp.nextLine();
                        geneList = geneLine.split(" v_");
                        geneList[0] = geneList[0].replace("v_", "");
                    }
                    String path = fixpointFile.getAbsolutePath();
                    long lineCount = Files.lines(Paths.get(path)).count();
                    lineCount--; // Remove the line which is always the genes

                    reinWriter.newLine();

                    // Write experiments into REIN file
                    for (int i = 1; i <= lineCount; i++) {
                        reinWriter.newLine();
                        reinWriter.write("//Experiment " + i);
                        reinWriter.newLine();
                        reinWriter.write("#Experiment" + i + "[19] |= $a" + i + "1;");
                        reinWriter.newLine();
                        reinWriter.write("#Experiment" + i + "[20] |= $a" + i + "2;");
                        reinWriter.newLine();
                    }
                    reinWriter.newLine();

                    int counter = 1;
                    while (scfp.hasNextLine()) {
                        String fixpoint = scfp.nextLine();
                        for (int j = 1; j <= 2; j++) {
                            reinWriter.write("$a" + counter + j + " := \n {");
                            reinWriter.newLine();
                            for (int i = 0; i < geneList.length - 1; i++) {
                                reinWriter.write(geneList[i] + " = " + fixpoint.charAt(i) + " and ");
                                reinWriter.newLine();
                            }
                            reinWriter.write(geneList[geneList.length - 1] + " = " + fixpoint.charAt(geneList.length - 1) + "\n };");
                            reinWriter.newLine();
                        }
                        counter++;
                    }
                    scfp.close();
                    reinWriter.close();
                } else {
                    System.out.println("Output file selection was cancelled.");
                }
            } else {
                System.out.println("FIXPOINT file selection was cancelled.");
            }
        } else {
            System.out.println("AEON file selection was cancelled.");
        }
    }
}
