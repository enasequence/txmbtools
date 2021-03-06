package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFiles;
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
    private static final List<String> twoColKeys = Arrays.asList("colName1", "colName2");
    private static final HashMap<String, String> threeCols = new HashMap<String, String>() {{
        put("colName1", "colDesc1");
        put("colName2", "colDesc2");
        put("colName3", "colDesc3");
    }};
    private static final List<String> threeColKeys = Arrays.asList("colName1", "colName2", "colName3");


    public ValidateNumberOfColumnsTests(List<String> additionalHeaders, HashMap<String, String> testCustomCols, boolean expected) {
        this.testHeaders = Stream.of(MetadataTableValidator.MandatoryHeaders.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        this.testHeaders.addAll(additionalHeaders);
        this.testCustomCols = testCustomCols;
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
                {Arrays.asList(), noCols, true},
                {twoColKeys, twoCols, true},
                {threeColKeys, threeCols, true},
                {Arrays.asList(), twoCols, false},
                {twoColKeys, noCols, false}
        });
    }

    @org.junit.Test
    public void validateNumberOfColumns() {
        mtv.validateNumberOfColumns(testHeaders, testCustomCols);
        assertEquals(expected, mtv.getValid());
    }
}
