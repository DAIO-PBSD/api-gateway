package daio.apigateway.orquestadorInterfacesUsuario

import daio.apigateway.orquestadorEventos.OrqEventos
import daio.diagnosticmicroservice.model.Sign
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api")
class ServicioUsuarios: ApplicationContextAware {

    private lateinit var orqEventos: OrqEventos

    override fun setApplicationContext(context: ApplicationContext) {
        this.orqEventos = context.getBean(OrqEventos::class.java)
    }

    @GetMapping("/patients/{patient_id}/signs")
    fun getPatientSigns (@PathVariable patient_id: String): List<Sign>? {
        return orqEventos.getPatientSigns(patient_id)
    }

    @PutMapping("/patients/{patient_id}/signs/{sign_name}")
    fun putPatientSign(@PathVariable patient_id: String, @PathVariable sign_name: String, @RequestBody sign: Sign) {
        sign.name = sign_name
        sign.sanitize()
        if (!sign.isValid())
            return

        orqEventos.putPatientSign(patient_id, sign)
    }

    @GetMapping
    fun getSourceQuery () {
        return this.orqEventos.getSourcePage()
    }

}