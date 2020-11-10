package daio.apigateway.service

import daio.apigateway.model.Sign
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/SMP")
class SMPService {
    @PostMapping("/signs")
    fun postSigns(@RequestBody sign: Sign) {
        println(sign)
    }
}