import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    // Dirección IP privada y número de puerto a utilizar [cite: 51, 90]
    private static final String IP_SERVIDOR = "100.111.142.111"; // Cambiar por la IP del equipo servidor en la red local
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        // Interfaz por consola [cite: 50]
        System.out.println("Intentando conectar al servidor en " + IP_SERVIDOR + ":" + PUERTO);

        // Se inicializa el socket del cliente para establecer la conexión [cite: 102]
        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("¡Conexión TCP establecida con éxito!");
            
            // --- NUEVO: Pedir el nombre antes de empezar a chatear ---
            System.out.print("Por favor, ingresa tu nombre de usuario: ");
            String miNombre = scanner.nextLine();
            out.println(miNombre); // Enviamos el nombre como el primer dato al servidor
            // ---------------------------------------------------------
            
            System.out.println("Escribe tus mensajes. Escribe '/salir' para desconectarte.");

            // Hilo dedicado a leer constantemente las respuestas que envía el servidor
            Thread hiloLectura = new Thread(() -> {
                String mensajeEntrante;
                try {
                    while ((mensajeEntrante = in.readLine()) != null) {
                        System.out.println("\n" + mensajeEntrante);
                    }
                } catch (IOException e) {
                    System.out.println("\nSe ha cerrado la conexión con el servidor.");
                }
            });
            hiloLectura.start();

            // Bucle principal para enviar mensajes de texto al servidor [cite: 50]
            while (true) {
                String mensaje = scanner.nextLine();
                out.println(mensaje);
                
                // Aplicación de comandos especiales [cite: 57]
                if (mensaje.equalsIgnoreCase("/salir")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor. Verifica que esté en ejecución o que la IP sea correcta.");
        }
    }
}