package uk.ac.ebi.ena.txmbvalidator;

import uk.ac.ebi.ena.webin.cli.validator.api.ValidationResponse;
import uk.ac.ebi.ena.webin.cli.validator.api.Validator;
import uk.ac.ebi.ena.webin.cli.validator.manifest.Manifest;
import uk.ac.ebi.ena.webin.cli.validator.manifest.ReadsManifest;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationMessage;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationOrigin;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class FakeValidator implements Validator<Manifest,ValidationResponse> {

    @Override
    public ValidationResponse validate(Manifest manifest) {
        if (someValidationHere(manifest)) {
            return new ValidationResponse(ValidationResponse.status.VALIDATION_SUCCESS);
        } else {
            return new ValidationResponse(ValidationResponse.status.VALIDATION_ERROR);
        }
    }

    public boolean someValidationHere(Manifest manifest) {
        ValidationResult validationResult = new ValidationResult();
        File fastaLog = new File("C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\main\\resources\\FASTA.log");
        validateFasta(validationResult, fastaLog);
        return true;
    }

    public ValidationResult validateFasta(ValidationResult validationResult, File fastaLog) {
        String zfile = "C:\\Users\\holt\\IdeaProjects\\txmb-validator\\src\\main\\resources\\empty_line_id.fasta.gz";
        BufferedReader bf;
        String currentLine;
        int lineNumber = 0;
        ValidationResult fastaValidationResult = new ValidationResult(validationResult, fastaLog);

        try {
            GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(zfile));
            bf = new BufferedReader( new InputStreamReader( gzip ));

            while ((currentLine = bf.readLine()) != null) {
                lineNumber++;
                System.out.println(currentLine);
                if (currentLine.matches( "\\s*")) {
                    ValidationOrigin validationOrigin = new ValidationOrigin("FASTA Line", lineNumber);
                    ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Empty FASTA line");
                    validationMessage.appendOrigin(validationOrigin);
                    fastaValidationResult.add(validationMessage);
                }
            }
        } catch ( IOException ex ) {
            System.out.println("File '" + zfile + "' could not be read or could not be found");
        }

        return fastaValidationResult;
    }

}
