package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static junit.framework.TestCase.assertTrue;

public class MetadataRecordValidatorTests {

    @org.junit.Test
    public void validateMetadataRecord(){
        Result validateMetadataRecordResult = JUnitCore.runClasses(ValidateMetadataRecordTests.class);
        assertTrue(validateMetadataRecordResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateTaxonomySystem(){
        Result validateTaxonomySystemResult = JUnitCore.runClasses(ValidateTaxonomySystemTests.class);
        assertTrue(validateTaxonomySystemResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateTaxonomySystemVersion() {
        Result validateTaxonomySystemVersionResult = JUnitCore.runClasses(ValidateTaxonomySystemVersionTests.class);
        assertTrue(validateTaxonomySystemVersionResult.wasSuccessful());
    }

    @org.junit.Test
    public void fileExists() {
        Result fileExistsResult = JUnitCore.runClasses(FileExistsTests.class);
        assertTrue(fileExistsResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateCustomFieldNames() {
        Result validateCustomFieldNamesResult = JUnitCore.runClasses(ValidateCustomFieldNamesTests.class);
        assertTrue(validateCustomFieldNamesResult.wasSuccessful());
    }

}
