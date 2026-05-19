import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LatestCurrencyConverter implements CurrencyConverter {
    // fca_live_RnwtzVgYRA9qlpegCia189guOBgXN2Xqz4cdlAtv
    // https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_RnwtzVgYRA9qlpegCia189guOBgXN2Xqz4cdlAtv
    // https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_RnwtzVgYRA9qlpegCia189guOBgXN2Xqz4cdlAtv&currencies=EUR%2CUSD%2CCAD

    // https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_RnwtzVgYRA9qlpegCia189guOBgXN2Xqz4cdlAtv&currencies=EUR%2CUSD%2CCAD


    @Override
    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        final String apiURL = "https://api.freecurrencyapi.com/v1/latest",
                apiKey = "fca_live_RnwtzVgYRA9qlpegCia189guOBgXN2Xqz4cdlAtv";
        final String urlString = String.format("%s?apikey=%s&base_currency=%s&currencies=%s", apiURL, apiKey, sourceCurrency, targetCurrency);

        try {
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
            System.out.println(response);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return 0;
    }
}
