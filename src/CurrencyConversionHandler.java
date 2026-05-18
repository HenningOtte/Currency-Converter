public class CurrencyConversionHandler implements ICurrencyConversion {
    private final String[] currencies = new String[] {"USD", "GBP", "EUR"};
    private final String[] converters = new String[] {"Echtzeit", "Historisch", "Fix"};

    @Override
    public String[] getCurrencies() {
        return this.currencies;
    }


    @Override
    public String[] getConverters() {
        return this.converters;
    }

    @Override
    public void performConversion() {
        System.out.println("Clicked!");
    }
}
