import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor {
    // Definimos el puerto lógico por encima del 1024 [cite: 51, 94]
    private static final int PUERTO = 5000; 
    
    // Lista sincronizada para guardar los flujos de salida de cada cliente conectado
    private static Set<PrintWriter> escritoresClientes = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Iniciando el servidor de mensajería TCP...");
        
        // Creamos el socket "oyente" [cite: 100]
        try (ServerSocket listener = new ServerSocket(PUERTO)) {
            System.out.println("Servidor a la escucha en el puerto " + PUERTO);
            
            while (true) {
                // El servidor se queda esperando conexiones [cite: 100]
                Socket socketCliente = listener.accept(); 
                
                String fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                // Mostramos logs con fecha y hora [cite: 57]
                System.out.println("[" + fechaHora + "] Nuevo cliente conectado desde IP: " + socketCliente.getInetAddress());

                // Delegamos la conexión de este cliente a un nuevo Hilo (Thread) [cite: 108]
                new Thread(new ManejadorCliente(socketCliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Clase interna que implementa la concurrencia para atender a múltiples clientes [cite: 106]
    private static class ManejadorCliente implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Obtenemos los flujos de entrada y salida de datos
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (escritoresClientes) {
                    escritoresClientes.add(out);
                }

                // --- NUEVO: El primer "mensaje" que recibimos es el nombre ---
                String nombreCliente = in.readLine(); 
                String fechaHoraConexion = new SimpleDateFormat("HH:mm:ss").format(new Date());
                System.out.println("[" + fechaHoraConexion + "] El usuario '" + nombreCliente + "' ha entrado al chat.");
                // -------------------------------------------------------------

                String mensaje;
                // El protocolo TCP garantiza la entrega ordenada y fiable de estos mensajes
                while ((mensaje = in.readLine()) != null) {
                    String fechaHora = new SimpleDateFormat("HH:mm:ss").format(new Date());

                    if (mensaje.equalsIgnoreCase("/salir")) {
                        break; 
                    }

                    System.out.println("[" + fechaHora + "] " + nombreCliente + ": " + mensaje);
                    
                    // Retransmitimos el mensaje a todos los clientes conectados usando el nombre real
                    synchronized (escritoresClientes) {
                        for (PrintWriter escritor : escritoresClientes) {
                            // Cambiamos "Cliente dice:" por la variable nombreCliente
                            escritor.println("[" + fechaHora + "] " + nombreCliente + " dice: " + mensaje);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Conexión interrumpida con un cliente.");
            } finally {
                // Control de errores y cierre limpio de conexiones [cite: 57]
                if (out != null) {
                    synchronized (escritoresClientes) {
                        escritoresClientes.remove(out);
                    }
                }
                try {
                    socket.close();
                    System.out.println("Un cliente se ha desconectado.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}