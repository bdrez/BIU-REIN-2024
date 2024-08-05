import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bnetToBiolqmGUI {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        // Prompt user to select the input BNET file
        System.out.println("Please select the BNET file:");
        int bnetFile = fileChooser.showOpenDialog(null);
        if (bnetFile == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
            String inputFilePath = inputFile.getAbsolutePath();

            // Prompt user to select the output BNET file location
            System.out.println("Please select the output BNET file location:");
            fileChooser.setSelectedFile(new File("outputFile.bnet"));
            bnetFile = fileChooser.showSaveDialog(null);
            if (bnetFile == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                String newBnetFilePath = outputFile.getAbsolutePath();
                BufferedWriter bnetWriter = new BufferedWriter(new FileWriter(newBnetFilePath));

                Scanner sc = new Scanner(new File(inputFilePath));

                // Take in the first line and ensure proper spacing after commas
                String line = sc.nextLine();
                line = line.replace(",", ", ");
                bnetWriter.write(line);
                bnetWriter.newLine();

                System.out.println(line);

                HashSet<String> geneTargets = new HashSet<>();
                HashSet<String> geneFactors = new HashSet<>();

                while (sc.hasNextLine()) {
                    String lines = sc.nextLine();
                    bnetWriter.write(lines);
                    bnetWriter.newLine();
                    String[] splitLines = lines.split(",");
                    geneTargets.add(splitLines[0].trim());

                    // Check for factors starting with v_ and add them
                    Pattern genePattern = Pattern.compile("v_[a-zA-Z0-9_]+");
                    Matcher geneMatch = genePattern.matcher(splitLines[1]);

                    while (geneMatch.find()) {
                        geneFactors.add(geneMatch.group());
                    }
                }

                // Add missing gene definitions at the end
                for (String gene : geneFactors) {
                    if (!geneTargets.contains(gene)) {
                        bnetWriter.write(gene + ", " + gene);
                        bnetWriter.newLine();
                    }
                }

                System.out.println("Targets: " + geneTargets);
                System.out.println("Factors: " + geneFactors);

                bnetWriter.close();
            } else {
                System.out.println("Output file selection was cancelled.");
            }
        } else {
            System.out.println("Input file selection was cancelled.");
        }
    }
}
