package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class FastaValidatorTest {

    @org.junit.Test
    public void validateFasta() {
        Result validateFastaResult = JUnitCore.runClasses(ValidateFastaTests.class);
        if (validateFastaResult.wasSuccessful()) {
            System.out.println("validateFasta tests passed");
        }
    }

    @org.junit.Test
    public void openFasta() {
        Result openFastaResult = JUnitCore.runClasses(OpenFastaTests.class);
        if (openFastaResult.wasSuccessful()) {
            System.out.println("openFasta tests passed");
        }
    }

    @org.junit.Test
    public void checkIdentifier() {
        Result checkIdentifierResult = JUnitCore.runClasses(CheckIdentifierTests.class);
        if (checkIdentifierResult.wasSuccessful()) {
            System.out.println("checkIdentifier tests passed");
        }
    }

    @org.junit.Test
    public void checkSequence() {
        Result checkSequenceTests = JUnitCore.runClasses(CheckSequenceTests.class);
        if (checkSequenceTests.wasSuccessful()) {
            System.out.println("checkSequence tests passed");
        }
    }


}
