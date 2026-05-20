import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CurrencyConverterWindow {

    private final ICurrencyConversion iCurrencyConversion;
    private JTextField currencyAmount;
    private JComboBox<String> sourceCurrency;
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

        JComboBox<String> currency = new JComboBox<>();
        currency.setModel(new DefaultComboBoxModel<>(currenciesMethods.getCurrencies()));

        JComboBox<String> converter = new JComboBox<>();
        converter.setModel(new DefaultComboBoxModel<>(currenciesMethods.getConverters()));

        JSpinner jSpinner = new JSpinner(new SpinnerDateModel());
        jSpinner.setEditor(new JSpinner.DateEditor(jSpinner, "yyyy-MM-dd"));

        JButton jButton = new JButton("Umrechnen");


        // Uses the selected converter to load the matching
        // CurrencyConverter from the HashMap and perform
        // the currency conversion.
        jButton.addActionListener(e -> {
            double result = iCurrencyConversion.performConversion(Integer.parseInt(currencyAmount.getText()), (String) this.sourceCurrency.getSelectedItem(), (String) currency.getSelectedItem(), (String) converter.getSelectedItem());
            this.resultLabel.setText("Ergebnis: " + String.format("%.2f", result));
        });

        jPanel.add(currency);
        jPanel.add(converter);
        jPanel.add(jSpinner);
        jPanel.add(jButton);

        return jPanel;
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