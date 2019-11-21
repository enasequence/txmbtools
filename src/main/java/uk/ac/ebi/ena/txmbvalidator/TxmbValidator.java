package uk.ac.ebi.ena.txmbvalidator;

import uk.ac.ebi.ena.webin.cli.validator.api.ValidationResponse;
import uk.ac.ebi.ena.webin.cli.validator.api.Validator;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFile;
import uk.ac.ebi.ena.webin.cli.validator.file.SubmissionFiles;
import uk.ac.ebi.ena.webin.cli.validator.manifest.Manifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<File> inputFastaFiles = submissionFiles.get(TaxRefSetManifest.FileType.FASTA);
        File fastaFile = inputFastaFiles.get(0);

        List<File> inputTabFiles = submissionFiles.get(TaxRefSetManifest.FileType.TAB);
        File tabFile = inputTabFiles.get(0);

        File manifestReportFile = new File(txmbManifest.getName() + ".report");
        manifestValidationResult = new ValidationResult(manifestReportFile);

        vmr = new MetadataRecordValidator(manifestValidationResult, taxSystem, taxSystemVersion, fastaFile, tabFile, customFields);
        boolean ncbiTax = vmr.validateMetadataRecord();
        if (!vmr.getValid()) {
            return false;
        }

//        vmt = new MetadataTableValidator(tabFile, manifestValidationResult, ncbiTax, customFields);
//        vmt.validateMetadataTable();
//        if (!vmt.getValid()) {
//            return false;
//        }
//
//        vfa = new FastaValidator(fastaFile, customFields, manifestValidationResult);
//        vfa.validateFasta();
//
//        if (vfa.getValid()) {
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }
}