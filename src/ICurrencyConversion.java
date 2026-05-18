public interface ICurrencyConversion {
    String[] getCurrencies();

    String[] getConverters();

    void performConversion();
}
