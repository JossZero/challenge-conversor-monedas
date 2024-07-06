import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Arrays;

public class ConvertidorMonedas {
    // Lista de monedas disponibles
    public static final List<String> AVAILABLE_CURRENCIES = Arrays.asList(
            "¡Hola estas son algunas de las monedas mas solicitadas por los usuarios!",
            "- USD (Dolar)",
            "- EUR(Euro)",
            "- JPY (Yen Japones)",
            "- CAD (Dolar Canadiense)",
            "- MXN (Peso mexicano)",
            "- BRL (Real brasileño)",
            "- ARS (Peso argentino)",
            "- CLP (Peso chileno)",
            "- COP (Peso colombiano)",
            "- VEF (Bolívar venezolano)"
    );

    public static String getExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        String apiKey = "cdf73cd044b87c8223a6507d";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static JsonObject parseTiposCambio(String jsonResponse) {
        return JsonParser.parseString(jsonResponse).getAsJsonObject();
    }

    public static double getTiposCambio(JsonObject jsonObject, String targetCurrency) {
        return jsonObject.getAsJsonObject("conversion_rates").get(targetCurrency).getAsDouble();
    }

    public static double ConvertirMoneda(double amount, double rate) {
        return amount * rate;
    }
}



