import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CurrencyConverterWindow {

    private final ICurrencyConversion iCurrencyConversion;

    public CurrencyConverterWindow(ICurrencyConversion iCurrencyConversion) {
        this.iCurrencyConversion = iCurrencyConversion;
        JFrameWindow window = new JFrameWindow();

        window.add(createOriginalValuePanel(this.iCurrencyConversion), BorderLayout.NORTH);
        window.add(createTargetValuePanel(this.iCurrencyConversion), BorderLayout.CENTER);
        window.add(createResultPanel(), BorderLayout.SOUTH);

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

        JTextField jTextField = new JTextField(10);

        JComboBox<String> currency = new JComboBox<>();
        currency.setModel(new DefaultComboBoxModel<>(currenciesMethods.getCurrencies()));

        jPanel.add(jTextField);
        jPanel.add(currency);

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
        jButton.addActionListener(e -> iCurrencyConversion.performConversion());

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

        JLabel result = new JLabel("Ergebnis");

        jPanel.add(result);

        return jPanel;
    }
}