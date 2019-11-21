package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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
public class ValidateMandatoryHeadersTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private List<String> testHeaders;
    private boolean expected;
    private static final HashMap<String, String> noCols = new HashMap<>();


    public ValidateMandatoryHeadersTests(boolean sublist, List<String> extraHeaders, boolean expected) {
        this.testHeaders = Stream.of(MetadataTableValidator.MandatoryHeaders.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        if (sublist) {
            this.testHeaders = this.testHeaders.subList(0, 2);
        }
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
                {false, Arrays.asList(), true},
                {true, Arrays.asList(), false},
                {false, Arrays.asList("extraCol", "anotherExtraCol"), true},
                {true, Arrays.asList("extraCol", "anotherExtraCol"), false}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateMandatoryHeaders(testHeaders);
        assertEquals(expected, mtv.getValid());
    }
}
