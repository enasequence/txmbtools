package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateMetadataTableTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private String metadataTableFilename;
    private boolean ncbiTax;
    private boolean expected;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public ValidateMetadataTableTests(String metadataTableFilename, boolean ncbiTax, boolean expected) {
        this.metadataTableFilename = metadataTableFilename;
        this.ncbiTax = ncbiTax;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator(metadataTableFilename, emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {(RESOURCETSVDIR + "valid.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "empty_id_column.tsv.gz"), true, false},
                {(RESOURCETSVDIR + "empty_row.tsv.gz"), true, false},
                {(RESOURCETSVDIR + "empty_taxid_column.tsv.gz"), false, true},
                {(RESOURCETSVDIR + "missing_id_column.tsv.gz"), true, false},
                {(RESOURCETSVDIR + "missing_identifier.tsv.gz"), true, false},
                {(RESOURCETSVDIR + "missing_insdc_acc_and_range.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "missing_insdc_acc.tsv.gz"), true, false},
                {(RESOURCETSVDIR + "missing_insdc_range.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "missing_lineage.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "missing_row.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "missing_tax_id.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "missing_taxon_name.tsv.gz"), true, false},
                {(RESOURCETSVDIR + "valid_w_customs.tsv.gz"), true, true},
                {(RESOURCETSVDIR + "valid_not_compressed.tsv.gz"), true, false},
        });
    }

    @org.junit.Test
    public void validateMetadataTable() {
        mtv.setNcbiTax(ncbiTax);
        mtv.validateMetadataTable();
        assertEquals(expected, mtv.getValid());
    }
}
