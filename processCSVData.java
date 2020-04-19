
/**
 * Write a description of exercise here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class exercise {
    
    //from FirstCSVExample
    // public void readFood() {
    	// FileResource fr = new FileResource();
    	// CSVParser parser = fr.getCSVParser();
    	// for (CSVRecord record : parser){
            // System.out.print(record.get("Name") + " ");
    	    // System.out.print(record.get("Favorite Color") + " ");
    	    // System.out.println(record.get("Favorite Food"));
    	// }
    // }
    
    
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String result = countryInfo(parser, "Nauru");
        System.out.println(" --- countryInfo ---");
        System.out.println(result);
        System.out.println("\n");
        
        fr = new FileResource();
        parser = fr.getCSVParser();
        System.out.println(" --- listExportersTwoProducts ---");
        listExportersTwoProducts(parser, "gold", "diamonds");
        System.out.println("\n");
        
        fr = new FileResource();
        parser = fr.getCSVParser();
        int count = numberOfExporters(parser, "sugar");
        System.out.println(" --- numberOfExporters --- ");
        System.out.println("number of exporters: " + count);
        System.out.println("\n");
        
        fr = new FileResource();
        parser = fr.getCSVParser();
        System.out.println(" --- bigExporters --- ");
        bigExporters(parser, "$999,999,999,999");
    }

    public String countryInfo(CSVParser parser, String country) {
        String result = "NOT FOUND";
    
        for (CSVRecord record : parser) {
            String ct = record.get("Country");
            if (ct.contains(country)) {                
                String export = record.get("Exports");
                String value = record.get("Value (dollars)");
                result =  ct + ": " + export + ": " + value;
            }
        }
        return result;
    }
    
    public void listExportersTwoProducts(CSVParser parser,
                                       String exportItem1,
                                       String exportItem2) {
        //FileResource fr = new FileResource();                                   
        //CSVParser parser = fr.getCSVParser();

        System.out.println("before for loop");
        for (CSVRecord record : parser) {
            //System.out.println("inside for loop");
            String exports = record.get("Exports");
            //System.out.println(exports);
            if (exports.contains(exportItem1) && 
                exports.contains(exportItem2)) {
                String country = record.get("Country");
                System.out.println(country);
            }
        }                        
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for (CSVRecord record : parser) {
            String export = record.get("Exports");
            if (export.contains(exportItem)) {
                count ++;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            String country = record.get("Country");
            String value = record.get("Value (dollars)");
            if (value.length() > amount.length()) {
                System.out.println(country + " " + value);
            }
        }
    }
}
