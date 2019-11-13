package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateInsdcSequenceAccessionTests {


    private MetadataTableValidator mtv;
    private boolean expected;
    private String insdcSequenceAccession;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public ValidateInsdcSequenceAccessionTests(String insdcSequenceAccession, boolean expected) {
        this.insdcSequenceAccession = insdcSequenceAccession;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator("NOT_APPLICABLE", emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {"LR590077", true},
                {"not_an_accession", false},
                {"58LR0077", false},
                {"CABFPT010000044.11", false},
                {"", true},
                {null, true}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateInsdcSequenceAccession(insdcSequenceAccession);
        assertEquals(expected, mtv.getValid());
    }
}
