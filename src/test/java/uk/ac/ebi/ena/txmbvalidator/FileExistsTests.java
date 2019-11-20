package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FileExistsTests {

    private static final String TESTRESOURCEDIR = "C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\test\\resources";
    private MetadataRecordValidator mrv;
    private ValidationResult emptyValidationResult;
    private File nonFile;
    private File testFile;
    boolean expected;
    private static final HashMap<String, String> emptyMap = new HashMap<String, String>();

    public FileExistsTests(File testFile, boolean expected) {
        this.testFile = testFile;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        mrv = new MetadataRecordValidator(emptyValidationResult, "void", "void", nonFile, nonFile, emptyMap);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {(TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), true},
                {(TESTRESOURCEDIR + "made_up_file"), false},
                {null, false},
        });
    }

    @org.junit.Test
    public void fileExists() {
        mrv.fileExists(testFile, "tst");
        assertEquals(expected, mrv.getValid());
    }
}
