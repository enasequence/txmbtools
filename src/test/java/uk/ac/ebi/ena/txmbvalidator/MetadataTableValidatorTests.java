package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class MetadataTableValidatorTests {

    @org.junit.Test
    public void validateMetadataTable() {
        Result validateMetadataTableResult = JUnitCore.runClasses(ValidateMetadataTableTests.class); // TODO
    }

    @org.junit.Test
    public void openMetadataTable() {
        Result openMetadataTableResult = JUnitCore.runClasses(OpenMetadataTableTests.class); // TODO
    }

    @org.junit.Test
    public void validategetHeaderList() {
        Result validategetHeaderList = JUnitCore.runClasses(GetHeaderListTests.class); // TODO
    }

    @org.junit.Test
    public void validateNumberOfColumns() {
        Result validateNumberOfColumnsResult = JUnitCore.runClasses(ValidateNumberOfColumnsTests.class); // TODO
    }

    @org.junit.Test
    public void validateMandatoryHeaders() {
        Result validateMandatoryHeadersResult = JUnitCore.runClasses(ValidateMandatoryHeadersTests.class); // TODO
    }

    @org.junit.Test
    public void validateCustomHeaders() {
        Result validateCustomHeadersResult = JUnitCore.runClasses(ValidateCustomHeadersTests.class); // TODO
    }

    @org.junit.Test
    public void validateRecordTests() {
        Result validateRecordResult = JUnitCore.runClasses(ValidateRecordTests.class); // TODO
    }

    @org.junit.Test
    public void validateIdentifier() {
        Result validateIdentifierResult = JUnitCore.runClasses(ValidateIdentifierTests.class);
    }

    @org.junit.Test
    public void validateInsdcSequenceAccession() {
        Result validateInsdcSequenceAccessionResult = JUnitCore.runClasses(ValidateInsdcSequenceAccessionTests.class);
    }

    @org.junit.Test
    public void validateInsdcSequenceRange() {
        Result validateInsdcSequenceRangeResult = JUnitCore.runClasses(ValidateInsdcSequenceRangeTests.class);
    }

    @org.junit.Test
    public void validateLocalOrganismName() {
        Result validateLocalOrganismNameResult = JUnitCore.runClasses(ValidateLocalOrganismNameTests.class);
    }

    @org.junit.Test
    public void validateLocalLineage() {
        Result validateLocalLineageResult = JUnitCore.runClasses(ValidateLocalLineageTests.class);
    }

    @org.junit.Test
    public void validateNcbiTaxId() {
        Result validateNcbiTaxIdResult = JUnitCore.runClasses(ValidateNcbiTaxIdTests.class);
    }
}
