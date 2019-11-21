package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static junit.framework.TestCase.assertTrue;

public class FastaValidatorTest {

    @org.junit.Test
    public void validateFasta() {
        Result validateFastaResult = JUnitCore.runClasses(ValidateFastaTests.class);
        assertTrue(validateFastaResult.wasSuccessful());
    }

    @org.junit.Test
    public void openFasta() {
        Result openFastaResult = JUnitCore.runClasses(OpenFastaTests.class);
        assertTrue(openFastaResult.wasSuccessful());
    }

    @org.junit.Test
    public void checkIdentifier() {
        Result checkIdentifierResult = JUnitCore.runClasses(CheckIdentifierTests.class);
        assertTrue(checkIdentifierResult.wasSuccessful());
    }

    @org.junit.Test
    public void checkSequence() {
        Result checkSequenceTests = JUnitCore.runClasses(CheckSequenceTests.class);
        assertTrue(checkSequenceTests.wasSuccessful());
    }


}
