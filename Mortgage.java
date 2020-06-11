import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class Mortgage extends JFrame {
    private static final int longYearLoan = 30;
    private static final int shortYearLoan = 15;
    private JTextField cost = new JTextField(12);
    private JTextField interest = new JTextField(12);
    private JTextField propTax = new JTextField(12);
    private JTextField downPayment = new JTextField(12);
    private Document amountText = cost.getDocument();
    private Document interestText = interest.getDocument();
    private Document propTaxText = propTax.getDocument();
    private Document downPaymentText = downPayment.getDocument();
    private JTextArea report = new JTextArea(20, 30);

    public Mortgage() {
        JPanel topPanel = new JPanel();
        JPanel lowerPanel = new JPanel();
        var longTermButton = new JButton("30 Year Loan");
        var shortTermButton = new JButton("15 Year Loan");

        topPanel.add(new JLabel("Amount:"));
        topPanel.add(cost);
        topPanel.add(new JLabel("Interest Rate:"));
        topPanel.add(interest);
        topPanel.add(new JLabel("Property Tax Percent:"));
        topPanel.add(propTax);
        topPanel.add(new JLabel("Down Payment Percent:"));
        topPanel.add(downPayment);
        topPanel.add(longTermButton);
        topPanel.add(shortTermButton);
        var summaryButton = new JButton("Summary");
        var principleButton = new JButton("Principle");
        var interestButton = new JButton("Interest");
        lowerPanel.add(summaryButton);
        lowerPanel.add(principleButton);
        lowerPanel.add(interestButton);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(report), BorderLayout.WEST);
        getContentPane().add(lowerPanel, BorderLayout.SOUTH);
        //getContentPane().add(new JScrollPane(graph), BorderLayout.CENTER);
        setBackground(Color.LIGHT_GRAY);
        report.setEditable(false);
        summaryButton.addActionListener(e -> errorMessage());
        principleButton.addActionListener(e -> errorMessage());
        interestButton.addActionListener(e -> errorMessage());
        longTermButton.addActionListener(e -> updateReport(30, principleButton, interestButton, summaryButton));
        shortTermButton.addActionListener(e -> updateReport(15, principleButton, interestButton, summaryButton));

    }

    void errorMessage() {
        report.setText("Error not enough info");
    }
    
    void displaySummary(double monthlyPayment, double downPaymentAmount, double propertyTaxAmount) {
        report.setText("");
        report.append(String.format("%-35s\n\n", "Mortgage Repayment Summary"));
        report.append(String.format("%s\n", "Monthly Payment:  "));
        report.append(String.format("%s%.2f\n\n", "$", monthlyPayment));
        report.append(String.format("%s\n", "Down Payment Amount:  "));
        report.append(String.format("%s%.2f\n\n", "$", downPaymentAmount));
        report.append(String.format("%s\n", "Property Tax Per Year:  "));
        report.append(String.format("%s%.2f\n\n", "$", propertyTaxAmount));
        report.append(String.format("%s\n", "Property Tax Per Month:  "));
        report.append(String.format("%s%.2f\n\n", "$", propertyTaxAmount / 12));
    }

    void displayPrinciple(LinkedList<Double> principle) {
        report.setText("");
        report.append(String.format("%-15s", "Payment"));
        report.append(String.format("%-15s", "Principle"));
        for (var count = 0; count < principle.size(); count++) {
            report.append(String.format("%-15d", count + 1));
            report.append(String.format("%-15.2f\n", principle.get(count)));
        }
    }
    
    void displayInterest(LinkedList<Double> interest) {
        report.setText("");
        report.append(String.format("%-15s", "Payment"));
        report.append(String.format("%-15s", "Interest"));
        for (var count = 0; count < interest.size(); count++) {
            report.append(String.format("%-15d", count + 1));
            report.append(String.format("%-15.2f\n", interest.get(count)));
        }
    }


    void updateReport(int loan, JButton principleButton, JButton interestButton, JButton summaryButton) {
        try {
            summaryButton.removeActionListener(summaryButton.getActionListeners()[0]);
            interestButton.removeActionListener(interestButton.getActionListeners()[0]);
            principleButton.removeActionListener(principleButton.getActionListeners()[0]);
            report.setText("Select what to view below...");
            var amount = Double.parseDouble(amountText.getText(0, amountText.getLength()));
            var interest = Double.parseDouble(interestText.getText(0, interestText.getLength()));
            var propTax = Double.parseDouble(propTaxText.getText(0, propTaxText.getLength()));
            var downPayment = Double.parseDouble(downPaymentText.getText(0, downPaymentText.getLength()));
            var downPaymentAmount = downPayment / 100 * amount;
            var propertyTaxAmount = propTax / 100 * amount;
            var interestPerMonth = new LinkedList<Double>();
            var principlePerMonth = new LinkedList<Double>();
            var remainingBalancePerMonth = new LinkedList<Double>();
            var loanAmount = amount - downPayment;
            var monthlyInterestRate = interest / 1200;
            var balance = amount;
            var monthlyPayment = loanAmount * monthlyInterestRate
                    / (1 - 1 / Math.pow(1 + monthlyInterestRate, loan * 12));
            // report.append(String.format("%-35s\n\n", "Mortgage Repayment Summary"));
            // report.append(String.format("%s\n", "Monthly Payment:  "));
            // report.append(String.format("%s%.2f\n\n", "$", monthlyPayment));
            // report.append(String.format("%s\n", "Down Payment Amount:  "));
            // report.append(String.format("%s%.2f\n\n", "$", downPaymentAmount));
            // report.append(String.format("%s\n", "Property Tax Per Year:  "));
            // report.append(String.format("%s%.2f\n\n", "$", propertyTaxAmount));
            // report.append(String.format("%s\n", "Property Tax Per Month:  "));
            // report.append(String.format("%s%.2f\n\n", "$", propertyTaxAmount / 12));
            for (int i = 1; i <= loan * 12; i++) {
                interest = monthlyInterestRate * balance;
                loanAmount = monthlyPayment - interest;
                balance = balance - loanAmount;
                interestPerMonth.add(interest);
                principlePerMonth.add(loanAmount);
                remainingBalancePerMonth.add(balance);
            }
            summaryButton.addActionListener(e -> displaySummary(monthlyPayment, downPaymentAmount, propertyTaxAmount));
            principleButton.addActionListener(e -> displayPrinciple(principlePerMonth));
            interestButton.addActionListener(e -> displayInterest(interestPerMonth));


        } catch (NumberFormatException e) {
            report.setText("Either once of the text field is not an integer or out of range for amount");
        } catch (Exception e) {
            report.setText(e.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new Mortgage();
            frame.setTitle("Mortgage Calculator");
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

// public class Mortgage{

//     public static void main (String[]args){
//         Scanner in = new Scanner(System.in);
//         int numberYears,
//             paymentNumber;
//         double loanAmount,
//                 monthlyPayment,
//                 totalPayment,
//                 annualInterestRate,
//                 interest,
//                 principle,
//                 balance,
//                 monthlyInterestRate;
//         do{
//             System.out.println("Please enter the amount you are going to loan:  ");
//             loanAmount = in.nextDouble();
//             if(loanAmount <= 0){

//                 System.out.println("Loan Amount can not be less than zero.");
//             }  
//         }
//         while(loanAmount <= 0);

//         do{
//             System.out.println("How many years do you want to finance for?");
//             numberYears = in.nextInt();
//             if(numberYears <= 0){
//                 System.out.println("Years can not be 0 or negative.");
//             }
//         }
//         while(numberYears <= 0);

//         do{
//             System.out.println("What is your annual interest rate?");
//             annualInterestRate = in.nextDouble();
//             if(annualInterestRate <= 0){
//                 System.out.println("Annual Interest Rate can not be negative.");
//             }
//         }
//         while(numberYears <= 0);
        
//         monthlyInterestRate = annualInterestRate/1200;
//         balance = loanAmount;
//         monthlyPayment = loanAmount * monthlyInterestRate/(1-1/Math.pow(1+monthlyInterestRate,numberYears * 12));
//             System.out.printf("%-15s", "Payment");
//             System.out.printf("%-15s","Interest");
//             System.out.printf("%-15s","Principle");
//             System.out.printf("%-15s%n","Balance");
//         for(int i= 1; i <=numberYears * 12; i ++){
//             interest = monthlyInterestRate * balance;
//             loanAmount = monthlyPayment - interest;
//             balance = balance - loanAmount;
//             System.out.printf("%-15d",i);
//             System.out.printf("%-15.2f",interest);
//             System.out.printf("%-15.2f",loanAmount);
//             System.out.printf("%-15.2f%n",balance);
//         }
        
//     }
// }