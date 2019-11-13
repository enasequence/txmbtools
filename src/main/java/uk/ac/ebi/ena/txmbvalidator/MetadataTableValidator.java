package uk.ac.ebi.ena.txmbvalidator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationMessage;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationOrigin;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class MetadataTableValidator {

    private final String[] MANDATORY_HEADERS = {"local_identifier",
            "insdc_sequence_accession",
            "insdc_sequence_range",
            "local_organism_name",
            "local_lineage",
            "ncbi_tax_id"};
    private final String CHARACTER_REGEX = "^[A-Za-z0-9_]+$";
    private ValidationResult metadataTableValidationResult;
    private String metadataTableFilename;
    private File metadataTableLogFile;
    private ArrayList<String> localIdentifiers = new ArrayList<String>();
    private boolean ncbiTax;
    private int linecount;

    public enum MandatoryHeaders {
        local_identifier,
        insdc_sequence_accession,
        insdc_sequence_range,
        local_organism_name,
        local_lineage,
        ncbi_tax_id
    }


    public MetadataTableValidator(String metadataTableFilename, ValidationResult manifestValidationResult, boolean ncbiTax) {
        this.metadataTableFilename = metadataTableFilename;
        this.metadataTableLogFile = new File(metadataTableFilename + ".report");
        this.metadataTableValidationResult = new ValidationResult(manifestValidationResult, metadataTableLogFile);
        this.ncbiTax = ncbiTax;
    }

    public MetadataTableValidator() {

    }

    public ValidationResult validateMetadataTable() {
        CSVParser metadataTableParser = openMetadataTable(this.metadataTableFilename);
//        getHeaderList();
//        validateNumberOfColumns();
//        validateMandatoryHeaders();
//        validateCustomHeaders();
//      TODO and now loop through CSV records
        {linecount++;}


        return this.metadataTableValidationResult; // TODO: Probably change this, result is already accessible through MetadataTableValidator object
    }

    public CSVParser openMetadataTable(String metadataTableFilename) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);
        BufferedReader metadataTableReader;
        CSVParser metadataTableParser;

        try {
            metadataTableReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(metadataTableFilename))));
            metadataTableParser = CSVParser.parse(metadataTableReader, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (FileNotFoundException ex) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence Metadata Table '" + this.metadataTableFilename + "' could not be found");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
            return null;
        } catch (IOException ex) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence Metadata Table '" + this.metadataTableFilename + "' could not be read");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
            return null;
        }

        return metadataTableParser;
    }

    public List<String> getHeaderList(CSVParser parser) {
        return null;
    }

    public void validateNumberOfColumns(List<String> headers, HashMap<String, String> customColumns) {

    }

    public void validateMandatoryHeaders(List<String> headers) {

    }

    public void validateCustomHeaders(List<String> headers, HashMap<String, String> customColumns) {

    }

    public void validateRecord(CSVRecord record) {

        validateIdentifier(record.get("local_identifier"));
        this.localIdentifiers.add(record.get("local_identifier"));

        boolean insdcAccessionPresent = validateInsdcSequenceAccession(record.get("insdc_sequence_accession"));

        validateInsdcSequenceRange(record.get("insdc_sequence_range"), insdcAccessionPresent);

        validateLocalOrganismName(record.get("local_organism_name"));

        validateLocalLineage(record.get("local_lineage"));

        validateNcbiTaxId(record.get("ncbi_tax_id"), ncbiTax);
        // Capture identifier
    }

    public void validateIdentifier(String sequenceIdentifier) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);

        if (sequenceIdentifier == null || sequenceIdentifier.isEmpty()) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "All records must include a sequence identifier");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
            return;
        }

        if (sequenceIdentifier.length() > 50) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence identifier is too long (>50)");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }

        Pattern identiferPattern = Pattern.compile("^[A-Za-z0-9_.-]+$");
        Matcher identifierMatch = identiferPattern.matcher(sequenceIdentifier);
        boolean identifierValid = identifierMatch.matches();

        if (!identifierValid) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence identifier contains invalid characters (Allowed: [A-Za-z0-9_.-] )");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }

    }

    public boolean validateInsdcSequenceAccession(String insdcSequenceAccession) {
        boolean accessionPresent;

        if (insdcSequenceAccession == null || insdcSequenceAccession.isEmpty()) {
            return accessionPresent = false;
        } else {
            accessionPresent = true;
        }

        Pattern insdcAccessionPattern = Pattern.compile("^[A-Z]{1,6}[0-9]{5,8}(\\.[0-9])?$");
        Matcher accessionMatch = insdcAccessionPattern.matcher(insdcSequenceAccession);
        boolean accessionValid = accessionMatch.matches();

        if (accessionValid) {
            return accessionPresent;
        } else {
            ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "INSDC accession does not appear to be valid; must match regex " + insdcAccessionPattern.pattern());
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
            return accessionPresent;
        }

    }

    public void validateInsdcSequenceRange(String insdcSequenceRange, boolean accessionPresent) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);

        if (insdcSequenceRange == null || insdcSequenceRange.isEmpty()) {
            return;
        } else if (!insdcSequenceRange.isEmpty() && !accessionPresent) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "An INSDC sequence range was given for a record without an INSDC accession");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }

        Pattern sequenceRangePattern = Pattern.compile("^<?\\d+\\.\\.>?\\d+$");
        Matcher sequenceRangeMatch = sequenceRangePattern.matcher(insdcSequenceRange);
        boolean validRange = sequenceRangeMatch.matches();

        if (validRange) {
            return;
        } else {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence range invalid, must match regex '^<?\\d+\\.\\.>?\\d+$'");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }
    }

    public void validateLocalOrganismName(String localOrganismName) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);

        if (localOrganismName == null || localOrganismName.isEmpty()) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Organism name must be provided for every record");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
            return;
        }

        if (localOrganismName.length() < 5) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Organism name appears too short to be valid");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }
    }

    public void validateLocalLineage(String localLineage) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);

        if (localLineage == null || localLineage.isEmpty()) {
            return;
        }

        if (localLineage.length() < 10) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.INFO, "Lineage appears too short to be valid");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }
    }

    public void validateNcbiTaxId(String ncbiTaxId, boolean ncbiTax) {
        int taxIdAsInt;
        ValidationOrigin validationOrigin = new ValidationOrigin("Sequence Metadata Table", linecount);

        if (ncbiTaxId == null || ncbiTaxId.isEmpty()) {
            if (!ncbiTax) {
                return;
            } else {
                ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Using NCBI Taxonomy database but no Tax ID given");
                validationMessage.appendOrigin(validationOrigin);
                metadataTableValidationResult.add(validationMessage);
            }
        } else {
            if (!ncbiTax) {
                ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "NCBI Tax ID given but not specified to be using NCBI Taxonomy");
                validationMessage.appendOrigin(validationOrigin);
                metadataTableValidationResult.add(validationMessage);
            }
        }

        try {
            taxIdAsInt = Integer.parseInt(ncbiTaxId);
        } catch (NumberFormatException ex) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "NCBI Tax IDs must be valid integers: '" + ncbiTaxId + "' is not acceptable");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
            return;
        }

        try {
            assert(taxIdAsInt > 0 && taxIdAsInt < 100_000_000);
        } catch (AssertionError ex) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Value '" + ncbiTaxId + "' is outside valid range for tax IDs");
            validationMessage.appendOrigin(validationOrigin);
            metadataTableValidationResult.add(validationMessage);
        }
    }

    public ArrayList<String> getLocalIdentifiers() {
        return localIdentifiers;
    }

    public boolean getValid() {
        return metadataTableValidationResult.isValid();
    }

    public void setNcbiTax(boolean ncbiTax) {
        this.ncbiTax = ncbiTax;
    }
}
