package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateCustomFieldNamesTests {
    private MetadataRecordValidator mrv;
    private ValidationResult emptyValidationResult;
    private File nonFile;
    private Map<String, String> customFields;
    boolean expected;
    private static final HashMap<String, String> emptyMap = new HashMap<String, String>();
    private static final HashMap<String, String> validCustomCols = new HashMap<String, String>() {{
        put("Annotation", "Source of annotation");
        put("ITSoneDB URL", "URL within ITSoneDB");
    }};
    private static final HashMap<String, String> tooLongCustomCols = new HashMap<String, String>() {{
        put("Annotation lotsofextracharacterstomakethistoolongandthereforeinvalid", "Source of annotation");
        put("ITSoneDB URL", "URL within ITSoneDB");
    }};
    private static final HashMap<String, String> badCharsCustomCols = new HashMap<String, String>() {{
        put("Annotation!!!", "$$Source of annotation");
        put("ITSoneDB URL@@", "##URL within ITSoneDB");
    }};


    public ValidateCustomFieldNamesTests(Map<String, String> customFields, boolean expected) {
        this.customFields = customFields;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        mrv = new MetadataRecordValidator(emptyValidationResult, "void", "void", nonFile, nonFile, emptyMap);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {validCustomCols, true},
                {tooLongCustomCols, false},
                {badCharsCustomCols, false}
        });
    }

    @org.junit.Test
    public void validateCustomFieldNames() {
        mrv.validateCustomFieldNames(customFields);
        assertEquals(expected, mrv.getValid());
    }
}