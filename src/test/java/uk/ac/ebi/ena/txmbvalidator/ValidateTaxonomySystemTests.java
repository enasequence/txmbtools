package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationMessage;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateTaxonomySystemTests {

    private MetadataRecordValidator mrv;
    private ValidationResult emptyValidationResult;
    private File nonFile;
    boolean expectedResult;
    boolean expectedNcbi;
    private static final HashMap<String, String> emptyMap = new HashMap<String, String>();

    String taxonomySystem;

    public ValidateTaxonomySystemTests(String taxonomySystem, boolean expectedResult, boolean expectedNcbi) {
        this.taxonomySystem = taxonomySystem;
        this.expectedResult = expectedResult;
        this.expectedNcbi = expectedNcbi;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult(new File("manifest_validation_test.report"));
        mrv = new MetadataRecordValidator(emptyValidationResult, "void", "void", nonFile, nonFile, emptyMap);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
//                {"NCBI", true, true},
//                {"ncbi", true, true},
                {"ncbi taxonomy", true, true},
//                {"ncbiTax", true, true},
//                {"nCbItAx", true, true},
                {"A taxonomy database", true, false},
//                {"tax_db_77", true, false},
//                {"reallylongnameofatleast50charactersisthisenoughofthemyet", false, false},
//                {"!\\\"£$%^&*()", false, false},
//                {"", false, false},
//                {null, false, false}
        });
    }

    @org.junit.Test
    public void ValidateTaxonomySystem() {
        mrv.validateTaxonomySystem(taxonomySystem);
        assertEquals(expectedResult, mrv.getValid());
        assertEquals(expectedNcbi, mrv.isNcbiTax());
    }
}
