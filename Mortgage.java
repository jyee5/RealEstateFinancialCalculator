import java.util.Scanner;
import java.lang.Math;

public class Mortgage{

    public static void main (String[]args){
        Scanner in = new Scanner(System.in);

        int numberYears,
            paymentNumber;
        double loanAmount,
                monthlyPayment,
                totalPayment,
                annualInterestRate,
                interest,
                principle,
                balance,
                monthlyInterestRate;
        do{
            System.out.println("Please enter the amount you are going to loan:  ");
            loanAmount = in.nextDouble();
            if(loanAmount <= 0){

                System.out.println("Loan Amount can not be less than zero.");
            }  
        }
        while(loanAmount <= 0);

        do{
            System.out.println("How many years do you want to finance for?");
            numberYears = in.nextInt();
            if(numberYears <= 0){
                System.out.println("Years can not be 0 or negative.");
            }
        }
        while(numberYears <= 0);

        do{
            System.out.println("What is your annual interest rate?");
            annualInterestRate = in.nextDouble();
            if(annualInterestRate <= 0){
                System.out.println("Annual Interest Rate can not be negative.");
            }
        }
        while(numberYears <= 0);
        
        monthlyInterestRate = annualInterestRate/1200;
        balance = loanAmount;
        monthlyPayment = loanAmount * monthlyInterestRate/(1-1/Math.pow(1+monthlyInterestRate,numberYears * 12));
            System.out.printf("%-15s", "Payment");
            System.out.printf("%-15s","Interest");
            System.out.printf("%-15s","Principle");
            System.out.printf("%-15s%n","Balance");
        for(int i= 1; i <=numberYears * 12; i ++){
            interest = monthlyInterestRate * balance;
            loanAmount = monthlyPayment - interest;
            balance = balance - loanAmount;
            System.out.printf("%-15d",i);
            System.out.printf("%-15.2f",interest);
            System.out.printf("%-15.2f",loanAmount);
            System.out.printf("%-15.2f%n",balance);
        }
        
        }
}