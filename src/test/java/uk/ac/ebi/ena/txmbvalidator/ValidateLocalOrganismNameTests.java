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
public class ValidateLocalOrganismNameTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String localOrganismName;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public ValidateLocalOrganismNameTests(String localOrganismName, boolean expected) {
        this.localOrganismName = localOrganismName;
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
