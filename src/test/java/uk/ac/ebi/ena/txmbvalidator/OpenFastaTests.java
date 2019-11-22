package uk.ac.ebi.ena.txmbvalidator;

import static org.junit.Assert.*;

import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
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
        fav = new FastaValidator(new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File("NOTAPPLICABLE")), empty_list, emptyValidationResult);

    }

    @org.junit.Test
    public void openFile_valid() {
        fav.openFasta(new File(RESOURCEFASTADIR + "valid.fasta.gz"));
        assertEquals(true, fav.getValid());
    }

    @org.junit.Test
    public void openFile_notFound() {
        fav.openFasta(new File(RESOURCEFASTADIR + "not_a_file.fasta.gz"));
        assertEquals(false, fav.getValid());
    }

    @org.junit.Test
    public void openFile_notGzipped() {
        fav.openFasta(new File(RESOURCEFASTADIR + "not_compressed.fasta"));
        assertEquals(false, fav.getValid());
    }
}
