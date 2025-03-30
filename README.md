# Sistema de Reserva de Habitaciones para Hotel

## Descripción
Este es un sistema basado en microservicios para gestionar la reserva de habitaciones en un hotel. Se implementa con **Spring Boot** y **Spring Cloud**, y permite la gestión de usuarios, habitaciones y reservas a través de servicios REST.

## Tecnologías Utilizadas
- **Backend:** Spring Boot, Spring Cloud
- **Servicios:** Eureka, API Gateway, Feign, Resilience4j, Load Balancer
- **Base de Datos:** MySQL

## Arquitectura de Microservicios
El sistema está compuesto por los siguientes microservicios:
- **Reservation Service**: Maneja las reservas de habitaciones.
- **Room Service**: Administra las habitaciones disponibles.
- **User Service**: Gestiona la información de los usuarios.
- **Eureka Server**: Servicio de descubrimiento de microservicios.
- **API Gateway**: Enruta las solicitudes hacia los microservicios correspondientes.

La comunicación entre microservicios se realiza mediante **Feign**, con balanceo de carga proporcionado por **Spring Cloud LoadBalancer**. Además, se implementa **Circuit Breaker** con **Resilience4j** para mejorar la tolerancia a fallos.

---

## Documentación de Endpoints

### Room Controller

**Base Path:** `/room`

| Método | Endpoint | Descripción |
|---------|---------|-------------|
| `GET` | `/room` | Obtiene todas las habitaciones. |
| `GET` | `/room/available` | Obtiene habitaciones disponibles. |
| `GET` | `/room/capacity/{capacity}` | Obtiene habitaciones según capacidad. |
| `GET` | `/room/id/{findId}` | Obtiene una habitación por ID. |
| `POST` | `/room/create` | Crea una nueva habitación. |
| `POST` | `/room/update/available/{markId}/{bool}` | Cambia la disponibilidad de una habitación. |
| `DELETE` | `/room/delete/{deleteId}` | Elimina una habitación por ID. |
| `PUT` | `/room/update` | Actualiza una habitación. |

---

### User Controller

**Base Path:** `/user`

| Método | Endpoint | Descripción |
|---------|---------|-------------|
| `GET` | `/user` | Obtiene todos los usuarios. |
| `GET` | `/user/id/{id}` | Obtiene un usuario por ID. |
| `POST` | `/user/create` | Crea un nuevo usuario. |
| `DELETE` | `/user/delete/{deleteId}` | Elimina un usuario por ID. |
| `PUT` | `/user/edit` | Edita un usuario. |

---

### Reservation Controller

**Base Path:** `/reservation`

| Método | Endpoint | Descripción |
|---------|---------|-------------|
| `GET` | `/reservation` | Obtiene todas las reservas. |
| `GET` | `/reservation/id/{id}` | Obtiene una reserva por ID. |
| `GET` | `/reservation/user/id/{userId}` | Obtiene una reserva según el usuario. |
| `GET` | `/reservation/room/id/{roomId}` | Obtiene una reserva según la habitación. |
| `POST` | `/reservation/create` | Crea una nueva reserva. |
| `DELETE` | `/reservation/delete/{deleteId}` | Elimina una reserva por ID. |
| `PUT` | `/reservation/update` | Actualiza una reserva. |

