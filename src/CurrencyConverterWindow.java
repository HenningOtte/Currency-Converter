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

    public CurrencyConverterWindow(ICurrencyConversion iCurrencyConversion) {
        this.iCurrencyConversion = iCurrencyConversion;

        JFrameWindow window = new JFrameWindow();

        window.add(createOriginalValuePanel(this.iCurrencyConversion), BorderLayout.NORTH);
        window.add(createTargetValuePanel(this.iCurrencyConversion), BorderLayout.CENTER);
        window.add(this.createResultPanel(), BorderLayout.SOUTH);

        window.setVisible(true);
    }

    public static void main(String[] args) {
        new CurrencyConverterWindow(new CurrencyConversionHandler());
    }

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