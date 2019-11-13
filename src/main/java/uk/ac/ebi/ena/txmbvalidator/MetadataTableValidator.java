package uk.ac.ebi.ena.txmbvalidator;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationMessage;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationOrigin;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.*;
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
    private String[] localIdentifiers;
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
        return null;
//      remember to increment linecount
    }

    public CSVParser openMetadataTable(String metadataTableFilename) {
        return null;
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

    }

    public void validateIdentifier(String sequenceIdentifier) {

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

    public String[] getLocalIdentifiers() {
        return localIdentifiers;
    }

    public boolean getValid() {
        return metadataTableValidationResult.isValid();
    }

}
