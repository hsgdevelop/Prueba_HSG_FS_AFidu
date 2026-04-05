workspace "Prueba Fullstack HSG" "Demo de administración de clientes" {

    !identifiers hierarchical

    model {
        admin = person "Administrador de clientes" "Usuario interno que consulta, busca, crea y exporta clientes desde la interfaz administrativa."

        sistema = softwareSystem "Sistema de Administración de Clientes" "Aplicación web para listar, buscar, crear y exportar clientes." {
            tags "Software System"

            web = container "Frontend Web Angular" "Interfaz web responsive para administración de clientes." "Angular, TypeScript, HTML, CSS" {
                tags "Web Application"

                sidebar = component "SidebarComponent" "Renderiza el menú lateral y deja activa la opción Clients." "Angular Component"
                clientsPage = component "ClientsPageComponent" "Muestra tabla de clientes, búsqueda simple, acciones y apertura de modales." "Angular Component"
                clientFormModal = component "ClientFormModalComponent" "Formulario/modal para creación de clientes y validaciones de captura." "Angular Component"
                advancedSearchModal = component "AdvancedSearchModalComponent" "Formulario/modal para búsqueda avanzada y limpieza de filtros." "Angular Component"
                clientApiService = component "ClientApiService" "Servicio HTTP que consume la API REST del backend." "Angular Service"
            }

            api = container "Backend API Spring Boot" "Expone endpoints REST para administrar clientes y exportar CSV." "Java 17, Spring Boot, Maven" {
                tags "API"

                clientController = component "ClientController" "Expone endpoints REST para listar, crear, buscar y exportar clientes." "Spring REST Controller"
                clientService = component "ClientService" "Implementa la lógica de negocio y coordina validaciones, búsquedas y exportación." "Spring Service"
                inMemoryRepository = component "ClientInMemoryRepository" "Persistencia temporal en memoria para el demo." "Repository"
                sharedKeyGenerator = component "SharedKeyGenerator" "Genera el shared key del cliente cuando aplica la regla de negocio." "Utility"
                requestLoggingFilter = component "RequestLoggingFilter" "Registra peticiones entrantes, duración y correlationId." "Servlet Filter"
                globalExceptionHandler = component "GlobalExceptionHandler" "Centraliza errores y respuestas homogéneas." "Controller Advice"
                dataSeeder = component "DataSeeder" "Carga clientes de prueba al iniciar la aplicación." "Bootstrap Component"
            }
        }

        admin -> sistema "Usa para administrar clientes"
        admin -> sistema.web "Usa desde el navegador"
        sistema.web -> sistema.api "Consume API REST/JSON"

        sistema.web.sidebar -> sistema.web.clientsPage "Navega hacia"
        sistema.web.clientsPage -> sistema.web.clientFormModal "Abre para crear cliente"
        sistema.web.clientsPage -> sistema.web.advancedSearchModal "Abre para búsqueda avanzada"
        sistema.web.clientsPage -> sistema.web.clientApiService "Solicita listado, búsqueda simple, búsqueda avanzada y exportación"
        sistema.web.clientFormModal -> sistema.web.clientApiService "Envía creación de cliente"
        sistema.web.advancedSearchModal -> sistema.web.clientApiService "Envía filtros de búsqueda avanzada"

        sistema.web.clientApiService -> sistema.api.clientController "Invoca endpoints REST"

        sistema.api.requestLoggingFilter -> sistema.api.clientController "Intercepta solicitudes hacia"
        sistema.api.clientController -> sistema.api.clientService "Delega lógica de negocio"
        sistema.api.clientService -> sistema.api.inMemoryRepository "Consulta y guarda clientes"
        sistema.api.clientService -> sistema.api.sharedKeyGenerator "Genera shared key"
        sistema.api.clientController -> sistema.api.globalExceptionHandler "Propaga excepciones a"
        sistema.api.dataSeeder -> sistema.api.inMemoryRepository "Carga datos iniciales"
    }

    views {
        systemContext sistema "SystemContext" {
            include admin
            include sistema
            autolayout lr
            title "Diagrama de Contexto - Sistema de Administración de Clientes"
        }

        container sistema "Containers" {
            include admin
            include sistema.web
            include sistema.api
            autolayout lr
            title "Diagrama de Contenedores - Sistema de Administración de Clientes"
        }

        component sistema.web "WebComponents" {
            include *
            autolayout lr
            title "Diagrama de Componentes - Frontend Angular"
        }

        component sistema.api "ApiComponents" {
            include *
            autolayout lr
            title "Diagrama de Componentes - Backend Spring Boot"
        }

        styles {
            element "Person" {
                background #0b7285
                color #ffffff
                shape person
            }

            element "Software System" {
                background #1971c2
                color #ffffff
            }

            element "Web Application" {
                background #2f9e44
                color #ffffff
            }

            element "API" {
                background #e67700
                color #ffffff
            }

            relationship "Relationship" {
                color #5c7cfa
                thickness 2
            }
        }

        theme default
    }
}