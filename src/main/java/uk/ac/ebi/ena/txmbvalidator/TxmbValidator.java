package uk.ac.ebi.ena.txmbvalidator;

import uk.ac.ebi.ena.webin.cli.validator.api.ValidationResponse;
import uk.ac.ebi.ena.webin.cli.validator.api.Validator;
import uk.ac.ebi.ena.webin.cli.validator.manifest.Manifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.TaxRefSetManifest;

public class TxmbValidator implements Validator<Manifest, ValidationResponse> {

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
        TaxRefSetManifest other = (TaxRefSetManifest) manifest;
        other.
//        Run metadata record validator, get back ncbi tax bool
//          Return false if not valid
//        Run metadata table valiator, get back local identifiers -> getLocalIdentifiers() ( ArrayList<String> )
//          Return false if not valid
//        Run FASTA validator
//          Return false if not valid
//          Return true if valid
//        Make sure error reports are written to workingDir or outputDir
        return false;
    }
}

//taxrefsetmanifestreader.java - WebinCli