Conversor Monedas
Este proyecto es un conversor de monedas escrito en Java que utiliza la API de ExchangeRate-API para obtener tasas de cambio en tiempo real. El proyecto está diseñado para ejecutarse en la consola y permite a los usuarios ver una lista de monedas disponibles y realizar conversiones entre diferentes monedas.

Características
-Configuración del ambiente Java.
-Consumo de la API para obtener tasas de cambio.
-Análisis de la respuesta JSON utilizando la biblioteca Gson.
-Filtro y exhibición de monedas disponibles.
-Conversión de cantidades entre diferentes monedas.
-Interfaz de usuario en la consola con un menú interactivo.

Requisitos
-Java 11 o superior
-IntelliJ IDEA (opcional pero recomendado)
-Biblioteca Gson

Configuración del Proyecto
1. Clonar el repositorio
https://github.com/JossZero/challenge-conversor-monedas.git

2. Abrir el proyecto en IntelliJ IDEA
-Abre IntelliJ IDEA.
-Selecciona "Open" y navega hasta la carpeta del proyecto.
-Asegúrate de que el SDK de Java 11 o superior esté configurado.

3. Agregar la biblioteca Gson
-Haz clic derecho en el proyecto en el panel izquierdo.
-Selecciona "Open Module Settings".
-Ve a la pestaña "Libraries" y haz clic en el signo "+" en la esquina inferior izquierda.
-Busca y selecciona la biblioteca Gson.

4. Configurar la API Key
- Reemplaza "cdf73cd044b87c8223a6507d" con tu propia API Key de ExchangeRate-API.

Estructura del Código
================================ Clase ConvertidorMonedas ================================
-Esta clase maneja la lógica de interacción con la API de ExchangeRate-API y el procesamiento de los datos JSON obtenidos.

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

================================ Clase Main ================================
Esta clase proporciona la interfaz de usuario, permitiendo a los usuarios ver monedas disponibles y realizar  la conversion que desee.
import java.util.Scanner;
import java.io.IOException;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("==================== Menu: ====================");
            System.out.println("1. Mostrar monedas disponibles");
            System.out.println("2. Acceder al conversor de monedas");
            System.out.println("3. Salir");
            System.out.print(" Selecciona una opción:  ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (choice) {
                case 1:
                    MostrarMonedasDisponibles();
                    break;
                case 2:
                    AccesoConvertidorMonedas(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige de nuevo.");
            }
        }

        scanner.close();
        System.out.println("¡Gracias por usar el conversor de monedas!");
    }

    private static void MostrarMonedasDisponibles() {
        System.out.println("Monedas disponibles:");
        for (String currency : ConvertidorMonedas.AVAILABLE_CURRENCIES) {
            System.out.println(currency);
        }
    }

    private static void AccesoConvertidorMonedas(Scanner scanner) {
        try {
            System.out.print("Introduce la moneda base (ej. USD): ");
            String cantidadBase = scanner.nextLine().toUpperCase();

            System.out.print("Introduce la moneda destino (ej. EUR): ");
            String monedaDeseada = scanner.nextLine().toUpperCase();

            System.out.print("Introduce la cantidad a convertir: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el buffer

            String jsonResponse = ConvertidorMonedas.getExchangeRates(cantidadBase);
            JsonObject jsonObject = ConvertidorMonedas.parseTiposCambio(jsonResponse);

            double rate = ConvertidorMonedas.getTiposCambio(jsonObject, monedaDeseada);
            double cantidadConvertida = ConvertidorMonedas.ConvertirMoneda(amount, rate);

            System.out.printf("La cantidad convertida es: %.2f %s%n", cantidadConvertida, monedaDeseada);

        } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener las tasas de cambio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}

Ejecución del Proyecto
-Asegúrate de que el proyecto esté configurado correctamente en IntelliJ IDEA.
-Ejecuta la clase Main como una aplicación Java.
-Sigue las instrucciones en la consola para interactuar con el menú y realizar conversiones de monedas.
