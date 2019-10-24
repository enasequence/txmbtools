package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ValidateLocalLineageTests {

    private MetadataTableValidator mtv;
    private boolean expected;
    private String lineage;

    public void validateLocalLineageTests(String lineage, boolean expected) {
        this.lineage = lineage;
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
                {"cellular organisms; Eukaryota; Opisthokonta; Metazoa; Eumetazoa; Bilateria; Deuterostomia; Chordata; Craniata; Vertebrata; Gnathostomata; Teleostomi; Euteleostomi; Sarcopterygii; Dipnotetrapodomorpha; Tetrapoda; Amniota; Mammalia; Theria; Eutheria; Boreoeutheria; Euarchontoglires; Primates; Haplorrhini; Simiiformes; Catarrhini; Hominoidea; Hominidae; Homininae; Homo", true},
                {"cellular organisms; Bacteria; Proteobacteria; Gammaproteobacteria; Enterobacterales; Enterobacteriaceae; Escherichia", true},
                {"", true},
                {null, true}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateLocalLineage(lineage);
        assertEquals(expected, mtv.getValid());
    }
}
