package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateIdentifierTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String localIdentifier;

    public void ValidateIdentifierTests(String localIdentifier, boolean expected) {
        this.localIdentifier = localIdentifier;
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
                {"anyValidString", true},
                {"", false},
                {null, false},
                {"thisNameIsUnacceptablyLongAndWillResultInAnErrorMessageByTheWayTheCharacterLimitIsFifty", false},
                {"nameWithUnacceptableCharacters!$%£*()", false}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateIdentifier(localIdentifier);
        assertEquals(expected, localIdentifier);
    }
}
