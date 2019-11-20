package uk.ac.ebi.ena.txmbvalidator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ebi.ena.webin.cli.validator.manifest.GenomeManifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.Manifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.ReadsManifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class Main {

    public enum MandatoryHeaders {
        local_identifier,
        insdc_sequence_accession,
        insdc_sequence_range,
        local_organism_name,
        local_lineage,
        ncbi_tax_id
    }
    static public HashMap<String, String> customCols = new HashMap<String, String>();


    public static void main(String[] args) {
        customCols.put("one", "1");
        customCols.put("two", "2");


        ReadsManifest ma;
        TaxRefSetManifest mb = new TaxRefSetManifest();

        File temp = new File("C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\test\\resources\\TSV\\empty_id_column.tsv.gz");
        boolean exists = temp.exists();
        System.out.println("Existence of the file is: " + exists);

        String empty = null;
        System.out.println(empty.length());
    }




    public static List<String> csvHandleTrial() {

       try {

           BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\test\\resources\\TSV\\valid.tsv.gz"))));
           CSVParser parser = CSVParser.parse(br, CSVFormat.TDF.withFirstRecordAsHeader());
           List<String> headers = parser.getHeaderNames();
//           for (String header : headers) {
//               System.out.println(header);
//           }
           return headers;

//           Iterable<CSVRecord> records = CSVFormat.TDF.withFirstRecordAsHeader().parse(br);
//
//           System.out.println(records.get(0));
//
//           for (CSVRecord record : records) {
//               System.out.println(record.get("local_identifier"));
//           }
       } catch ( Exception ex ) {
           System.out.println(ex);
       }

       return null;
    }

}
