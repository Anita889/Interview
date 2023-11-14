package interviews;
import com.opencsv.exceptions.CsvValidationException;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.*;
import java.io.IOException;



/**
 * Unit test for simple App.
 */
public class FundingRaisedTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FundingRaisedTest ( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static TestSuite suite()
    {
        return  new TestSuite( FundingRaisedTest.class );
    }

    public void testWhereGivenCompany() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "Facebook");
            assertEquals(7, FundingRaised.filterRequest(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testWhereGivenCity() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("city", "Tempe");
            assertEquals(3, FundingRaised.filterRequest(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testWhereGivenState() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("state", "CA");
            assertEquals(873, FundingRaised.filterRequest(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testWhereGivenRound() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("round", "a");
            assertEquals(582, FundingRaised.filterRequest(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testMultipleOptions() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("round", "a");
            options.put("company_name", "Facebook");
            assertEquals(1, FundingRaised.filterRequest(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testWhereNotExists() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "NotFacebook");
            assertEquals(0, FundingRaised.filterRequest(options).size());
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testWhereCorrectKeys() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "Facebook");
            Map<String, String> row = FundingRaised.filterRequest(options).get(0);

            assertEquals("facebook", row.get("permalink"));
            assertEquals("Facebook", row.get("company_name"));
            assertEquals("450", row.get("number_employees"));
            assertEquals("web", row.get("category"));
            assertEquals("Palo Alto", row.get("city"));
            assertEquals("CA", row.get("state"));
            assertEquals("1-Sep-04", row.get("funded_date"));
            assertEquals("500000", row.get("raised_amount"));
            assertEquals("angel", row.get("round"));
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void testFindByGivenCompanyName() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "Facebook");
            Map<String, String> row = FundingRaised.findUniqueMember(options);

            assertEquals("facebook", row.get("permalink"));
            assertEquals("Facebook", row.get("company_name"));
            assertEquals("450", row.get("number_employees"));
            assertEquals("web", row.get("category"));
            assertEquals("Palo Alto", row.get("city"));
            assertEquals("CA", row.get("state"));
            assertEquals("1-Sep-04", row.get("funded_date"));
            assertEquals("500000", row.get("raised_amount"));
            assertEquals("angel", row.get("round"));
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch(NoSuchEntryException | CsvValidationException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        }
    }

    public void testFindByGivenState() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("state", "CA");
            Map<String, String> row = FundingRaised.findUniqueMember(options);

            assertEquals("digg", row.get("permalink"));
            assertEquals("Digg", row.get("company_name"));
            assertEquals("60", row.get("number_employees"));
            assertEquals("web", row.get("category"));
            assertEquals("San Francisco", row.get("city"));
            assertEquals("CA", row.get("state"));
            assertEquals("1-Dec-06", row.get("funded_date"));
            assertEquals("8500000", row.get("raised_amount"));
            assertEquals("b", row.get("round"));
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch(NoSuchEntryException | CsvValidationException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        }
    }

    public void testFindByMultipleOptions() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "Facebook");
            options.put("round", "c");
            Map<String, String> row = FundingRaised.findUniqueMember(options);

            assertEquals("facebook", row.get("permalink"));
            assertEquals("Facebook", row.get("company_name"));
            assertEquals("450", row.get("number_employees"));
            assertEquals("web", row.get("category"));
            assertEquals("Palo Alto", row.get("city"));
            assertEquals("CA", row.get("state"));
            assertEquals("1-Oct-07", row.get("funded_date"));
            assertEquals("300000000", row.get("raised_amount"));
            assertEquals("c", row.get("round"));
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch(NoSuchEntryException | CsvValidationException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        }
    }

    public void testFindByNotExists() {
        try {
            Map<String, String> options = new HashMap<String, String> ();
            options.put("company_name", "NotFacebook");
            options.put("round", "c");
            Map<String, String> row = FundingRaised.findUniqueMember(options);
            fail("findBy should throw exception");
        } catch(IOException e) {
            System.out.print(e.getMessage());
            System.out.print("error");
        } catch(NoSuchEntryException | CsvValidationException e) {
        }
    }
}
