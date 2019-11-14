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
public class ValidateMandatoryHeadersTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private List<String> testHeaders;
    private boolean expected;
    private static final HashMap<String, String> noCols = new HashMap<>();

    private static List<String> correctHeaders = Stream.of(MetadataTableValidator.MandatoryHeaders.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    private static List<String> missingHeader = correctHeaders.subList(0, 2);


    public ValidateMandatoryHeadersTests(List<String> testHeaders, List<String> extraHeaders, boolean expected) {
        this.testHeaders = testHeaders;
        this.testHeaders.addAll(extraHeaders);
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator("NOT_APPLICABLE", emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {correctHeaders, Arrays.asList(), true},
                {missingHeader, Arrays.asList(), false},
                {correctHeaders, Arrays.asList("extraCol", "anotherExtraCol"), true},
                {missingHeader, Arrays.asList("extraCol", "anotherExtraCol"), false}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateMandatoryHeaders(testHeaders);
        assertEquals(expected, mtv.getValid());
    }
}
