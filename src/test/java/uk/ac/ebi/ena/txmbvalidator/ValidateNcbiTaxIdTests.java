package uk.ac.ebi.ena.txmbvalidator;

import org.junit.runners.Parameterized;
import uk.ac.ebi.ena.webin.cli.validator.message.ValidationResult;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ValidateNcbiTaxIdTests {

    private String taxId;
    private boolean expected;
    private boolean ncbiTax;
    private MetadataTableValidator mtv;

    public void validateNcbiTaxIdTests(String taxId, boolean ncbiTax, boolean expected) {
        this.taxId = taxId;
        this.ncbiTax = ncbiTax;
        this.expected = expected;
    }

    @org.junit.Before
    public void setup() {
        ValidationResult emptyValidationResult = new ValidationResult();
        mtv = new MetadataTableValidator("NOT_APPLICABLE", emptyValidationResult, ncbiTax);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testConditions() {
        return Arrays.asList(new Object[][]{
                {"6", true, true},
                {"6", false, false},
                {"6h", true, false},
                {"6h", false, false},
                {null, true, false},
                {null, false, true}
        });
    }

    @org.junit.Test
    public void checkIdentifier() {
        mtv.validateNcbiTaxId(taxId, ncbiTax);
        assertEquals(expected, mtv.getValid());
    }
}
