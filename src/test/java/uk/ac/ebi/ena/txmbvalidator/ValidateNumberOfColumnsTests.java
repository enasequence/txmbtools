package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateNumberOfColumnsTests {

    private MetadataTableValidator mtv;
    private List<String> testHeaders;
    private HashMap<String, String> testCustomCols;
    private boolean expected;

    private static final HashMap<String, String> noCols = new HashMap<>();
    private static final HashMap<String, String> twoCols = new HashMap<String, String>() {{
        put("colName1", "colDesc1");
        put("colName2", "colDesc2");
    }};
    private static final HashMap<String, String> threeCols = new HashMap<String, String>() {{
        put("colName1", "colDesc1");
        put("colName2", "colDesc2");
        put("colName3", "colDesc3");
    }};

    private static List<String> correctHeaders = Stream.of(MetadataTableValidator.MandatoryHeaders.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private static List<String> missingHeader = correctHeaders.subList(0, 2);

    public ValidateNumberOfColumnsTests(List<String> testHeaders, List<String> additionalHeaders, HashMap<String, String> testCustomCols, boolean expected) {
        this.testHeaders = testHeaders;
        this.testHeaders.addAll(additionalHeaders);
        this.testCustomCols = testCustomCols;
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
                {correctHeaders, Arrays.asList(), noCols, true},
                {correctHeaders, twoCols.keySet(), twoCols, true},
                {correctHeaders, Arrays.asList(), twoCols, false},
                {correctHeaders, twoCols.keySet(), noCols, false}
        });
    }

    @org.junit.Test
    public void validateNumberOfColumns() {
        mtv.validateNumberOfColumns(testHeaders, testCustomCols);
        assertEquals(mtv.getValid(), expected);
    }
}
