package uk.ac.ebi.ena.txmbvalidator;

import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetadataRecordValidator {

    private ValidationResult manifestValidationResult;
    private String taxonomySystem;
    private String taxonomySystemVersion;
    private String fastaFileName;
    private String metadataTableFileName;
    private Map<String,String> customFields;
    private boolean ncbiTax;

    public MetadataRecordValidator(ValidationResult manifestValidationResult, String taxonomySystem, String taxonomySystemVersion, String fastaFileName, String tabFileName, Map<String, String> customFields) {
        this.manifestValidationResult = manifestValidationResult;
        this.taxonomySystem = taxonomySystem;
        this.taxonomySystemVersion = taxonomySystemVersion;
        this.fastaFileName = fastaFileName;
        this.metadataTableFileName = tabFileName;
        this.customFields = customFields;
    }

    public void validateMetadataRecord() {

    }

    public void validateTaxonomySystem(String taxonomySystem) {

    }

    public void validateTaxonomySystemVersion(String taxonomySystemVersion) {

    }

    public void existsFastaFile() {

    }

    public void existsMetadataTableFile() {

    }

    public void validateCustomFieldNames(Map<String,String> customFields) {

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
