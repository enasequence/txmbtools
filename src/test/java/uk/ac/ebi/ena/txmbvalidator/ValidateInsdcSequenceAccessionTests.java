package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ValidateInsdcSequenceAccessionTests {


    private MetadataTableValidator mtv;
    private boolean expected;
    private String insdcSequenceAccession;

    public void validateInsdcSequenceAccessionTests(String insdcSequenceAccession, boolean expected) {
        this.insdcSequenceAccession = insdcSequenceAccession;
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
                {"LR590077", true},
                {"not_an_accession", false},
                {"58LR0077", false},
                {1, false},
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
