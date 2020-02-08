package k;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;


class Mortage {
    // The method can only calculate positive numbers.
    // Luckily the amount of months are usually positive in this case.
    static Double inPower(Double number, Double power) {
        Double result = 1.0;
        for (int i = 0; i < power; i++){
            result = result * number;
        }
        return result;
      }
    static Double roundToTwoDecimals(Double number) {
    number = number* 100;
    number = number + 0.5;
    Integer temp = number.intValue();
    Double result = temp.doubleValue() / 100;

    return result;
    }
    static void calculateMortage(){
        try {
            File myObj = new File("prospects.txt");
            Scanner myReader = new Scanner(myObj);
            List<List> dataArray = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!data.isEmpty()){
                    if (data.contains("\"")){
                        Pattern p = Pattern.compile(".*\\\" *(.*) *\\\".*");
                        Matcher m = p.matcher(data);
                        m.find();
                        String fixedString = m.group(1);
                        fixedString = fixedString.replace(","," ");
                        fixedString = fixedString.replace("*","");
                        data = data.replace(data.substring(0,data.indexOf("\",")+1), fixedString);
                    }
                        
                    String[] temp = data.split(",");
                    if (temp.length == 4){
                        dataArray.add(Arrays.asList(temp));
                    }
                }
            }
            myReader.close();
            
            for (int i = 1; i < dataArray.size(); i++){

                Double FixedMonthlyPayment = 0.0;
                String customer = dataArray.get(i).get(0).toString();
                Double loan = Double.valueOf(dataArray.get(i).get(1).toString());
                Double interest = Double.valueOf(dataArray.get(i).get(2).toString());
                Double years = Double.valueOf(dataArray.get(i).get(3).toString());
                Double months = years * 12;
                interest = interest / loan;
                Double p = inPower(1+interest, months);
                Double a =  ( interest * p );
                Double b = ( p - 1);
                //E = U[b(1 + b)^p]/[(1 + b)^p - 1]
                FixedMonthlyPayment =  loan * a / b;
                
                System.out.println("****************************************************************************************************");
                System.out.println("Prospect "+i+": "+customer+" wants to borrow "+roundToTwoDecimals(loan)+" € for a period of "+years.intValue()+" years and pay "+roundToTwoDecimals(FixedMonthlyPayment)+" € each month");
                System.out.println("****************************************************************************************************");
            }
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }
    }
    // I went with the idea that the text file should not be tampered with and still get the persons as intended.
    public static void main(String[] args) {
        calculateMortage();
        
    }

}
