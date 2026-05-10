package cliente.servidor;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {

    // Puerto de comunicación
    private static final int PUERTO = 5000;

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" SERVIDOR TCP INICIADO ");
        System.out.println("=================================");

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {

            // Mostrar información del servidor
            System.out.println("Puerto utilizado: " + PUERTO);
            System.out.println("IP del servidor: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Esperando conexiones...\n");

            // El servidor queda escuchando siempre
            while (true) {

                // Espera conexión del cliente
                Socket cliente = servidor.accept();

                System.out.println("=================================");
                System.out.println(" NUEVO CLIENTE CONECTADO ");
                System.out.println("IP Cliente: " + cliente.getInetAddress());
                System.out.println("Puerto Cliente: " + cliente.getPort());
                System.out.println("Hora: " + obtenerHora());
                System.out.println("=================================\n");

                // Crear hilo para atender cliente
                HiloCliente hilo = new HiloCliente(cliente);
                hilo.start();
            }

        } catch (IOException e) {

            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Método para obtener hora actual
    public static String obtenerHora() {

        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(new Date());
    }
}

// Clase para manejar múltiples clientes
class HiloCliente extends Thread {

    private Socket socket;

    public HiloCliente(Socket socket) {

        this.socket = socket;
    }

    @Override
    public void run() {

        try (

            BufferedReader entrada =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            PrintWriter salida =
                    new PrintWriter(socket.getOutputStream(), true);

        ) {

            // Mensaje inicial
            salida.println("Conexion establecida con el servidor.");

            String mensaje;

            // Leer mensajes continuamente
            while ((mensaje = entrada.readLine()) != null) {

                // Validar mensaje vacío
                if (mensaje.trim().isEmpty()) {

                    salida.println("No se permiten mensajes vacios.");
                    continue;
                }

                // Comando para salir
                if (mensaje.equalsIgnoreCase("salir")) {

                    System.out.println("Cliente desconectado: "
                            + socket.getInetAddress());

                    salida.println("Conexion finalizada.");
                    break;
                }

                // Mostrar mensaje recibido
                System.out.println("[" + Servidor.obtenerHora() + "] "
                        + socket.getInetAddress()
                        + " dice: " + mensaje);

                // Respuesta al cliente
                salida.println("Servidor recibio correctamente el mensaje: "
                        + mensaje);
            }

        } catch (IOException e) {

            System.out.println("Cliente desconectado inesperadamente.");
        }

        // Cerrar socket
        try {

            socket.close();

        } catch (IOException e) {

            System.out.println("Error al cerrar conexion.");
        }
    }
}