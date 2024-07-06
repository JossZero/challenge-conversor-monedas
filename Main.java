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

