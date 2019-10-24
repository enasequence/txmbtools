package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ValidateLocalOrganismNameTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String localOrganismName;

    public void validateLocalOrganismNameTests(String localOrganismName, boolean expected) {
        this.localOrganismName = localOrganismName;
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
                {"Homo sapiens", true},
                {"Stramenopiles sp. MAST-7 TOSAG23-7", true},
                {"Schmlug gablub", true},
                {"", false},
                {" ", false},
                {null, false}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateLocalOrganismName(localOrganismName);
        assertEquals(expected, mtv.getValid());
    }
}
