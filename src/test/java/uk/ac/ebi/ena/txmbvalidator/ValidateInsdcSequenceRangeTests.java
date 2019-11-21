package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateInsdcSequenceRangeTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String insdcSequenceRange;
    private boolean accessionPresent;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public ValidateInsdcSequenceRangeTests(String insdcSequenceRange, boolean accessionPresent, boolean expected) {
        this.insdcSequenceRange = insdcSequenceRange;
        this.accessionPresent = accessionPresent;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator(new File("NOT_APPLICABLE"), emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {"21..3523", true, true},
                {"21..3523", false, false},
                {"nonsenseString", false, false},
                {"21,.3523", true, false},
                {"", true, true},
                {"", false, true},
                {null, false, true},
                {"<21..>239", true, true},
                {"21..>239", true, true},
                {"<21..239", true, true},
                {"21", true, false}

        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateInsdcSequenceRange(insdcSequenceRange, accessionPresent);
        assertEquals(expected, mtv.getValid());
    }
}
