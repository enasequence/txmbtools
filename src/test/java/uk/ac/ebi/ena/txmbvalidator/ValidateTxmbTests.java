package uk.ac.ebi.ena.txmbvalidator;

import org.junit.Test;

import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFiles;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


public class ValidateTxmbTests {

    private final String RESOURCETXTDIR = "src\\test\\resources\\Manifests\\";
    private final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private final String RESOURCEFASTADIR = "src\\test\\resources\\FASTA\\";
    private TxmbValidator txv;

    @Test
    public void testValidManifest() {
        TaxRefSetManifest validManifest = new TaxRefSetManifest();

        validManifest.setName("testValidManifest");
        validManifest.setDescription("Description");
        validManifest.setTaxonomySystem("NCBI");
        validManifest.setTaxonomySystemVersion("");

        SubmissionFiles validFiles = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        validFiles.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        validFiles.add(tab);
        validManifest.setFiles(validFiles);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(validManifest);
        assertTrue(validationResult);
    }

    @Test
    public void testValidManifestWithCustoms() {
        TaxRefSetManifest validManifestWithCustoms = new TaxRefSetManifest();

        validManifestWithCustoms.setName("testValidManifestWithCustoms");
        validManifestWithCustoms.setDescription("Description");
        validManifestWithCustoms.setTaxonomySystem("NCBI");
        validManifestWithCustoms.setTaxonomySystemVersion("");

        SubmissionFiles validFiles = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        validFiles.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        validFiles.add(tab);
        validManifestWithCustoms.setFiles(validFiles);

        Map<String, String> customFields = new HashMap<String, String>();
        customFields.put("Custom1", "Desc1");
        customFields.put("Custom2", "Desc2");

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(validManifestWithCustoms);
        assertTrue(validationResult);
    }

    @Test
    public void testManifestInvalidFasta() {
        TaxRefSetManifest manifestInvalidFasta = new TaxRefSetManifest();

        manifestInvalidFasta.setName("testManifestInvalidFasta");
        manifestInvalidFasta.setDescription("Description");
        manifestInvalidFasta.setTaxonomySystem("NCBI");
        manifestInvalidFasta.setTaxonomySystemVersion("");

        SubmissionFiles validFiles = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"missing_entry.fasta.gz")));
        validFiles.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        validFiles.add(tab);
        manifestInvalidFasta.setFiles(validFiles);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(manifestInvalidFasta);
        assertFalse(validationResult);
    }

    @Test
    public void testManifestInvalidTab() {
        TaxRefSetManifest manifestInvalidTab = new TaxRefSetManifest();

        manifestInvalidTab.setName("testManifestInvalidTab");
        manifestInvalidTab.setDescription("Description");
        manifestInvalidTab.setTaxonomySystem("NCBI");
        manifestInvalidTab.setTaxonomySystemVersion("");

        SubmissionFiles validFiles = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"empty_row.fasta.gz")));
        validFiles.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        validFiles.add(tab);
        manifestInvalidTab.setFiles(validFiles);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(manifestInvalidTab);
        assertFalse(validationResult);
    }

    @Test
    public void testManifestInavlidTaxSys() {
        TaxRefSetManifest manifestInvalidTaxSys = new TaxRefSetManifest();

        manifestInvalidTaxSys.setName("testManifestInavlidTaxSys");
        manifestInvalidTaxSys.setDescription("Description");
        manifestInvalidTaxSys.setTaxonomySystem("NCBI!!!");
        manifestInvalidTaxSys.setTaxonomySystemVersion("");

        SubmissionFiles validFiles = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        validFiles.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        validFiles.add(tab);
        manifestInvalidTaxSys.setFiles(validFiles);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(manifestInvalidTaxSys);
        assertFalse(validationResult);
    }

    @Test
    public void testNcbiTaxMismatch() {
        TaxRefSetManifest manifestInvalidTaxSys = new TaxRefSetManifest();

        manifestInvalidTaxSys.setName("testNcbiTaxMismatch");
        manifestInvalidTaxSys.setDescription("Description");
        manifestInvalidTaxSys.setTaxonomySystem("Some-other-database");
        manifestInvalidTaxSys.setTaxonomySystemVersion("");

        SubmissionFiles validFiles = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        validFiles.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        validFiles.add(tab);
        manifestInvalidTaxSys.setFiles(validFiles);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(manifestInvalidTaxSys);
        assertFalse(validationResult);
    }
}


