import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bnetToBiolqm  {
    

    public static void main(String[] args) throws FileNotFoundException, IOException{
        File file = new File("C:\\Users\\blimy\\OneDrive\\Desktop\\demo11.bnet");
        String newBnetFilePath = "C:\\Users\\blimy\\Downloads\\outputFile2.bnet";
        BufferedWriter bnetWriter = new BufferedWriter(new FileWriter(newBnetFilePath));
        Scanner sc = new Scanner(file);

        //take in the first sentence and make a space between
        String line=sc.nextLine();
        line=line.replace(",",", ");
        bnetWriter.write(line);
        bnetWriter.newLine();

        System.out.println(line);

        HashSet<String> geneTargets = new HashSet<>();
        HashSet<String> geneFactors= new HashSet<>();
        //HashSet<String> extraFactors = new HashSet<>();
        while (sc.hasNextLine()){
            String lines=sc.nextLine();
            bnetWriter.write(lines);
            bnetWriter.newLine();
            String splitLines[]=lines.split(",");
            geneTargets.add(splitLines[0].trim());
            //now we go through the second part of the split and if something start with v_ add it
            Pattern genePattern = Pattern.compile("v_[a-zA-Z0-9_]+");
            Matcher geneMatch = genePattern.matcher(splitLines[1]);

            while (geneMatch.find()){
                geneFactors.add(geneMatch.group());
            }

        }

        for (String gene : geneFactors) {
            if (!geneTargets.contains(gene)) {
                bnetWriter.write(gene + ", " + gene);
                bnetWriter.newLine();
                //extraFactors.add(gene);
            }
        }

        System.out.println("targets" +geneTargets);
        System.out.println("factor" +geneFactors);

        bnetWriter.close();
        //System.out.println("missed targ"+ extraFactors);

        // add all the defined targets to a set, and then go through each stnence and look at the factor if
        //their not in the set then we can add either to the next line or at the end equal to itself?

    }


}
