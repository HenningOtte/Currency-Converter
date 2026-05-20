import java.util.HashMap;
import java.util.Map;

public class CurrencyConversionHandler implements ICurrencyConversion {
    private final String[] currencies = new String[] {"USD", "GBP", "EUR"};
    private final Map<String, CurrencyConverter> converters = new HashMap<>();

    CurrencyConversionHandler() {
        converters.put("Echtzeit", new LatestCurrencyConverter());
        converters.put("Historisch", null);
        converters.put("Fix", new LatestCurrencyConverter());
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
    public double performConversion(double amount, String sourceCurrency, String targetCurrency, String converter) {
        CurrencyConverter currencyConverter = converters.get(converter);
        return currencyConverter.convertCurrency(amount, sourceCurrency, targetCurrency);
    }
}
