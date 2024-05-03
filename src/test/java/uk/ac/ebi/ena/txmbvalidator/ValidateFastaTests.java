package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFiles;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ValidateFastaTests {

    private static final String RESOURCEFASTADIR = "src\\test\\resources\\FASTA\\";
    private static ValidationResult fastaValidationResult;
    private static ArrayList<String> correctIdentifiers =
            new ArrayList<>(Arrays.asList("ITS1DB00887249",
                "ITS1DB00944432",
                "ITS1DB01095025",
                "ITS1DB01019240",
                "ITS1DB00588026",
                "ITS1DB00588027",
                "ITS1DB00588024",
                "ITS1DB00588025",
                "ITS1DB00588022",
                "ITS1DB00588023"));
    private static ArrayList<String> missingIdentifier =
            new ArrayList<>(Arrays.asList("ITS1DB00887249",
                    "ITS1DB00944432",
                    "ITS1DB01095025",
                    "ITS1DB01019240",
                    "ITS1DB00588026",
                    "ITS1DB00588027",
                    "ITS1DB00588024",
                    "ITS1DB00588025",
                    "ITS1DB00588022"));
    private static ArrayList<String> expectedIdentifiers;
    private SubmissionFile fasta;
    private boolean expected;


    public ValidateFastaTests(String fasta, ArrayList<String> expectedIdentifiers, boolean expected) {
        this.fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File(fasta));
        this.expectedIdentifiers = expectedIdentifiers;
        this.fastaValidationResult = new ValidationResult();
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        fastaValidationResult = new ValidationResult();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][] {
            {(RESOURCEFASTADIR +"valid.fasta.gz"), correctIdentifiers, true},
            {(RESOURCEFASTADIR +"valid.fasta.gz"), missingIdentifier, false},
            {(RESOURCEFASTADIR +"two_seqs.fasta.gz"), correctIdentifiers, false},
            {(RESOURCEFASTADIR +"two_ids.fasta.gz"), correctIdentifiers, false},
            {(RESOURCEFASTADIR +"missing_entry.fasta.gz"), correctIdentifiers, false},
            {(RESOURCEFASTADIR +"empty_line_seq.fasta.gz"), correctIdentifiers, false},
            {(RESOURCEFASTADIR +"empty_line_id.fasta.gz"), correctIdentifiers, false},
            {(RESOURCEFASTADIR +"empty_id.fasta.gz"), correctIdentifiers, false},
            {(RESOURCEFASTADIR +"not_compressed.fasta"), correctIdentifiers, false},
        });
    }

    /**
     * For some reason, this test fails when 'gradlew clean test' is run from Intellij's gradle panel but passes when
     * right-clicked and run from context menu.
     */
    @org.junit.Test
    public void validateFasta() {
        FastaValidator fav = new FastaValidator(fasta, expectedIdentifiers, fastaValidationResult);
        fav.validateFasta();
        assertEquals(expected, fastaValidationResult.isValid());
    }
}
