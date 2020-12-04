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

    @PostMapping("/patients/{patient_id}/reads/normal")
    fun postNormalRead(@PathVariable patient_id: String, @RequestBody read: Read) {
        this.orqEventos.normalRead(patient_id, read)
    }

    @PostMapping("/patients/{patient_id}/reads/warning")
    fun postWarningRead(@PathVariable patient_id: String, @RequestBody read: Read) {
        this.orqEventos.warningRead(patient_id, read)
    }

    @PostMapping("/patients/{patient_id}/reads/danger")
    fun postDangerRead(@PathVariable patient_id: String, @RequestBody read: Read) {
        this.orqEventos.dangerousRead(patient_id, read)
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