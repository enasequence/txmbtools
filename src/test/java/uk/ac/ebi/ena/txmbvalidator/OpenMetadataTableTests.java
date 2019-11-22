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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OpenMetadataTableTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private File metadataTableFile;
    private boolean expected;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public OpenMetadataTableTests(File metadataTableFilename, boolean expected) {
        this.metadataTableFile = metadataTableFilename;
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
                {new File(RESOURCETSVDIR + "valid.tsv.gz"), true},
                {new File(RESOURCETSVDIR + "valid_not_compressed.gz"), false}
        });
    }

    @org.junit.Test
    public void openMetadataTable() {
        mtv.openMetadataTable(metadataTableFile);
        assertEquals(mtv.getValid(), expected);
    }
}
