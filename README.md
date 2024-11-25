# Agencia_de_Vehiculos
Este proyecto fue desarrollado como parte del Trabajo Práctico Integrador 2024 en la electiva Backend de Aplicaciones de la carrera de Ingeniería en Sistemas.

Este proyecto implementa funcionalidades para administrar las pruebas, garantizar la seguridad durante las mismas y generar reportes detallados sobre incidentes y estadísticas.

## **Características principales**
- **Gestión de pruebas:** Registro, seguimiento y finalización de pruebas de manejo, con validaciones para licencias vencidas y restricciones de clientes.
- **Control de ubicación:** Monitoreo en tiempo real de los vehículos mediante coordenadas y validación de límites establecidos.
- **Notificaciones automáticas:** Alerta a empleados cuando un vehículo excede el radio permitido o ingresa a zonas peligrosas.
- **Reportes detallados:** Estadísticas de incidentes, kilómetros recorridos y pruebas realizadas.
- **Consumo de servicios externos:** Obtención de configuraciones como zonas peligrosas y radio de operación.
- **Seguridad:** Control de acceso basado en roles mediante **Spring Security**.

## **Tecnologías utilizadas**
- **Lenguaje:** Java 21
- **Framework:** Spring Boot
- **Seguridad:** Spring Security
- **Base de datos:** SQLITE
- **Arquitectura:** Microservicios con un API Gateway
- **Maven:** Para la gestión de dependencias

## **Instalación**
   Clona el repositorio:
   git clone https://github.com/sharonpc/Agencia_de_Vehiculos.git

