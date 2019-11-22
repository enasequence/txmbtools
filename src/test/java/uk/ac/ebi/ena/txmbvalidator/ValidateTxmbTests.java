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

    private final String REPORTDIR = "src\\test\\resources\\Report\\";
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
        validManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        files.add(tab);
        validManifest.setFiles(files);

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
        validManifestWithCustoms.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        files.add(tab);
        validManifestWithCustoms.setFiles(files);

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
        manifestInvalidFasta.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"missing_entry.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        files.add(tab);
        manifestInvalidFasta.setFiles(files);

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
        manifestInvalidTab.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"empty_row.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        files.add(tab);
        manifestInvalidTab.setFiles(files);

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
        manifestInvalidTaxSys.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        files.add(tab);
        manifestInvalidTaxSys.setFiles(files);

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
        manifestInvalidTaxSys.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR+"valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR+"valid.tsv.gz")));
        files.add(tab);
        manifestInvalidTaxSys.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(manifestInvalidTaxSys);
        assertFalse(validationResult);
    }

    @Test
    public void testEmptyIdColTsv() {
        TaxRefSetManifest emptyIdColTsvManifest = new TaxRefSetManifest();

        emptyIdColTsvManifest.setName("testEmptyIdColTsv");
        emptyIdColTsvManifest.setDescription("Description");
        emptyIdColTsvManifest.setTaxonomySystem("NCBI");
        emptyIdColTsvManifest.setTaxonomySystemVersion("");
        emptyIdColTsvManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR + "valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR + "empty_id_column.tsv.gz")));
        files.add(tab);
        emptyIdColTsvManifest.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(emptyIdColTsvManifest);
        assertFalse(validationResult);
    }

    @Test
    public void testMissingIdColTsv() {
        TaxRefSetManifest missingIdColTsvManifest = new TaxRefSetManifest();

        missingIdColTsvManifest.setName("testMissingIdColTsv");
        missingIdColTsvManifest.setDescription("Description");
        missingIdColTsvManifest.setTaxonomySystem("NCBI");
        missingIdColTsvManifest.setTaxonomySystemVersion("");
        missingIdColTsvManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR + "valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR + "missing_id_column.tsv.gz")));
        files.add(tab);
        missingIdColTsvManifest.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(missingIdColTsvManifest);
        assertFalse(validationResult);
    }

    @Test
    public void testMissingInsdcAccAndRange() {
        TaxRefSetManifest missingInsdcAccAndRangeManifest = new TaxRefSetManifest();

        missingInsdcAccAndRangeManifest.setName("testMissingInsdcAccAndRange");
        missingInsdcAccAndRangeManifest.setDescription("Description");
        missingInsdcAccAndRangeManifest.setTaxonomySystem("NCBI");
        missingInsdcAccAndRangeManifest.setTaxonomySystemVersion("");
        missingInsdcAccAndRangeManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR + "valid.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR + "missing_insdc_acc_and_range.tsv.gz")));
        files.add(tab);
        missingInsdcAccAndRangeManifest.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(missingInsdcAccAndRangeManifest);
        assertTrue(validationResult);
    }

    @Test
    public void testNonCompressedFasta() {
        TaxRefSetManifest nonCompressedFastaManifest = new TaxRefSetManifest();

        nonCompressedFastaManifest.setName("testNonCompressedFasta");
        nonCompressedFastaManifest.setDescription("Description");
        nonCompressedFastaManifest.setTaxonomySystem("NCBI");
        nonCompressedFastaManifest.setTaxonomySystemVersion("");
        nonCompressedFastaManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR + "not_compressed.fasta")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR + "missing_insdc_acc_and_range.tsv.gz")));
        files.add(tab);
        nonCompressedFastaManifest.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(nonCompressedFastaManifest);
        assertFalse(validationResult);
    }

    @Test
    public void testMissingEntryFasta() {
        TaxRefSetManifest missingEntryFastaManifest = new TaxRefSetManifest();

        missingEntryFastaManifest.setName("testMissingEntryFasta");
        missingEntryFastaManifest.setDescription("Description");
        missingEntryFastaManifest.setTaxonomySystem("NCBI");
        missingEntryFastaManifest.setTaxonomySystemVersion("");
        missingEntryFastaManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR + "missing_entry.fasta")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR + "valid.tsv.gz")));
        files.add(tab);
        missingEntryFastaManifest.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(missingEntryFastaManifest);
        assertFalse(validationResult);
    }

    @Test
    public void testNonExistentFile() {
        TaxRefSetManifest nonExistentFileManifest = new TaxRefSetManifest();

        nonExistentFileManifest.setName("testNonExistentFile");
        nonExistentFileManifest.setDescription("Description");
        nonExistentFileManifest.setTaxonomySystem("NCBI");
        nonExistentFileManifest.setTaxonomySystemVersion("");
        nonExistentFileManifest.setReportFile(new File(REPORTDIR));

        SubmissionFiles files = new SubmissionFiles();
        SubmissionFile fasta = new SubmissionFile(TaxRefSetManifest.FileType.FASTA, new File((RESOURCEFASTADIR + "this_file_does_not_exists.fasta.gz")));
        files.add(fasta);
        SubmissionFile tab = new SubmissionFile(TaxRefSetManifest.FileType.TAB, new File((RESOURCETSVDIR + "valid.tsv.gz")));
        files.add(tab);
        nonExistentFileManifest.setFiles(files);

        txv = new TxmbValidator();
        boolean validationResult = txv.validateTxmb(nonExistentFileManifest);
        assertFalse(validationResult);
    }
}


