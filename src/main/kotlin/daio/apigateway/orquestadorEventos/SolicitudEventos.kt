package daio.apigateway.orquestadorEventos

import com.fasterxml.jackson.databind.ObjectMapper
import daio.diagnosticmicroservice.model.Sign
import org.springframework.context.ApplicationContextAware
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.net.URI

class SolicitudEventos {

    private val restTemplate: RestTemplate = RestTemplate()
    val mapper: ObjectMapper = ObjectMapper()

    // private val sourcesURI = "http://localhost:8081/"
    private val diagnosticURI = "http://ec2-3-17-180-185.us-east-2.compute.amazonaws.com:8080/events"
    private val attentionURI = "http://ec2-18-222-66-140.us-east-2.compute.amazonaws.com:8080/events"

    fun getPatientSigns(patientId: String): List<Sign>? {
        return this.restTemplate.getForEntity(URI("$diagnosticURI/patients/$patientId/signs"), List::class.java).body?.map { mapper.convertValue(it, Sign::class.java) }
    }

    fun putPatientSign(patientID: String, sign: Sign) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(sign, headers)
        restTemplate.put(URI("$diagnosticURI/patients/${patientID}/signs/${sign.name}"), requestEntity)
    }

    fun raiseAlarm (patientID: String) {
        this.restTemplate.getForEntity(URI("$attentionURI/patients/$patientID/raise-alarm/XXXXXXX-XXXXXXX"), Boolean::class.java)
    }

    fun getSourcePage() {
        // return restTemplate.clientHt
    }
}