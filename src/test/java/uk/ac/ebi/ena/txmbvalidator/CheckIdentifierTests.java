package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CheckIdentifierTests {

    private ValidationResult fastaIdentifierValidationResult;
    private String idLine;
    private FastaValidator fav;
    boolean expected;
    private ArrayList<String> expectedIdentifiers = new ArrayList<>();

    public CheckIdentifierTests(String idLine, boolean expected) {
        this.idLine = idLine;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        fastaIdentifierValidationResult = new ValidationResult();
        fav = new FastaValidator(new File("NOT_APPLICABLE"), expectedIdentifiers, emptyValidationResult);
        expectedIdentifiers.clear();
        expectedIdentifiers.add("ITS1DB00887249");
        expectedIdentifiers.add("ITS1DB00944432");
        expectedIdentifiers.add("ITS1DB01095025");
        expectedIdentifiers.add("ITS1DB01019240");
        expectedIdentifiers.add("ITS1DB00588026");
        expectedIdentifiers.add("ITS1DB00588027");
        expectedIdentifiers.add("ITS1DB00588024");
        expectedIdentifiers.add("ITS1DB00588025");
        expectedIdentifiers.add("ITS1DB00588022");
        expectedIdentifiers.add("ITS1DB00588023");
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {">ITS1DB00588023|175245|uncultured fungus|ITS1 located by HMM annotation, 142bp", true},
                {">ITS1DB00588023175245uncultured fungusITS1 located by HMM annotation, 142bp", false},
                {">ITS1DB00588023", true},
                {">ITS1DB00588023|", true},
                {">ITS1DB99999999|175245|uncultured fungus|ITS1 located by HMM annotation, 142bp", false}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        fav.checkIdentifier(idLine, expectedIdentifiers);
        assertEquals(expected, fav.getValid());
    }

}
