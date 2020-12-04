package daio.diagnosticmicroservice.model

import java.time.LocalDateTime
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Read (
        var signName: String,
        var timeEmitted: LocalDateTime,
        var patientID: String,
        var value: Double
) {
    override fun toString() = "READ -> $patientID: [$signName] $value ($timeEmitted)"

    fun sanitize() {
        signName = signName.trim()
        timeEmitted = LocalDateTime.now()
        patientID = patientID.trim()
    }

    fun isValid(): Boolean {
        return signName.isNotEmpty() && patientID.isNotEmpty()
    }
}