package daio.apigateway

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class SMPController {
    @MessageMapping("/SMPClient")
    @SendTo("/topic/Dorian")
    @Throws(Exception::class)
    fun greeting(message: String): String {
        Thread.sleep(1000) // simulated delay
        return "MESSAGE RECEIVED"
    }


}