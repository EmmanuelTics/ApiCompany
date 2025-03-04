// Application.kt
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.routing.*
import routes.employeeRoutes // Importa las rutas de employees
import routes.userRoutes // Importa las rutas de employees
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.*

fun main() {
    embeddedServer(Netty, host = "0.0.0.0", port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    // Configuración de la negociación de contenido para JSON
    install(ContentNegotiation) {
        json() // Serialización/Deserialización de JSON
    }
  install(CORS) {

    anyHost() // Permite solicitudes desde cualquier host (origen)

    // Configura los métodos permitidos
    allowMethod(HttpMethod.Get)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Delete)

    // Configura los encabezados permitidos
    allowHeader(HttpHeaders.ContentType)
    allowHeader(HttpHeaders.Authorization)
    allowHeader(HttpHeaders.Accept)

    // Habilita el envío de credenciales si es necesario
    allowCredentials = true
}
 
   
    


    // Manejo global de excepciones
    install(StatusPages) {
        exception<Throwable> { call, cause -> 
            call.respond(HttpStatusCode.InternalServerError, cause.message ?: "Unknown error")
        }
    }

    // Llamada a las rutas de employees
    routing {
        employeeRoutes()
         userRoutes()  // Aquí se llaman las rutas definidas en RoutesEmployees.kt
    }
}
