import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SalaryCalculator extends JFrame {

    private JTabbedPane tabbedPane;
    private JPanel salaryPanel;
    private JPanel investmentPanel;
    private JLabel salaryLabel;
    private JTextField salaryField;
    private JLabel raiseLabel;
    private JTextField raiseField;
    private JButton calculateButton;
    private JLabel resultLabel;
    private JTextField resultField;
    private JLabel investmentLabel;
    private JTextField investmentField;
    private JRadioButton valueButton;
    private ButtonGroup radioGroup;
    private JLabel typeLabel;
    private JComboBox<String> typeCombo;
    private JLabel growthLabel;
    private JTextField growthField;
    private JButton investButton;
    private JLabel portfolioLabel;
    private JTextField portfolioField;
    private JPanel graphPanel;

    public SalaryCalculator() {

        salaryPanel = new JPanel();
        salaryLabel = new JLabel("Current salary:");
        salaryField = new JTextField(10);
        raiseLabel = new JLabel("Projected annual raise (%):");
        raiseField = new JTextField(10);
        calculateButton = new JButton("Calculate");
        resultLabel = new JLabel("Future (15 year) salary: ");
        resultField = new JTextField(10);
        resultField.setEditable(false);
        graphPanel = new JPanel();

        salaryPanel.add(salaryLabel);
        salaryPanel.add(salaryField);
        salaryPanel.add(raiseLabel);
        salaryPanel.add(raiseField);
        salaryPanel.add(calculateButton);
        salaryPanel.add(resultLabel);
        salaryPanel.add(resultField);
        salaryPanel.add(graphPanel);
        
        investmentPanel = new JPanel();
        investmentLabel = new JLabel("Investment amount:");
        investmentField = new JTextField(10);
        valueButton = new JRadioButton("Amount");
        radioGroup = new ButtonGroup();
        radioGroup.add(valueButton);
        typeLabel = new JLabel("Investment type:");
        String[] types = { "Roth IRA", "REIT", "S&P 500", "Other" };
        typeCombo = new JComboBox<>(types);
        growthLabel = new JLabel("Standard growth rate (%):");
        growthField = new JTextField(10);
        investButton = new JButton("Calculate");
        portfolioLabel = new JLabel("Potential portfolio value:");
        portfolioField = new JTextField(10);
        portfolioField.setEditable(false);

        investmentPanel.add(investmentLabel);
        investmentPanel.add(investmentField);
        investmentPanel.add(valueButton);
        investmentPanel.add(typeLabel);
        investmentPanel.add(typeCombo);
        investmentPanel.add(growthLabel);
        investmentPanel.add(growthField);
        investmentPanel.add(investButton);
        investmentPanel.add(portfolioLabel);
        investmentPanel.add(portfolioField);

        tabbedPane = new JTabbedPane();
        tabbedPane.add("Salary Calculator", salaryPanel);
        tabbedPane.add("Investment Calculator", investmentPanel);

        add(tabbedPane);

        calculateButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                double salary = Double.parseDouble(salaryField.getText());
                double raise = Double.parseDouble(raiseField.getText()) / 100;

                graphPanel.removeAll();
                graphPanel.setLayout(new GridLayout(1, 15));

                for (int i = 1; i <= 15; i++) {
                    double futureSalary = salary * Math.pow(1 + raise, i);
                    JLabel label = new JLabel(String.format("%.2f", futureSalary));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 10));
                    label.setPreferredSize(new Dimension(50, (int) futureSalary / 1000));
                    graphPanel.add(label);
                }

                resultField.setText(String.format("%.2f", salary * Math.pow(1 + raise, 15)));
                graphPanel.revalidate();
                graphPanel.repaint();
            }
        });

        investButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                double investment = Double.parseDouble(investmentField.getText());
                String type = (String) typeCombo.getSelectedItem();
                double growthRate;

                if (type.equals("Roth IRA")) {
                    growthRate = 0.085;
                } else if (type.equals("REIT")) {
                    growthRate = 0.133;
                } else if (type.equals("S&P 500")) {
                    growthRate = 0.121;
                } else {
                    growthRate = Double.parseDouble(growthField.getText()) / 100;
                }
                double portfolio = investment * Math.pow(1 + growthRate, 15);

                portfolioField.setText(String.format("%.2f", portfolio));
            }
        });

        setTitle("Salary Calculator");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new SalaryCalculator().setVisible(true);
            }
        });
    }
}