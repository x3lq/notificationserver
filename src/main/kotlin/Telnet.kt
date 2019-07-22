import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.apache.commons.net.telnet.TelnetClient
import java.io.PrintWriter
import java.net.ConnectException

fun Routing.telnet() {
    post("/telnet") {
        val configuration = call.receive<TelnetSettings>()

        print(configuration.toString())

        val telnetClient = TelnetClient()
        telnetClient.connect(configuration.receiverIp, configuration.receiverPort)

        if(telnetClient.isConnected) {
            val printWriter = PrintWriter(telnetClient.outputStream, true)
            printWriter.print(configuration.command)
            printWriter.flush()
        } else {
            throw ConnectException()
        }

        call.respond(mapOf("OK" to true))
    }
}

data class TelnetSettings(val command: String, val receiverIp: String, val receiverPort: Int)