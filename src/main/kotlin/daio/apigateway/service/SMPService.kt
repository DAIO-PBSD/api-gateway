package daio.apigateway.service

import daio.apigateway.model.Sign
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/SMP")
class SMPService (private val template: SimpMessagingTemplate) {

    @PostMapping("/signs")
    fun postSigns(@RequestBody sign: Sign) {
        this.template.convertAndSend("/topic/${sign.patient}", sign)
        println(sign)
    }
}