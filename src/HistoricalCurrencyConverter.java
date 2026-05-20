import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class HistoricalCurrencyConverter implements CurrencyConverter {

    private ICurrencyConversion iCurrencyConversion;

    public HistoricalCurrencyConverter(ICurrencyConversion iCurrencyConversion) {
        this.iCurrencyConversion = iCurrencyConversion;
    }

    @Override
    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        final String apiURL = "https://api.freecurrencyapi.com/v1/historical",
                apiKey = "fca_live_RnwtzVgYRA9qlpegCia189guOBgXN2Xqz4cdlAtv";

        // Converts the selected Date instance from the
        // CurrencyConversionHandler into a LocalDate
        // formatted for the API request.
        final LocalDate localDate = iCurrencyConversion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            final String urlString = String.format("%s?apikey=%s&date=%s&base_currency=%s&currencies=%s", apiURL, apiKey, dateString, sourceCurrency, targetCurrency);

            final URL url = new URL(urlString);
            final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Fehler bei der API-Anfrage: " + httpURLConnection.getResponseCode());
                return -1;
            }

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String response = "";
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }

           bufferedReader.close();
           final double exchangeRate = extractExchangeRate(response, targetCurrency);
           return amount * exchangeRate;

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return 0;
    }

    // Extracts and returns the exchange rate from the JSON response.
    private double extractExchangeRate(String data, String targetCurrency) {
        String[] currencySplit  = data.split("\"" + targetCurrency + "\":");
        String[] valueSplit  = currencySplit[1].split("\\}");

        return Double.parseDouble(valueSplit[0]);
    }
}
