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
public class ValidateTaxonomySystemVersionTests {

    private MetadataRecordValidator mrv;
    private ValidationResult emptyValidationResult;
    private File nonFile;
    boolean expected;
    private static final HashMap<String, String> emptyMap = new HashMap<String, String>();

    String taxonomySystemVersion;

    public ValidateTaxonomySystemVersionTests(String taxonomySystemVersion, boolean expected) {
        this.taxonomySystemVersion = taxonomySystemVersion;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        emptyValidationResult = new ValidationResult(new File("manifest_validation_test.report"));
        mrv = new MetadataRecordValidator(emptyValidationResult, "void", "void", nonFile, nonFile, emptyMap);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {"1", true},
                {"v2.1.0", true},
                {"", true},
                {null, true},
                {"reallylongnameofatleast50charactersisthisenoughofthemyet", false},
                {"!\"£$%^&*()", false}
        });
    }

    @org.junit.Test
    public void ValidateTaxonomySystemVersion() {
        mrv.validateTaxonomySystemVersion(taxonomySystemVersion);
        assertEquals(expected, mrv.getValid());
    }
}
