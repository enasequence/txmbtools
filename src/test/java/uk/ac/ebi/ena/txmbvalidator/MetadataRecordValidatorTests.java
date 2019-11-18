package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class MetadataRecordValidatorTests {

    @org.junit.Test
    public void validateMetadataRecord(){
        Result validateMetadataRecordResult = JUnitCore.runClasses(ValidateMetadataRecordTests.class); // TODO
        assert(validateMetadataRecordResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateTaxonomySystem(){
        Result validateTaxonomySystemResult = JUnitCore.runClasses(ValidateTaxonomySystemTests.class); // TODO: RUN
    }

    @org.junit.Test
    public void validateTaxonomySystemVersion() {
        Result validateTaxonomySystemVersionResult = JUnitCore.runClasses(ValidateTaxonomySystemVersionTests.class); // TODO: RUN
    }

    @org.junit.Test
    public void existsFastaFile() {
        Result validateTaxonomySystemVersionResult = JUnitCore.runClasses(ExistsFastaFileTests.class); // TODO
    }

    @org.junit.Test
    public void existsMetadataTableFile() {
        Result existsMetadataTableFileResult = JUnitCore.runClasses(ExistsMetadataTableFileTests.class); // TODO
    }

    @org.junit.Test
    public void validateCustomFieldNames() {
        Result validateCustomFieldNamesResult = JUnitCore.runClasses(ValidateCustomFieldNamesTests.class); // TODO
    }

}
