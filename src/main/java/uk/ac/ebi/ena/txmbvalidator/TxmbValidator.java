package uk.ac.ebi.ena.txmbvalidator;

import uk.ac.ebi.ena.webin.cli.validator.api.ValidationResponse;
import uk.ac.ebi.ena.webin.cli.validator.api.Validator;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFiles;
import uk.ac.ebi.ena.webin.cli.validator.manifest.Manifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TxmbValidator implements Validator<Manifest, ValidationResponse> {

    private ValidationResult manifestValidationResult;
    private MetadataRecordValidator vmr;
    private MetadataTableValidator vmt;
    private FastaValidator vfa;

    @Override
    public ValidationResponse validate(Manifest manifest) {
        if (validateTxmb(manifest)) {
            return new ValidationResponse(ValidationResponse.status.VALIDATION_SUCCESS);
        } else {
            return new ValidationResponse(ValidationResponse.status.VALIDATION_ERROR);
        }
    }

    public boolean validateTxmb(Manifest manifest) {

        TaxRefSetManifest txmbManifest = (TaxRefSetManifest) manifest;

        String taxSystem = txmbManifest.getTaxonomySystem();
        String taxSystemVersion = txmbManifest.getTaxonomySystemVersion();
        HashMap<String, String> customFields = (HashMap) txmbManifest.getCustomFields();
        SubmissionFiles submissionFiles = txmbManifest.getFiles();

        List<SubmissionFile> inputFastaFiles = submissionFiles.get(TaxRefSetManifest.FileType.FASTA);
        SubmissionFile fastaFile = inputFastaFiles.get(0);

        List<SubmissionFile> inputTabFiles = submissionFiles.get(TaxRefSetManifest.FileType.TAB);
        SubmissionFile tabFile = inputTabFiles.get(0);

        manifestValidationResult = new ValidationResult(new File(txmbManifest.getReportFile().getPath()+".report"));

        vmr = new MetadataRecordValidator(manifestValidationResult, taxSystem, taxSystemVersion, fastaFile.getFile(), tabFile.getFile(), customFields);
        boolean ncbiTax = vmr.validateMetadataRecord();
        if (!vmr.getValid()) {
            return false;
        }

        vmt = new MetadataTableValidator(tabFile, manifestValidationResult, ncbiTax, customFields);
        vmt.validateMetadataTable();
        if (!vmt.getValid()) {
            return false;
        }

        ArrayList<String> tableIdentifiers = vmt.getLocalIdentifiers();

        vfa = new FastaValidator(fastaFile, tableIdentifiers, manifestValidationResult);
        vfa.validateFasta();

        if (vfa.getValid()) {
            return true;
        } else {
            return false;
        }
    }
}