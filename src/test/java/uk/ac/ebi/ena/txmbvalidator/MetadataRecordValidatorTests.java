package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class MetadataRecordValidatorTests {

    @org.junit.Test
    public void validateMetadataRecord(){
        Result validateMetadataRecordResult = JUnitCore.runClasses(ValidateMetadataRecordTests.class);

    }

    @org.junit.Test
    public void validateTaxonomySystem(){
        Result validateTaxonomySystemResult = JUnitCore.runClasses(ValidateTaxonomySystemTests.class);
    }

    @org.junit.Test
    public void validateTaxonomySystemVersion() {
        Result validateTaxonomySystemVersionResult = JUnitCore.runClasses(ValidateTaxonomySystemVersionTests.class);
    }

    @org.junit.Test
    public void fileExists() {
        Result fileExistsResult = JUnitCore.runClasses(FileExistsTests.class);
    }

    @org.junit.Test
    public void validateCustomFieldNames() {
        Result validateCustomFieldNamesResult = JUnitCore.runClasses(ValidateCustomFieldNamesTests.class);
    }

}
