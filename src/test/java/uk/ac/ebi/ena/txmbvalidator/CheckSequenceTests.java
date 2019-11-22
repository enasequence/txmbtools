package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CheckSequenceTests {

    private ValidationResult fastaSequenceValidationResult;
    private String sequence;
    private FastaValidator fav;
    boolean expected;

    public CheckSequenceTests(String sequence, boolean expected) {
        this.sequence = sequence;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ArrayList<String> empty_list = new ArrayList<String>();
        ValidationResult emptyValidationResult = new ValidationResult();
        fastaSequenceValidationResult = new ValidationResult();
        fav = new FastaValidator(new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File("NOTAPPLICABLE")), empty_list, emptyValidationResult);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
                {"gtcgaaccctgcgatagcagacgacccggtaacatgtaaacacatcgggagagcatcaaggggcatcgatcccttgaccgcgaaccctaggttggtgtccgcttagattctaaggggacgccgattgacataaccatccgggcgcggcatgcgccaaggaacttaaaatcgaattgtatgttcgcttcccgttatcgggcagcagcgtcattccaaaaaacaacg", true},
                {"abcdefghijklmnopqrstuvwxyz!£$%^&*()", false},
                {"actg\\nactg", false},
                {"actg actg", false},
                {"", false},
        });
    }

    @org.junit.Test
    public void checkSequence() {
        fav.checkSequence(sequence);
        assertEquals(expected, fav.getValid());
    }
}
