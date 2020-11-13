package daio.apigateway.model

import java.time.LocalDateTime

class Sign (
    var type: String,
    var timeEmitted: LocalDateTime,
    var patient: String,
    var danger: Int, // 0 -> none, 1 -> moderate, 2 -> high
    var value: Double
) {
    override fun toString() = "$patient: [$type] $value ($timeEmitted)"
}