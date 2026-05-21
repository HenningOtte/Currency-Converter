import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CurrencyConverterWindow {

    private final ICurrencyConversion iCurrencyConversion;

    private JTextField currencyAmount;
    private JComboBox<String> sourceCurrency;
    private JComboBox<String> targetCurrency;
    private JComboBox<String> conversionMode;
    private JLabel resultLabel;


    // Initializes the converter window and sets up
    // all panels required for the currency conversion UI.
    public CurrencyConverterWindow(ICurrencyConversion iCurrencyConversion) {
        this.iCurrencyConversion = iCurrencyConversion;

        JFrameWindow window = new JFrameWindow();

        window.add(createOriginalValuePanel(this.iCurrencyConversion), BorderLayout.NORTH);
        window.add(createTargetValuePanel(this.iCurrencyConversion), BorderLayout.CENTER);
        window.add(this.createResultPanel(), BorderLayout.SOUTH);

        window.setVisible(true);

        window.pack();
    }

    // Starts the application and initializes the
    // CurrencyConverterWindow with a CurrencyConversionHandler
    // that contains all available converters.
    public static void main(String[] args) {
        new CurrencyConverterWindow(new CurrencyConversionHandler());
    }

    // Creates the panel for entering the original amount
    // and selecting the source currency.
    private JPanel createOriginalValuePanel(ICurrencyConversion currenciesMethods) {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        TitledBorder title =  BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Ausgangswert");
        jPanel.setBorder(title);

        this.currencyAmount = new JTextField(10);

        this.sourceCurrency = new JComboBox<>();
        this.sourceCurrency.setModel(new DefaultComboBoxModel<>(currenciesMethods.getCurrencies()));

        jPanel.add(this.currencyAmount);
        jPanel.add(this.sourceCurrency);

        return jPanel;
    }

    // Creates the panel for selecting the target currency,
    // converter type, and conversion date, and handles
    // the currency conversion when the button is pressed.
    private JPanel createTargetValuePanel(ICurrencyConversion currenciesMethods) {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        TitledBorder title =  BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Zielwährung");
        jPanel.setBorder(title);

        this.targetCurrency = new JComboBox<>();
        this.targetCurrency.setModel(new DefaultComboBoxModel<>(currenciesMethods.getCurrencies()));

        // JComboBox<String> converter = new JComboBox<>();
        // converter.setModel(new DefaultComboBoxModel<>(currenciesMethods.getConverters()));
        this.conversionMode =  new JComboBox<>();
        this.conversionMode.setModel(new DefaultComboBoxModel<>(currenciesMethods.getConverters()));

        JSpinner jSpinner = new JSpinner(new SpinnerDateModel());
        jSpinner.setEditor(new JSpinner.DateEditor(jSpinner, "yyyy-MM-dd"));

        // Uses the selected converter to load the matching
        // CurrencyConverter from the HashMap and perform
        // the currency conversion.
        JButton jButton = new JButton("Umrechnen");

        jButton.addActionListener(e -> {
            // Retrieves the selected date from the JSpinner
            // and passes it to the CurrencyConversionHandler
            // before performing the currency conversion.
            this.iCurrencyConversion.setDate((Date) jSpinner.getValue());

            int currencyInput = processCurrencyInput();

            this.processCurrencyInput();
        });

        jPanel.add(this.targetCurrency);
        jPanel.add(this.conversionMode);
        jPanel.add(jSpinner);
        jPanel.add(jButton);

        return jPanel;
    }

    // Validates the user input, performs the currency conversion,
    // and updates the result label with the converted value or an error message.
    private int processCurrencyInput() {
        int currencyInput = 0;
        try {
            currencyInput = Integer.parseInt(currencyAmount.getText());
            double result = iCurrencyConversion.performConversion(currencyInput, (String) this.sourceCurrency.getSelectedItem(), (String) this.targetCurrency.getSelectedItem(), (String) this.conversionMode.getSelectedItem());

            this.resultLabel.setText("Result: " + String.format("%.2f", result) + " " + (String) this.targetCurrency.getSelectedItem());
        } catch (Exception e) {
            this.resultLabel.setText("Please enter a number!");
            currencyAmount.setText("");
        }
        return currencyInput;
    }

    // Creates the panel used to display the currency conversion result.
    private JPanel createResultPanel() {
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        TitledBorder title =  BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Ergebnis");
        jPanel.setBorder(title);

        this.resultLabel = new JLabel("Ergebnis");

        jPanel.add(this.resultLabel);

        return jPanel;
    }
}