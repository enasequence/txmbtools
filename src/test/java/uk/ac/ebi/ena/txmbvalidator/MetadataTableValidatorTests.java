package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class MetadataTableValidatorTests {

    @org.junit.Test
    public void validateMetadataTable() {
        Result validateMetadataTableResult = JUnitCore.runClasses(ValidateMetadataTableTests.class);
        assert(validateMetadataTableResult.wasSuccessful());
    }

    @org.junit.Test
    public void openMetadataTable() {
        Result openMetadataTableResult = JUnitCore.runClasses(OpenMetadataTableTests.class);
        assert(openMetadataTableResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateGetHeaderList() {
        Result validateGetHeaderList = JUnitCore.runClasses(GetHeaderListTests.class);
        assert(validateGetHeaderList.wasSuccessful());
    }

    @org.junit.Test
    public void validateNumberOfColumns() {
        Result validateNumberOfColumnsResult = JUnitCore.runClasses(ValidateNumberOfColumnsTests.class);
        assert(validateNumberOfColumnsResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateMandatoryHeaders() {
        Result validateMandatoryHeadersResult = JUnitCore.runClasses(ValidateMandatoryHeadersTests.class);
        assert(validateMandatoryHeadersResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateCustomHeaders() {
        Result validateCustomHeadersResult = JUnitCore.runClasses(ValidateCustomHeadersTests.class);
        assert(validateCustomHeadersResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateRecordTests() {
        Result validateRecordResult = JUnitCore.runClasses(ValidateRecordTests.class);
        assert(validateRecordResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateIdentifier() {
        Result validateIdentifierResult = JUnitCore.runClasses(ValidateIdentifierTests.class);
        assert(validateIdentifierResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateInsdcSequenceAccession() {
        Result validateInsdcSequenceAccessionResult = JUnitCore.runClasses(ValidateInsdcSequenceAccessionTests.class);
        assert(validateInsdcSequenceAccessionResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateInsdcSequenceRange() {
        Result validateInsdcSequenceRangeResult = JUnitCore.runClasses(ValidateInsdcSequenceRangeTests.class);
        assert(validateInsdcSequenceRangeResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateLocalOrganismName() {
        Result validateLocalOrganismNameResult = JUnitCore.runClasses(ValidateLocalOrganismNameTests.class);
        assert(validateLocalOrganismNameResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateLocalLineage() {
        Result validateLocalLineageResult = JUnitCore.runClasses(ValidateLocalLineageTests.class);
        assert(validateLocalLineageResult.wasSuccessful());
    }

    @org.junit.Test
    public void validateNcbiTaxId() {
        Result validateNcbiTaxIdResult = JUnitCore.runClasses(ValidateNcbiTaxIdTests.class);
        assert(validateNcbiTaxIdResult.wasSuccessful());
    }
}
