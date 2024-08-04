import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class bnetToBiolqm  {
    

    public static void main(String[] args) throws FileNotFoundException, IOException{
        // we take in a downloaded bnet file- change to the correct path
        File file = new File("C:\\Users\\blimy\\OneDrive\\Desktop\\demo11.bnet");
        
        // this is where the new file will output to -change to the correct path
        String newBnetFilePath = "C:\\Users\\blimy\\Downloads\\outputFile2.bnet";
        
        BufferedWriter bnetWriter = new BufferedWriter(new FileWriter(newBnetFilePath));
        Scanner sc = new Scanner(file);

        //Takes in the first sentence and make a space after the comma
        String line=sc.nextLine();
        line=line.replace(",",", ");
        bnetWriter.write(line);
        bnetWriter.newLine();

        System.out.println(line);
        
        //create two sets one for the targets and one for the genes
        HashSet<String> geneTargets = new HashSet<>();
        HashSet<String> geneFactors= new HashSet<>();

        //goes through gene factors/ logical statements and adds genes in the factor column to a hashset and genes in the 
        // target column to a hashset
        while (sc.hasNextLine()){
            String lines=sc.nextLine();
            bnetWriter.write(lines);
            bnetWriter.newLine();
            String splitLines[]=lines.split(",");
            geneTargets.add(splitLines[0].trim());
            
            Pattern genePattern = Pattern.compile("v_[a-zA-Z0-9_]+");
            Matcher geneMatch = genePattern.matcher(splitLines[1]);

            while (geneMatch.find()){
                geneFactors.add(geneMatch.group());
            }

        }

        //Here we check if the target do not have contain the factors 
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
