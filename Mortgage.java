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
    private Document amountText = cost.getDocument();
    private Document interestText = interest.getDocument();
    private JTextArea report = new JTextArea(8, 40);

    public Mortgage() {
        var total = 0;
        var interestRate = 0;
        var loanYear = 1;
        JPanel topPanel = new JPanel();
        var longTermButton = new JButton("30 Year Loan");
        var shortTermButton = new JButton("15 Year Loan");

        topPanel.add(new JLabel("Amount:"));
        topPanel.add(cost);
        topPanel.add(new JLabel("Interest Rate:"));
        topPanel.add(interest);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(report), BorderLayout.CENTER);

        setBackground(Color.LIGHT_GRAY);
        report.setEditable(false);

        amountText.addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateReport();
            }

            public void insertUpdate(DocumentEvent e) {
                updateReport();
            }

            public void removeUpdate(DocumentEvent e) {
                updateReport();
            }
        });
    }
    void updateReport() {
        try {
            var amount = Integer.parseInt(amountText.getText(0, amountText.getLength()));
            var interest = Integer.parseInt(interestText.getText(0, interestText.getLength()));
            
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