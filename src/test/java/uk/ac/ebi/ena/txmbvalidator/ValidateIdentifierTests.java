package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateIdentifierTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String localIdentifier;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public ValidateIdentifierTests(String localIdentifier, boolean expected) {
        this.localIdentifier = localIdentifier;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator(new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File("NOTAPPLICABLE")), emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {"anyValidString", true},
                {"ITS1DB00887249", true},
                {"ITS1DB-00887249", true},
                {"", false},
                {null, false},
                {"thisNameIsUnacceptablyLongAndWillResultInAnErrorMessageByTheWayTheCharacterLimitIsFifty", false},
                {"nameWithUnacceptableCharacters!$%£*()", false}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateIdentifier(localIdentifier);
        assertEquals(expected, mtv.getValid());
    }
}
