package daio.apigateway.orquestadorInterfacesUsuario

import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate

class EnviadoMensajes() {

    @Autowired
    private lateinit var template: SimpMessagingTemplate

    fun propagateSign(patientId: String, sign: Sign) {
        this.template.convertAndSend("/topic/$patientId", sign)
    }

    fun propagateRead(patientId: String, read: Read) {
        this.template.convertAndSend("/topic/$patientId", read)
    }
}