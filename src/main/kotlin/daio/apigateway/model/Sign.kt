package daio.apigateway.model

import java.time.LocalDateTime

class Sign (
    var type: String,
    var timeEmitted: LocalDateTime,
    var patient: String,
    var value: Double
) {
    override fun toString() = "$patient: [$type] $value ($timeEmitted)"
}