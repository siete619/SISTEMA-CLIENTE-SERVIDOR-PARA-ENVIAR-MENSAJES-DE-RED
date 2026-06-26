# Sistema Cliente-Servidor de Mensajería TCP/IP 

![Java](https://img.shields.io/badge/Java-%23FD1D1D.svg?style=for-the-badge&logo=java&logoColor=white)
![TCP/IP](https://img.shields.io/badge/Protocolo-TCP%2FIP-%23FD1D1D.svg?style=for-the-badge)
![Tailscale](https://img.shields.io/badge/VPN-Tailscale-%23FD1D1D.svg?style=for-the-badge&logo=tailscale&logoColor=white)
![Wireshark](https://img.shields.io/badge/Análisis-Wireshark-%23FD1D1D.svg?style=for-the-badge&logo=wireshark&logoColor=white)

Este repositorio contiene la implementación práctica de un sistema de mensajería en red basado en la arquitectura **Cliente-Servidor**. Fue desarrollado como proyecto para la asignatura de Sistemas Abiertos, demostrando la aplicación empírica del modelo OSI y la suite de protocolos de Internet.

## 📌 Descripción del Proyecto

El sistema está desarrollado íntegramente en Java y utiliza **Sockets** para establecer un canal de comunicación bidireccional y confiable a través del puerto lógico `5000`. Superando las limitaciones de una red local (LAN), el proyecto fue desplegado y probado en un entorno distribuido real utilizando una Red Privada Virtual (VPN) para conectar equipos en diferentes redes Wi-Fi.

## ✨ Características Principales

* **Servidor Concurrente:** Implementación de programación multihilo (`Threads`) que permite al servidor aceptar y gestionar múltiples conexiones de clientes simultáneamente sin bloquear la ejecución.
* **Comunicación Confiable:** Uso estricto del protocolo TCP para garantizar la entrega íntegra y ordenada de los paquetes de datos.
* **Validación Empírica:** El tráfico del sistema fue interceptado y analizado con Wireshark, comprobando en tiempo real el *Three-way handshake* (SYN, SYN-ACK, ACK) y la transmisión del payload en texto plano.
* **Gestión de Sesiones:** Sistema de ingreso por nombre de usuario y cierre limpio de conexiones mediante el comando de escape `/salir`.

## 🛠️ Tecnologías y Herramientas

* **Lenguaje:** Java (JDK)
* **Entorno de Desarrollo:** Apache NetBeans
* **Redes y Enrutamiento:** Tailscale (Overlay Network / VPN)
* **Análisis de Tráfico:** Wireshark

## 👨‍💻 Autores e Institución

Proyecto desarrollado en el **Instituto Tecnológico Superior de Poza Rica (ITSPR)** por:
* Aaron Luna Gonzalez 22isic136
* Luis Enrique Cobos Galindo 22isic138
* Ana Ruth Martinez Ramirez 22isic148

