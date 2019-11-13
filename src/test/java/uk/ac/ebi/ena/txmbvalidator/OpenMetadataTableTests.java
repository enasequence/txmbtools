package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OpenMetadataTableTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private String metadataTableFilename;
    private boolean expected;

    public OpenMetadataTableTests(String metadataTableFilename, boolean expected) {
        this.metadataTableFilename = metadataTableFilename;
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
                {(RESOURCETSVDIR + "valid.tsv.gz"), true},
                {(RESOURCETSVDIR + "valid_not_compressed.gz"), false}
        });
    }

    @org.junit.Test
    public void openMetadataTable() {
        mtv.openMetadataTable(metadataTableFilename);
        assertEquals(mtv.getValid(), expected);
    }
}
