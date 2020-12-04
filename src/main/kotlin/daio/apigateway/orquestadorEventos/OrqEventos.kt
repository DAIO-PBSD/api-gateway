package daio.apigateway.orquestadorEventos

import daio.apigateway.orquestadorInterfacesUsuario.EnviadoMensajes
import daio.diagnosticmicroservice.model.Read
import daio.diagnosticmicroservice.model.Sign
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware


class OrqEventos: ApplicationContextAware {

    private lateinit var solicitudEventos: SolicitudEventos
    private lateinit var enviadoMensajes: EnviadoMensajes

    override fun setApplicationContext(context: ApplicationContext) {
        this.solicitudEventos = context.getBean(SolicitudEventos::class.java)
        this.enviadoMensajes = context.getBean(EnviadoMensajes::class.java)
    }

    fun getPatientSigns(patientId: String): List<Sign>? {
        return this.solicitudEventos.getPatientSigns(patientId)
    }

    fun putPatientSign(patientId: String, sign: Sign) {
        this.solicitudEventos.putPatientSign(patientId, sign)
    }

    fun propagateSign(patientId: String, sign: Sign) {
        this.enviadoMensajes.propagateSign(patientId, sign)
    }

    fun propagateRead(patientId: String, read: Read) {
        this.enviadoMensajes.propagateRead(patientId, read)
    }

    fun normalRead(patientId: String, read: Read) {
        this.propagateRead(patientId, read)
    }

    fun getSourcePage() {
        return this.solicitudEventos.getSourcePage()
    }

    fun raiseAlarm(patientID: String) {
        this.solicitudEventos.raiseAlarm(patientID)
    }

    fun warningRead(patientId: String, read: Read) {
        this.propagateRead(patientId, read)
    }

    fun dangerousRead(patientId: String, read: Read) {
        this.propagateRead(patientId, read)
        raiseAlarm(patientId)
    }
}