/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cliente.servidor;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;

        try (
            Socket socket = new Socket(host, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            Scanner teclado = new Scanner(System.in)
        ) {
            System.out.println("Conectado al servidor");

            String mensaje;

            while (true) {
                System.out.print("Escribe un mensaje: ");
                mensaje = teclado.nextLine();

                salida.println(mensaje);

                String respuesta = entrada.readLine();
                System.out.println("Servidor: " + respuesta);
            }

        } catch (IOException e) {
            System.out.println("No se pudo conectar al servidor");
        }
    }
}