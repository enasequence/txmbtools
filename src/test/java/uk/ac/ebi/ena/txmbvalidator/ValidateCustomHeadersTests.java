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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateCustomHeadersTests {

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


    public ValidateCustomHeadersTests(List<String> additionalHeads, HashMap<String, String> customColHash, boolean expected) {
        this.testHeaders = Stream.of(MetadataTableValidator.MandatoryHeaders.values())
                .map(Enum::name)
                .collect(Collectors.toList());;
        this.testHeaders.addAll(additionalHeads);
        this.testCustomCols = customColHash;
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
                {Arrays.asList("colName1", "colName2"), twoCols, true},
                {Arrays.asList("colName1", "colName2", "colName3"), threeCols, true},
                {Arrays.asList("colName1", "colName2"), threeCols, false},
                {Arrays.asList("colName1", "colName2", "colName3"), twoCols, false},
        });
    }

    @org.junit.Test
    public void validateCustomHeaders() {
        mtv.validateCustomHeaders(testHeaders, testCustomCols);
        assertEquals(expected, mtv.getValid());
    }
}
