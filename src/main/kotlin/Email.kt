import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.apache.commons.configuration2.Configuration
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

fun Routing.email(config : Configuration) {
    post("/sendEmail") {
        val email = call.receive<EMail>()

        SimpleEmail().apply {
            hostName = config.getString("hostName")
            setSmtpPort(config.getInt("smtpPort"))
            setAuthenticator(DefaultAuthenticator(config.getString("email"), config.getString("pwd")))
            setSSLOnConnect(true)
            setFrom(config.getString("email"))
            setSubject(email.subject)
            setMsg(email.message)
            addTo(config.getStringArray("sendTo"), this)
        }.send()

        call.respond(mapOf("OK" to true))
    }
}

fun addTo(receivers: Array<String>, simpleEmail: SimpleEmail) {
    var first = true
    for(address in receivers) {
        if(first) {
            first = false
            simpleEmail.addTo(address)
        } else {
            simpleEmail.addCc(address)

        }
    }
}

data class EMail(val subject: String, val message: String)
