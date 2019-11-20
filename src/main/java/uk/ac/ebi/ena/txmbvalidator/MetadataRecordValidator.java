package uk.ac.ebi.ena.txmbvalidator;

import uk.ac.ebi.ena.webin.cli.validator.message.ValidationMessage;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationOrigin;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.lang.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetadataRecordValidator {

    private ValidationResult manifestValidationResult;
    private String taxonomySystem;
    private String taxonomySystemVersion;
    private File fastaFile;
    private File metadataTableFile;
    private Map<String,String> customFields;
    private Pattern characterRegex = Pattern.compile("^[A-Za-z0-9_.\\- ]+$");
    private ArrayList<String> ncbiTaxSynonyms = new ArrayList<String>(){{
        add("ncbi");
        add("ncbi taxonomy");
        add("ncbi tax");
        add("ncbi_taxonomy");
        add("ncbi_tax");
        add("ncbi-taxonomy");
        add("ncbi-tax");
        add("ncbi - taxonomy");
        add("ncbi - tax");
        add("ncbitax");
        add("ncbitaxonomy");
        add("taxonomy - ncbi");
        add("taxonomy-ncbi");
    }};
    private boolean ncbiTax;

    public MetadataRecordValidator(ValidationResult manifestValidationResult, String taxonomySystem, String taxonomySystemVersion, File fastaFile, File tabFile, Map<String, String> customFields) {
        this.manifestValidationResult = manifestValidationResult;
        this.taxonomySystem = taxonomySystem;
        this.taxonomySystemVersion = taxonomySystemVersion;
        this.fastaFile = fastaFile;
        this.metadataTableFile = tabFile;
        this.customFields = customFields;
    }

    public void validateMetadataRecord() {

    }

    public void validateTaxonomySystem(String taxonomySystem) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Manifest File", "Taxonomic System");

        if (taxonomySystem == null || taxonomySystem.isEmpty()) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Must specify the database/authority from which taxon names and lineages are drawn");
            validationMessage.appendOrigin(validationOrigin);
            manifestValidationResult.add(validationMessage);
            return;
        }

        if (taxonomySystem.length() > 50) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Value for taxonomic system is too long (limit = 50)");
            validationMessage.appendOrigin(validationOrigin);
            manifestValidationResult.add(validationMessage);
        }

        Matcher taxSystemMatch = characterRegex.matcher(taxonomySystem);
        boolean taxSystemValid = taxSystemMatch.matches();

        if (!taxSystemValid) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Taxonomic system contains unacceptable characters (allowed pattern = " + characterRegex.pattern());
            validationMessage.appendOrigin(validationOrigin);
            manifestValidationResult.add(validationMessage);
        }

        for (String synonym : ncbiTaxSynonyms) {
            if (synonym.equalsIgnoreCase(taxonomySystem)) {
                this.ncbiTax = true;
                return;
            }
        }

        this.ncbiTax = false;
    }

    public void validateTaxonomySystemVersion(String taxonomySystemVersion) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Manifest File", "Taxonomic System Version");

        if (taxonomySystemVersion == null || taxonomySystemVersion.isEmpty()) {
            return;
        }

        if (taxonomySystemVersion.length() > 50) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Value for taxonomic system version is too long (limit = 50)");
            validationMessage.appendOrigin(validationOrigin);
            manifestValidationResult.add(validationMessage);
        }

        Matcher taxSystemVersionMatch = characterRegex.matcher(taxonomySystemVersion);
        boolean taxSystemValid = taxSystemVersionMatch.matches();

        if (!taxSystemValid) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Taxonomic system version contains unacceptable characters (allowed pattern = " + characterRegex.pattern());
            validationMessage.appendOrigin(validationOrigin);
            manifestValidationResult.add(validationMessage);
        }
    }

    public void fileExists(File fileToCheck, String fileType) {
        ValidationOrigin validationOrigin = new ValidationOrigin("Manifest File", fileType);

        if (!fileToCheck.exists()) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, ("Could not find " + fileType + " file '" + fileToCheck.getName() + "'"));
            validationMessage.appendOrigin(validationOrigin);
            manifestValidationResult.add(validationMessage);
        }
    }

    public void validateCustomFieldNames(Map<String,String> customFields) {
        for (String fieldName : customFields.keySet()) {
            ValidationOrigin validationOrigin = new ValidationOrigin("Manifest File", "Custom Field");

            if (fieldName.length() > 50) {
                ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Custom field name '" + fieldName + "' is too long (limit = 50)");
                validationMessage.appendOrigin(validationOrigin);
                manifestValidationResult.add(validationMessage);
            }

            Matcher customFieldMatch = characterRegex.matcher(fieldName);
            boolean customFieldNameValid = customFieldMatch.matches();

            if (!customFieldNameValid) {
                ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Custom field name '" + fieldName + "' contains unacceptable characters (allowed pattern = " + characterRegex.pattern());
                validationMessage.appendOrigin(validationOrigin);
                manifestValidationResult.add(validationMessage);
            }
        }
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    public boolean isNcbiTax() {
        return ncbiTax;
    }

    public void setNcbiTax(boolean ncbiTax) {
        this.ncbiTax = ncbiTax;
    }

    public boolean getValid() {
        return manifestValidationResult.isValid();
    }

}

// TODO: check if file exists: https://howtodoinjava.com/java/io/how-to-check-if-file-exists-in-java/
