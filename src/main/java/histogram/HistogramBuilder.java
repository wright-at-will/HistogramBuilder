package histogram;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HistogramBuilder {

    private Scanner scanIn;
    private HashMap<String, Integer> map;
    private ArrayList<String> histogram;
    private FileWriter writer;
    private int maxLength=0;

    public static void main(String[] args) {
        System.out.println("Usage: [-i input_File | -o output_File]");
        try {
            String inputFile = "input.txt";
            String outputFile = "output.txt";
            for (int i=0;i<args.length-1;i++) {
                if (args[i].equals("-i")){
                    inputFile = args[i + 1];
                    System.out.println("Reading input from: "+inputFile);
                }else if(args[i].equals("-o")) {
                    outputFile = args[i + 1];
                    System.out.println("Writing output to: "+outputFile);
                }
            }
            HistogramBuilder hg = new HistogramBuilder(inputFile,outputFile);
            //Step1: Read Input File
            hg.readFile();
            //Step2: Generate Histogram
            hg.histogram();
            //Step3: Generate output file
            hg.generateFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public HistogramBuilder(String inputFile,String outputFile) throws IOException {
        scanIn = new Scanner(new File(inputFile));
        scanIn.useDelimiter("\\W+");
        map = new HashMap<>();
        histogram = new ArrayList<>();
        writer = new FileWriter(outputFile);
    }

    private void readFile() {
        String word;
        while(scanIn.hasNext()){
            word = scanIn.next().toLowerCase();
            //Handle reading a word
            Integer i = map.get(word);
            map.put(word, (i == null) ? 1 : i + 1);
            if(maxLength<word.length())
                maxLength=word.length();
        }
        System.out.println();
    }
    private void histogram(){
        for (String s : map.keySet())
            histogram.add(String.format(" %"+maxLength+"s | %s (%d)\n",s , "=".repeat(map.get(s)),map.get(s)));
        histogram.sort(Comparator.comparing(String::length).reversed());
    }


    private void generateFile() throws IOException{
        for(String s:histogram){
            writer.write(s);
        }
        writer.close();
    }
}
