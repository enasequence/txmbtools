package uk.ac.ebi.ena.txmbvalidator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetHeaderListTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private String metadataTableFilename;
    private boolean expected;
    private CSVParser parser;
    private static final HashMap<String, String> noCols = new HashMap<>();

    public GetHeaderListTests(String metadataTableFilename, boolean expected) {
        this.metadataTableFilename = metadataTableFilename;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator("NOT_APPLICABLE", emptyValidationResult, false, noCols);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {(RESOURCETSVDIR + "valid.tsv.gz"), true},
                {(RESOURCETSVDIR + "valid_w_customs.tsv.gz"), true},
                {(RESOURCETSVDIR + "missing_id_column.tsv.gz"), true}
        });
    }

    @org.junit.Test
    public void getHeaderList() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(metadataTableFilename))));
            parser = CSVParser.parse(br, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException occurred in GetHeaderListTests test case");
        } catch (IOException e) {
            System.out.println("IOException occurred in GetHeaderListTests test case");
        }

        mtv.getHeaderList(parser);
        assertEquals(expected, mtv.getValid());
    }
}
