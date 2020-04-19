
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class Part1 {
    
    // ----- from part1 of StringsSecondAssignments ----- 
    public int findStopCodon(String dnaStr, 
                             int startIndex, 
                             String stopCodon) {
        int currIndex = dnaStr.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1) {
            int diff = currIndex - startIndex;
            if (diff % 3 == 0) {
                return currIndex;
            }
            else {
                currIndex = dnaStr.indexOf(stopCodon, currIndex + 1);
            }     
        }
        // indicate no stop codon found
        return -1;
    }
    
    public String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        // int temp = Math.min(taaIndex, tagIndex);
        // int minIndex = Math.min(temp, tgaIndex);
        int minIndex = 0;
        if (taaIndex == -1 || 
            (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;    
        }
        else {
            minIndex = taaIndex;
        }
        if (minIndex == -1 ||
            (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;    
        }
        if (minIndex == -1) {
            return "";
        }
        return dna.substring(startIndex, minIndex + 3);
    
    }
    
    public void testFindStopCodon() {
        //            01234567890123456789012345
        String dna = "xxxyyyzzzTAAxxxyyyzzzTAAxx";
        int dex = findStopCodon(dna, 0, "TAA");
        if (dex != 9) System.out.println("error on 9");
        dex = findStopCodon(dna, 9, "TAA");
        if (dex != 21) System.out.println("error on 21");
        dex = findStopCodon(dna, 1, "TAA");
        if (dex != -1) System.out.println("error on 26");
        dex = findStopCodon(dna, 0, "TAG");
        if (dex != -1) System.out.println("error on 26");
        System.out.println("tests finished");
    } 
    
    public void testFindGene() {
    
        String dna = "ATGCCCGGGAAATAACCC";
        String gene = findGene(dna, 0);
        if (! gene.equals("ATGCCCGGGAAATAA")) {
            System.out.println("error");
        }
        System.out.println("tests finished");
        
        // String dna = "TAATAA";
        // String gene = findGene(dna);
        // System.out.println(dna + " no ATG: " + gene);
        
        // dna = "ATGTGA";
        // gene = findGene(dna);
        // System.out.println(dna + " one valid stop codon: " + gene);
        
        // dna = "ATGTAATGA";
        // gene = findGene(dna);
        // System.out.println(dna + " multiple valid stop codons: " + gene);
        
        // dna = "AAAATGAAA";
        // gene = findGene(dna);
        // System.out.println(dna + " no valid stop codons: " + gene);
        
        // //         ^  ^  ^  ^
        // dna = "AAAATGAAAAATGA";
        // gene = findGene(dna);
        // System.out.println(dna + " ATG and valid stop codon but not multiple of 3: " + gene);
    }
    
    public void printAllGenes(String dna) {
        int startIndex = 0;
        while ( true ) {
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()) {
                break;
            }
            System.out.println(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + 
                         currentGene.length();
        }
    }
    
    public void testOn(String dna) {
        System.out.println("Testing printAllGenes on " + dna);
        printAllGenes(dna);
    }
    
    public void test() {
        testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        testOn("");
        testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    }
    // ----- from part1 of StringsSecondAssignments ----- 
    
    public StorageResource getAllGenes(String dna) {
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        while ( true ) {
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()) {
                break;
            }
            geneList.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + 
                         currentGene.length();
        }
        return geneList;
    }
    
    public float cgRatio(String dna) {
        int countC = countSomeChar('C', dna);
        int countG = countSomeChar('G', dna);
        float ans = (float) (countC + countG) / dna.length(); 
        
        //debug
        System.out.println("countC: " + countC);
        System.out.println("countG: " + countG);
        System.out.println("dna.length(): " + dna.length());
        System.out.println("ans: " + ans);
        
        return ans;
    }
    
    public int countCTG(String dna) {
        int startIndex = 0;
        int currIndex = 0;
        int count = 0;
        while ( true ) {
            if (currIndex == -1) {
                break;
            }
            else {
                currIndex = dna.indexOf("CTG", startIndex);
                if (currIndex != -1) {
                    count += 1;
                }
            }
            startIndex = currIndex + 3;
        }
        return count;
    }
    
    public void processGenes(StorageResource sr) {
        int count60 = 0;
        int count35 = 0;
        int maxLength = 0;
        int countGenes = 0;
        for (String s : sr.data()) {
            
            countGenes += 1;
            //debug
            //System.out.println("s: " + s);
            
            if (s.length() > 60) {
                //System.out.println("string longer than 60: " + s);
                count60 += 1;
            }
            if (cgRatio(s) > 0.35) {
                //System.out.println("cgRatio higher than 0.35: " + s);
                count35 += 1;
            }
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }
        System.out.println("the number of strings in sr that are longer than 60: " + count60);
        System.out.println("the number of strings in sr cgRatio higher than 0.35: " + count35);
        System.out.println("max length is: " + maxLength);
        System.out.println("number of genes is: " + countGenes);
        
    }
    
    public void testCountCTG() {
        String dna = "CTGCTGCTGCT";
        System.out.println(countCTG(dna));
    }
    
    public int countSomeChar(char someChar, String someString) {
        int count = 0;
        for (int i = 0;
             i < someString.length();
             i ++)
             if (someString.charAt(i) == someChar) {
                count ++;
            }
        return count;
    }
    
    public void testProcessGenes() {
        // StorageResource sr = new StorageResource();
        // sr.add("AAABBBCCCDDD");
        // sr.add("AAABBB");
        // sr.add("CCCGGGAB");
        // sr.add("CGAAABBBDDD");
        // sr.add("WHAT");
        // processGenes(sr);
        
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString();
        System.out.println("dna is: " + dna);
        StorageResource sr = getAllGenes(dna.toUpperCase());
        processGenes(sr);
    }
    
    public void week2LastQuiz() {
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        System.out.println("dna is: " + dna);
        StorageResource sr = getAllGenes(dna.toUpperCase());
        processGenes(sr);
        System.out.println("number of CTG is: " + countCTG(dna));
    }
    
    public void testOnGetAllGenes(String dna) {
        System.out.println("Testing getAllGenes on " + dna);
        StorageResource genes = getAllGenes(dna);
        for (String g : genes.data()) {
            System.out.println(g);
        }
    }
    
    public void testGetAllGenes() {
        testOn("ATGATCTAATTTATGCTGCAACGGTGAAGA");
        testOn("");
        testOn("ATGATCATAAGAAGATAATAGAGGGCCATGTAA");
    }

    public void testCgRatio() {
        String dna = "ATGCCATAG";
        float ratio = cgRatio(dna);
        System.out.println(dna + " cgRatio: " + ratio);
    }
}
