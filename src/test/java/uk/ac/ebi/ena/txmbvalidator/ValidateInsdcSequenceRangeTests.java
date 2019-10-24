package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ValidateInsdcSequenceRangeTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String insdcSequenceRange;
    private boolean accessionPresent;

    public void validateInsdcSequenceRangeTests(String insdcSequenceRange, boolean accessionPresent, boolean expected) {
        this.insdcSequenceRange = insdcSequenceRange;
        this.accessionPresent = accessionPresent;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator("NOT_APPLICABLE", emptyValidationResult, false);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {"21..3523", true, true},
                {"21..3523", false, false},
                {"nonsenseString", false, false},
                {"21,.3523", true, false},
                {"", true, false},
                {"", false, true},
                {null, false, true},
                {"<21..>239", true, false},
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateInsdcSequenceRange(insdcSequenceRange, accessionPresent);
        assertEquals(expected, mtv.getValid());
    }
}
