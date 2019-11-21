package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateMetadataRecordTests {

    private static final String TESTRESOURCEDIR = "src\\test\\resources";
    MetadataRecordValidator vmr;
    ValidationResult emptyValidationResult;
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

    public ValidateMetadataRecordTests(String taxonomySystem, String taxonomyVersion, File fastaFile, File tabFile, Map<String, String> customFields, boolean expected) {
        emptyValidationResult = new ValidationResult(new File("manifest_validation_test.report"));
        vmr = new MetadataRecordValidator(emptyValidationResult, taxonomySystem, taxonomyVersion, fastaFile, tabFile, customFields);
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {}

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {"NCBI", "1", new File(TESTRESOURCEDIR + "\\FASTA\\valid.fasta.gz"), new File (TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), emptyMap, true},
                {"NCBI", "1", new File(TESTRESOURCEDIR + "\\FASTA\\valid.fasta.gz"), new File (TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), validCustomCols, true},
                {"NCBI", "1", new File(TESTRESOURCEDIR + "\\FASTA\\valid.fasta.gz"), new File (TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), tooLongCustomCols, false},
                {"", "1", new File(TESTRESOURCEDIR + "\\FASTA\\valid.fasta.gz"), new File (TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), emptyMap, false},
                {"NCBI", "", new File(TESTRESOURCEDIR + "\\FASTA\\valid.fasta.gz"), new File (TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), emptyMap, true},
                {"NCBI", "1", new File("not\\a\\valid\\path"), new File ( TESTRESOURCEDIR + "\\TSV\\valid.tsv.gz"), emptyMap, false},
                {"NCBI", "1", new File(TESTRESOURCEDIR + "\\FASTA\\valid.fasta.gz"), new File ("not\\a\\valid\\path"), emptyMap, false},
                {"", "", new File("not\\a\\valid\\path"), new File ("not\\a\\valid\\path"), emptyMap, false},
                {"", "", new File("not\\a\\valid\\path"), new File ("not\\a\\valid\\path"), validCustomCols, false},
                {"", "", new File("not\\a\\valid\\path"), new File ("not\\a\\valid\\path"), tooLongCustomCols, false},
                {"NCBI", "1", null, new File ("\\TSV\\valid.tsv.gz"), emptyMap, false},
        });
    }

    @org.junit.Test
    public void ValidateMetadataRecord() {
        vmr.validateMetadataRecord();
        assertEquals(expected, vmr.getValid());
    }
}
