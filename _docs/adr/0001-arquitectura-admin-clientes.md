# ADR 0001 - Arquitectura base de la prueba de administración de clientes

## Estado
Aceptado

## Contexto
La prueba técnica solicita una solución fullstack para administrar clientes con estas capacidades:

- listar clientes
- buscar por shared key
- crear clientes
- realizar búsqueda avanzada
- exportar información a CSV

También exige considerar aspectos no funcionales como:

- Angular en frontend
- Java en backend
- validaciones
- logs
- pruebas unitarias
- diseño responsive
- claridad de implementación

## Decisión
Se decidió implementar una arquitectura desacoplada en dos capas principales:

- **FrontEnd Angular**
- **BackEnd Spring Boot**

La persistencia se dejó en memoria en lugar de base de datos, porque el ejercicio lo permitía y el objetivo era priorizar:

- diseño de solución
- separación de responsabilidades
- validaciones
- logging
- pruebas
- facilidad de evaluación

La arquitectura se documentó con el modelo **C4**, en cuatro niveles:

- contexto del sistema
- contenedores
- componentes frontend
- componentes backend

## Motivación
La decisión buscó equilibrar rapidez de implementación, claridad técnica, trazabilidad y mantenibilidad.

La separación entre frontend y backend hace la solución más fácil de explicar, probar y evolucionar.

La persistencia en memoria reduce complejidad en esta fase y deja abierta la posibilidad de incorporar una base de datos más adelante sin rediseñar toda la aplicación.

## Consecuencias positivas
- solución simple de ejecutar y evaluar
- arquitectura clara y desacoplada
- mejor separación entre presentación, negocio y persistencia
- facilidad para agregar pruebas
- facilidad para agregar logging y manejo de errores
- bajo acoplamiento con infraestructura
- facilidad de evolución futura

## Consecuencias asumidas
- la persistencia no es durable entre reinicios
- no se cubren escenarios productivos de concurrencia avanzada
- la solución está optimizada para demo y evaluación, no para producción completa

## Atributos no funcionales considerados

### Observabilidad
Logging de peticiones, duración y eventos de negocio.

### Calidad
Pruebas unitarias y validaciones de entrada.

### Consistencia
Arquitectura por capas y componentes con responsabilidades claras.

### Logueabilidad
Trazabilidad de solicitudes, respuestas y eventos relevantes.

### Mantenibilidad
Separación entre controller, service, repository, DTOs y componentes frontend.

### Testabilidad
Estructura preparada para pruebas unitarias en backend y frontend.

## Alternativas consideradas

### Base de datos relacional
No se eligió porque aumentaba la complejidad del demo y no era obligatoria.

### Solución monolítica sin separación clara
No se eligió porque dificultaba explicar responsabilidades, mantener el código y defender la arquitectura.

## Relación con C4
El modelo C4 se utilizó para conectar la necesidad del usuario con la implementación técnica:

- el **contexto** identifica al administrador como actor principal
- los **contenedores** separan frontend y backend
- los **componentes** muestran la división interna de cada contenedor
- el **código** materializa esa estructura en clases, servicios, DTOs, validaciones y pruebas

## Decisión final
Se adopta una arquitectura frontend/backend desacoplada, persistencia en memoria y documentación mediante C4 + ADR para soportar una entrega funcional, clara y alineada con los requerimientos de la prueba técnica.
