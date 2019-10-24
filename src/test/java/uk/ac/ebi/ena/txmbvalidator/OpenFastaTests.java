package uk.ac.ebi.ena.txmbvalidator;

import static org.junit.Assert.*;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.ArrayList;

public class OpenFastaTests {

    private static final String RESOURCEFASTADIR = "src\\test\\resources\\FASTA\\";
    private ValidationResult openFileValidationResult;
    private FastaValidator fav;

    @org.junit.Before
    public void setup() {
        ArrayList<String> empty_list = new ArrayList<String>();
        ValidationResult emptyValidationResult = new ValidationResult();
        openFileValidationResult = new ValidationResult();
        fav = new FastaValidator("NOT_APPLICABLE", empty_list, emptyValidationResult);

    }

    @org.junit.Test
    public void openFile_valid() {
        fav.openFasta((RESOURCEFASTADIR + "valid.fasta.gz"), openFileValidationResult);
        assertEquals(true, openFileValidationResult.isValid());
    }

    @org.junit.Test
    public void openFile_notFound() {
        fav.openFasta((RESOURCEFASTADIR + "not_a_file.fasta.gz"), openFileValidationResult);
        assertEquals(false, openFileValidationResult.isValid());
    }

    @org.junit.Test
    public void openFile_notGzipped() {
        fav.openFasta((RESOURCEFASTADIR + "not_compressed.fasta.gz"), openFileValidationResult);
        assertEquals(false, openFileValidationResult.isValid());
    }
}
