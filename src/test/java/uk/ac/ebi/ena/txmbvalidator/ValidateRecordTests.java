package uk.ac.ebi.ena.txmbvalidator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateRecordTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private String[] headers = Stream.of(MetadataTableValidator.MandatoryHeaders.values()).map(MetadataTableValidator.MandatoryHeaders::name).toArray(String[]::new);
    private String recordData;
    private CSVRecord record;
    private boolean expected;

    public ValidateRecordTests(String[] values, boolean expected) {
        recordData = String.join(",", values);
        try (final CSVParser parser = CSVFormat.DEFAULT.withHeader(headers).parse(new StringReader(recordData))) {
            this.record = parser.iterator().next();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator("NOT_APPLICABLE", emptyValidationResult, false);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {new String[] {"ITS1DB00887249", "FM986452", "61..286", "Pimpinella kyimbilaensis", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", "591038"}, true},
                {new String[] {"", "FM986452", "61..286", "Pimpinella kyimbilaensis", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", "591038"}, false},
                {new String[] {"", "", "", "", "", ""}, false},
                {new String[] {"ITS1DB00887249", "FM986452", "61..286", "Pimpinella kyimbilaensis", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", ""}, true},
                {new String[] {"ITS1DB00887249", "", "61..286", "Pimpinella kyimbilaensis", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", "591038"}, false},
                {new String[] {"ITS1DB00887249", "", "", "Pimpinella kyimbilaensis", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", "591038"}, true},
                {new String[] {"ITS1DB00887249", "FM986452", "", "Pimpinella kyimbilaensis", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", "591038"}, true},
                {new String[] {"ITS1DB00887249", "FM986452", "61..286", "Pimpinella kyimbilaensis", "", "591038"}, false},
                {new String[] {"ITS1DB00887249", "FM986452", "61..286", "", "cellular organisms ; Eukaryota ; Viridiplantae ; Streptophyta ; Streptophytina ; Embryophyta ; Tracheophyta ; Euphyllophyta ; Spermatophyta ; Magnoliophyta ; Mesangiospermae ; eudicotyledons ; Gunneridae ; Pentapetalae ; asterids ; campanulids ; Apiales ; Apiineae ; Apiaceae ; Apioideae ; apioid superclade ; Pimpinelleae ; Pimpinella", "591038"}, false},
        });
    }

    @org.junit.Test
    public void validateRecord() {
        mtv.validateRecord(record);
        assertEquals(expected, mtv.getValid());
    }
}

