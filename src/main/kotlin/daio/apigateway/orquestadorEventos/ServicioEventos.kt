package daio.apigateway.orquestadorEventos

import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/events")
class ServicioEventos (private val template: SimpMessagingTemplate): ApplicationContextAware {

    private lateinit var orqEventos: OrqEventos

    override fun setApplicationContext(context: ApplicationContext) {
        this.orqEventos = context.getBean(OrqEventos::class.java)
    }

    @PostMapping("/reads")
    fun postRead(@RequestBody read: Read) {
        println("Propagando read $read")
        this.orqEventos.propagateRead(read.patientID, read)
    }

    @PostMapping("/reads/warning")
    fun postWarningRead(@RequestBody read: Read) {
        println("warning")
    }

    @PostMapping("/reads/danger")
    fun postDangerRead(@RequestBody read: Read) {
        println("danger")
    }

    @PostMapping("/patients/{patient_id}/signs/{sign_name}")
    fun postSign(@PathVariable patient_id: String, @PathVariable sign_name: String, @RequestBody sign: Sign) {
        sign.name = sign_name
        sign.sanitize()
        if (!sign.isValid())
            return

        this.orqEventos.propagateSign(patient_id, sign)
    }
}