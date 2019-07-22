import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.netty.handler.codec.json.JsonObjectDecoder
import org.apache.commons.beanutils.PropertyUtils
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.apache.commons.configuration2.ex.ConfigurationException
import java.io.File
import kotlin.system.exitProcess

fun Application.main() {
    val configs = Configurations()
    var config : Configuration

    try {
        val file = File( javaClass.classLoader.getResource("user.properties").file)
        config = configs.properties(file)
    } catch (exp: ConfigurationException) {
        println("Error occured while loading configuration file ")
        exp.printStackTrace()
        exitProcess(-1)
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT) // Pretty Prints the JSON
        }
    }
    install(DefaultHeaders)
    install(CallLogging)

    install(StatusPages) {
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    routing {
        email(config)
        telnet()
    }
}

/*fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("MainKT"), module = Application::module).start()
}*/