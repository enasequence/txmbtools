package uk.ac.ebi.ena.txmbvalidator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class MetadataTableValidator {

    private final String[] MANDATORY_HEADERS = {"local_identifier",
            "insdc_sequence_accession",
            "insdc_sequence_range",
            "local_organism_name",
            "local_lineage",
            "ncbi_tax_id"};
    private final String CHARACTER_REGEX = "^[A-Za-z0-9_]+$";
    private String[] local_identifiers;
    private String metadataTableFilename;
    private File metadataTableLogFile;
    private ValidationResult metadataTableValidationResult;
    private boolean ncbiTax;

    public MetadataTableValidator(String metadataTableFilename, ValidationResult manifestValidationResult, boolean ncbiTax) {
        this.metadataTableFilename = metadataTableFilename;
        this.metadataTableLogFile = new File(metadataTableFilename + ".report");
        this.metadataTableValidationResult = new ValidationResult(manifestValidationResult, metadataTableLogFile);
        this.ncbiTax = ncbiTax;
    }

    public MetadataTableValidator() {

    }

    public ValidationResult validateMetadataTable() {

    }

    public Iterable<CSVRecord> openMetadataTable() {

    }

    public String[] validateMandatoryHeaders() {

    }

    public void validateCustomHeaders() {

    }

    public void validateIdentifier(String sequenceIdentifier) {

    }

    public void validateInsdcSequenceAccession(String insdcSequenceAccession) {

    }

    public void validateInsdcSequenceRange(String insdcSequenceRange, boolean accessionPresent) {

    }

    public void validateLocalOrganismName(String localOrganismName) {

    }

    public void validateLocalLineage(String localLineage) {

    }

    public void validateNcbiTaxId(String ncbiTaxId, boolean ncbiTax) {

    }

    public String[] getLocal_identifiers() {
        return local_identifiers;
    }

    public boolean getValid() {
        return metadataTableValidationResult.isValid();
    }

//    public static void trial() {
//
//       try {
//
//           BufferedReader bf = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream("C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\test\\resources\\TSV\\valid.tsv.gz"))));
//           Reader in = new FileReader("C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\test\\resources\\TSV\\valid.tsv.gz");
//           Iterable<CSVRecord> records = CSVFormat.TDF.withFirstRecordAsHeader().parse(bf);
//
//           for (CSVRecord record : records) {
//               System.out.println(record.get("local_identifier"));
//           }
//       } catch ( Exception ex ) {
//           System.out.println(ex);
//       }
//    }
//
}
