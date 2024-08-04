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

        //Here we check if the target set is missing any of the genes from the factor side, if so we add them to the end and set it 
        //equal to itself 
        for (String gene : geneFactors) {
            if (!geneTargets.contains(gene)) {
                bnetWriter.write(gene + ", " + gene);
                bnetWriter.newLine();
            }
        }


        //uncomment if you want to check if the genes and factors converted correctly or if you want to see the output on your screen
        //System.out.println("targets" +geneTargets);
        //System.out.println("factor" +geneFactors);

        bnetWriter.close();

    }


}
