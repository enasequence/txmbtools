package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidateMetadataTableTests {

    private static final String RESOURCETSVDIR = "src\\test\\resources\\TSV\\";
    private MetadataTableValidator mtv;
    private boolean expected;

    public void ValidateMetadataTableTests() {

    }

    @org.junit.Before
    public void setup() {

    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{

        });
    }

    @org.junit.Test
    public void validateMetadataTable() {
        mtv.validateMetadataTable();
        assertEquals(expected, mtv.getValid());
    }
}
