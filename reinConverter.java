import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;



public class aeonReader {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Set<String> genes = new HashSet<>();
        List<String[]> relationships = new ArrayList<>();
        String[] geneList = new String[0];

        //File file = new File("C:\\Users\\blimy\\OneDrive\\Desktop\\modle3.txt");
        File aeonFile = new File("C:\\Users\\blimy\\OneDrive\\Desktop\\demo11.aeon");
        //"C:\Users\blimy\OneDrive\Desktop\model7.txt"
        //File file = new File ("C:\\Users\\blimy\\OneDrive\\Desktop\\model5.txt");
        File fixpointFile = new File( "C:\\Users\\blimy\\OneDrive\\Desktop\\fixpointOutput.txt");


        String newReinFilePath = "C:\\Users\\blimy\\OneDrive\\Desktop\\outputFile.rein";
        BufferedWriter reinWriter = new BufferedWriter(new FileWriter(newReinFilePath));




        Scanner sc = new Scanner(aeonFile);
        //scanner for aeon

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

        for (String gene : genes) {
            reinWriter.write(gene + " (0..17); ");
        }
        reinWriter.newLine();

        Scanner user= new Scanner(System.in);
        System.out.println("Would you like to make all interactions optional? (yes/no)");
        String answer= user.nextLine();

        if (answer.equals("yes") | answer.equals("Yes")){
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

        Scanner scfp=new Scanner(fixpointFile);

        // Reset the scanner to read the file again
        scfp = new Scanner(fixpointFile);
        //scanner for fixpoints
        //ArrayList <String> geneList = new ArrayList<>();
        if (scfp.hasNextLine()){
            String geneLine=scfp.nextLine();
            //System.out.println("read line "+geneLine);
            geneList = geneLine.split(" v_");
            geneList[0] = geneList[0].replace("v_", "");
            //System.out.println(Arrays.toString(geneList));
        }
        String path = "C:\\Users\\blimy\\OneDrive\\Desktop\\fixpointOutput.txt";
        long lineCount = Files.lines(Paths.get(path)).count();
        lineCount--;// get ride of the line which is always the genes

        reinWriter.newLine();

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

        while (scfp.hasNextLine()){
            String fixpoint=scfp.nextLine();
            System.out.println(fixpoint);
            //int lenth= geneList.length;
            //System.out.println("$a" + counter + "1");

            for (int j = 1; j <= 2; j++) {

                reinWriter.write("$a" + counter + j +" := \n {");
                reinWriter.newLine();

                for (int i = 0; i < geneList.length - 1; i++) {
                    reinWriter.write(geneList[i] + " = " + fixpoint.charAt(i) + " and ");
                    reinWriter.newLine();
                    //System.out.println(geneList[i] + " = " + fixpoint.charAt(i) + " and ");
                }
                reinWriter.write(geneList[geneList.length - 1] + " = " + fixpoint.charAt(geneList.length - 1) + "\n };");
                reinWriter.newLine();
            }
            counter++;


        }
        scfp.close();
        reinWriter.close();
    }
}
