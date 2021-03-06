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
public class ValidateMetadataTableTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private SubmissionFile metadataTableFile;
    private boolean ncbiTax;
    private boolean customColumnsUsed;
    private boolean expected;
    private static final HashMap<String, String> noCols = new HashMap<>();
    private static final HashMap<String, String> customCols = new HashMap<String, String>() {{
        put("Annotation", "description");
        put("ITSoneDB URL", "description");
    }};

    public ValidateMetadataTableTests(File metadataTableFilename, boolean ncbiTax, boolean customColumnsUsed, boolean expected) {
        this.metadataTableFile = new SubmissionFile(TaxRefSetManifest.FileType.TAB, metadataTableFilename);
        this.ncbiTax = ncbiTax;
        this.customColumnsUsed = customColumnsUsed;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator(metadataTableFile, emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {new File(RESOURCETSVDIR + "valid.tsv.gz"), true, false, true},
                {new File(RESOURCETSVDIR + "empty_id_column.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "empty_row.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "empty_taxid_column.tsv.gz"), false, false, true},
                {new File(RESOURCETSVDIR + "missing_id_column.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "missing_identifier.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "missing_insdc_acc_and_range.tsv.gz"), true, false, true},
                {new File(RESOURCETSVDIR + "missing_insdc_acc.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "missing_insdc_range.tsv.gz"), true, false, true},
                {new File(RESOURCETSVDIR + "missing_lineage.tsv.gz"), true, false, true},
                {new File(RESOURCETSVDIR + "missing_row.tsv.gz"), true, false, true},
                {new File(RESOURCETSVDIR + "missing_tax_id.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "missing_taxon_name.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "valid_w_customs.tsv.gz"), true, true, true},
                {new File(RESOURCETSVDIR + "valid_not_compressed.tsv.gz"), true, false, false},
                {new File(RESOURCETSVDIR + "missing_column.tsv.gz"), false, false, false},
        });
    }

    @org.junit.Test
    public void validateMetadataTable() {
        if (customColumnsUsed) {
            mtv.setCustomColumns(customCols);
        }
        mtv.setNcbiTax(ncbiTax);
        mtv.validateMetadataTable();
        assertEquals(expected, mtv.getValid());
    }
}
