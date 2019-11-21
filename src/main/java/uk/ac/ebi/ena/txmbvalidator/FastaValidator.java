package uk.ac.ebi.ena.txmbvalidator;


import uk.ac.ebi.ena.webin.cli.validator.message.ValidationMessage;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationOrigin;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class FastaValidator {

    private final String NUCLEOTIDE_REGEX = "^[ATCGactgRYSWKMBDHVryswkmbdhvNn]*$";
    private int linecount = 0;
    private char lineflag = 's';
    private ArrayList<String> remainingIds;
    private File fastaFile;
    private File fastaLogFile;
    private ValidationResult fastaValidationResult;


    public FastaValidator(File fastaFile, ArrayList<String> table_identifiers, ValidationResult manifestValidationResult) {
        this.fastaFile = (fastaFile); // TODO: fix to work with input from manifest
        this.fastaLogFile = new File(fastaFile + ".report"); // TODO: Output Dir here?
        this.remainingIds = table_identifiers;
        this.fastaValidationResult = new ValidationResult(manifestValidationResult, fastaLogFile);
    }


    public ValidationResult validateFasta() {

        String currentLine;

        BufferedReader fastaReader = openFasta(this.fastaFile);
        if (!fastaValidationResult.isValid()) {
            return fastaValidationResult; // if an error was found in openFasta() then attempting further validation is pointless
        }

        try {
            while ((currentLine = fastaReader.readLine()) != null) {
                linecount++;

                if (currentLine.isEmpty()) {
                    ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", linecount);
                    ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Empty line in FASTA is not acceptable");
                    validationMessage.appendOrigin(validationOrigin);
                    fastaValidationResult.add(validationMessage);

                    continue;
                }

                if (currentLine.charAt(0) == '>') {
                        if (lineflag == 's') {
                            lineflag = 'i';
                            checkIdentifier(currentLine, remainingIds);
                        } else {
                            ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", linecount);
                            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Two consecutive ID lines (\">\") in FASTA ");
                            validationMessage.appendOrigin(validationOrigin);
                            fastaValidationResult.add(validationMessage);
                        }
                } else if (lineflag == 'i') {
                    lineflag = 's';
                    checkSequence(currentLine);
                } else if (lineflag == 's') {
                    ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", linecount);
                    ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Two consecutive sequence lines in FASTA ");
                    validationMessage.appendOrigin(validationOrigin);
                    fastaValidationResult.add(validationMessage);
                }
            }
        } catch ( IOException ex ) {
            ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", fastaFile);
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Could not read line");
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }

        if (linecount % 2 != 0) {
            ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", fastaFile);
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Input FASTA does not contain an even number of lines");
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }


        if (remainingIds.isEmpty()) {
        } else {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, ("Sequence identifier(s) " + remainingIds.toString() + " from table not found in FASTA"));
            ValidationOrigin validationOrigin = new ValidationOrigin("FASTA ID Line", linecount);
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }

        return fastaValidationResult;
    }


    public BufferedReader openFasta(File fastaFile) {

        BufferedReader fastaReader = null;

        try {
            assert(fastaFile.exists());
            GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(fastaFile));
            fastaReader = new BufferedReader(new InputStreamReader(gzip));
        } catch ( AssertionError ex ) {
            ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", fastaFile);
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Could not find file");
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        } catch ( IOException ex ) {
            ValidationOrigin validationOrigin = new ValidationOrigin("FASTA File", fastaFile);
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Could not read file");
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }

        return fastaReader;
    }


    public void checkIdentifier(String idLine, ArrayList<String> remainingIds) {

        boolean idCaught;
        int index;
        String originName = "FASTA ID Line";

        String identifier = getIdentifier(idLine, originName);

        index = remainingIds.indexOf(identifier);

        if (index >= 0) {
            remainingIds.remove(index);
        } else {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, ("Sequence identifier '" + identifier + "' from FASTA not found in table"));
            ValidationOrigin validationOrigin = new ValidationOrigin(originName, linecount);
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }
    }


    public String getIdentifier(String idLine, String originName) {

        String identifier;

        try {
            identifier = idLine.substring(idLine.indexOf(">") + 1, idLine.indexOf("|"));
            return identifier;
        } catch ( StringIndexOutOfBoundsException ex1 ) {
            try {
                identifier = idLine.substring(idLine.indexOf(">") + 1);
                return identifier;
            } catch ( StringIndexOutOfBoundsException ex2 ) {
                ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Could not find appropriately formatted sequence identifier");
                ValidationOrigin validationOrigin = new ValidationOrigin(originName, linecount);
                validationMessage.appendOrigin(validationOrigin);
                fastaValidationResult.add(validationMessage);
            }
        }

        return null;
    }


    public void checkSequence(String sequence) {

        String orginName = "FASTA Seq Line";

        if (sequence.isEmpty()) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Empty sequence line");
            ValidationOrigin validationOrigin = new ValidationOrigin(orginName, linecount);
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
            return;
        }

        if (!sequence.matches(NUCLEOTIDE_REGEX)) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence contains invalid characters");
            ValidationOrigin validationOrigin = new ValidationOrigin(orginName, linecount);
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }

        if (sequence.matches("\\s")) {
            ValidationMessage validationMessage = new ValidationMessage(ValidationMessage.Severity.ERROR, "Sequence contains illegal whitespace");
            ValidationOrigin validationOrigin = new ValidationOrigin(orginName, linecount);
            validationMessage.appendOrigin(validationOrigin);
            fastaValidationResult.add(validationMessage);
        }
    }

    public boolean getValid() {
        return fastaValidationResult.isValid();
    }
}
