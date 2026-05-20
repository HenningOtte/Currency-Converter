import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConversionHandler implements ICurrencyConversion {
    private final String[] currencies = new String[] {"USD", "GBP", "EUR"};
    private final Map<String, CurrencyConverter> converters = new HashMap<>();

    private Date selectedDate;

    CurrencyConversionHandler() {
        converters.put("Echtzeit", new LatestCurrencyConverter());

        // Passes the current CurrencyConversionHandler instance
        // to the HistoricalCurrencyConverter so it can access
        // the selected date through getDate().
        converters.put("Historisch", new HistoricalCurrencyConverter(this));
        converters.put("Fix", new FixedCurrencyConverter());
    }

    @Override
    public String[] getCurrencies() {
        return this.currencies;
    }

    @Override
    public String[] getConverters() {
        return this.converters.keySet().toArray(new String[converters.size()]);
    }

    @Override
    public void setDate(Date date) {
        this.selectedDate = date;
    }

    @Override
    public Date getDate() {
        return this.selectedDate;
    }

    // Retrieves the requested CurrencyConverter
    // implementation (Latest, Historical, or Fixed)
    // and passes the required parameters for conversion.
    @Override
    public double performConversion(double amount, String sourceCurrency, String targetCurrency, String converter) {
        CurrencyConverter currencyConverter = converters.get(converter);
        return currencyConverter.convertCurrency(amount, sourceCurrency, targetCurrency);
    }
}
