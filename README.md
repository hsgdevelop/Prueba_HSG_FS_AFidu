# Prueba HSG Fullstack  -  Administración de Clientes AFidu

## Resumen ejecutivo

Este proyecto es un demo fullstack desarrollado para resolver una prueba técnica de diseño e implementación de una solución de **Administración de Clientes**.

La solución permite:

- listar clientes
- buscar por **shared key**
- crear nuevos clientes
- realizar **búsqueda avanzada**
- exportar información a **CSV**

Se construyó con una arquitectura desacoplada:

- **Frontend:** Angular 19
- **Backend:** Java 17 + Spring Boot
- **Persistencia:** almacenamiento en memoria

La decisión de no usar base de datos en esta versión fue intencional, ya que el ejercicio permitía simplificar la persistencia y el objetivo principal era demostrar:

- diseño de solución
- separación de responsabilidades
- validaciones
- logs
- pruebas unitarias
- claridad arquitectónica

---

## Alcance funcional

La opción implementada del menú lateral es únicamente **Clients**.

### Funcionalidades incluidas
- listado de clientes
- búsqueda simple por `sharedKey`
- creación de cliente
- búsqueda avanzada
- exportación CSV

### Funcionalidades fuera de alcance
Las demás opciones del menú lateral son visuales y no forman parte del alcance funcional del demo.

---

## Arquitectura

Para estructurar la solución se tomó como referencia el **modelo C4**, ya que permite explicar la arquitectura desde el contexto del usuario hasta los componentes internos del sistema de forma clara y ordenada.

La solución quedó organizada en:

- **Contexto del sistema**
![Contexto del sistema](_docs/images/contexto-del-sistema.png)
- **Contenedores**
![Contenedores](_docs/images/contenedores.png)
- **Componentes BackEnd**
![Contenedores](_docs/images/componentes-backend.png)


---

## Stack técnico

### Backend
- Java 17
- Spring Boot 4.19.0
- Maven
- Bean Validation
- SLF4J / Logback
- JUnit 5

### Frontend
- Angular 19
- TypeScript
- HTML
- CSS

---

## Ejecución del proyecto

### Backend
```bash
mvn spring-boot:run