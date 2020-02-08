package k;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MortageTest
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }
    
    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testInPower()
    {
        assertEquals( Mortage.inPower(2.0, 2.0), new Double(4.0));
        //assertEquals( Mortage.inPower(2.0, -2.0), new Double(0.25));
    }
    @Test
    public void testRound()
    {
        assertEquals( Mortage.roundToTwoDecimals(2.1189), new Double(2.12));
        assertEquals( Mortage.roundToTwoDecimals(0.00001), new Double(0.0));
        assertEquals( Mortage.roundToTwoDecimals(0.14449), new Double(0.14));
        assertEquals( Mortage.roundToTwoDecimals(-0.14449), new Double(-0.13));
    }
    @Test
    public void testMakeMortage()
    {
        Mortage.calculateMortage();
        Integer count = 0;
        Scanner scanner = new Scanner(outContent.toString());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (count == 1){
                assertEquals("Prospect 1: Juha wants to borrow 1000.0 € for a period of 2 years and pay 44.32 € each month"
                ,line);
            }
            if (count == 4){
                assertEquals("Prospect 2: Karvinen wants to borrow 4356.0 € for a period of 6 years and pay 61.15 € each month"
                ,line);
            }
            if (count == 7){
                assertEquals("Prospect 3: Claes Månsson wants to borrow 1300.55 € for a period of 2 years and pay 58.82 € each month"
                ,line);
            }
            if (count == 10){
                assertEquals("Prospect 4: Clarencé Andersson wants to borrow 2000.0 € for a period of 4 years and pay 44.8 € each month"
                ,line);
            }
            count++;
        }
        scanner.close();
    }


}
