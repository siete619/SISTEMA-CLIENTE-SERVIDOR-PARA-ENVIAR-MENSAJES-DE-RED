package cliente.servidor;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    // Dirección IP del servidor
    private static final String HOST = "localhost";

    // Puerto del servidor
    private static final int PUERTO = 5000;

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println(" CLIENTE TCP ");
        System.out.println("=================================");

        try (

            // Conectar al servidor
            Socket socket = new Socket(HOST, PUERTO);

            BufferedReader entrada =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            PrintWriter salida =
                    new PrintWriter(socket.getOutputStream(), true);

        ) {

            System.out.println("Conexion establecida.");
            System.out.println("Servidor: " + HOST);
            System.out.println("Puerto: " + PUERTO);

            // Mostrar mensaje del servidor
            System.out.println("Servidor dice: "
                    + entrada.readLine());

            String mensaje;

            while (true) {

                System.out.print("\nEscribe un mensaje: ");
                mensaje = teclado.nextLine();

                // Enviar mensaje
                salida.println(mensaje);

                // Si escribe salir
                if (mensaje.equalsIgnoreCase("salir")) {

                    System.out.println("Conexion cerrada.");
                    break;
                }

                // Leer respuesta
                String respuesta = entrada.readLine();

                System.out.println("Servidor responde: "
                        + respuesta);
            }

        } catch (UnknownHostException e) {

            System.out.println("No se encontro el servidor.");

        } catch (IOException e) {

            System.out.println("Error de conexion: " + e.getMessage());
        }

        teclado.close();
    }
}