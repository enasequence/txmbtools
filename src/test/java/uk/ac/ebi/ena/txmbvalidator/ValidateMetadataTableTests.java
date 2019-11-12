package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateMetadataTableTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private String metadataTableFilename;
    private boolean expected;

    public void ValidateMetadataTableTests(String metadataTableFilename, boolean expected) {
        this.metadataTableFilename = metadataTableFilename;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator(metadataTableFilename, emptyValidationResult, false);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {(RESOURCETSVDIR + "valid.tsv.gz"), true},
                {(RESOURCETSVDIR + "empty_id_column.tsv.gz"), false},
                {(RESOURCETSVDIR + "empty_row.tsv.gz"), false},
                {(RESOURCETSVDIR + "empty_taxid_column.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_id_column.tsv.gz"), false},
                {(RESOURCETSVDIR + "missing_identifier.tsv.gz"), false},
                {(RESOURCETSVDIR + "missing_insdc_acc_and_range.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_insdc_acc.tsv.gz"), false},
                {(RESOURCETSVDIR + "missing_insdc_range.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_lineage.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_row.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_tax_id.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_taxon_name.tsv.gz"), false},
                {(RESOURCETSVDIR + "valid_w_customs.tsv.gz"), true},
        });
    }

    @org.junit.Test
    public void validateMetadataTable() {
        mtv.validateMetadataTable();
        assertEquals(expected, mtv.getValid());
    }
}
