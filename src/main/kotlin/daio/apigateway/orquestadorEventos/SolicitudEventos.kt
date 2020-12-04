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

    private val diagnosticURI = "http://localhost:8080/events"

    fun getPatientSigns(patientId: String): List<Sign>? {
        return this.restTemplate.getForEntity(URI("$diagnosticURI/patients/$patientId/signs"), List::class.java).body?.map { mapper.convertValue(it, Sign::class.java) }
    }

    fun putPatientSign(patientID: String, sign: Sign) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(sign, headers)
        restTemplate.put(URI("$diagnosticURI/patients/${patientID}/signs/${sign.name}"), requestEntity)
    }
}