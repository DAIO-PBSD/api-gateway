package daio.diagnosticmicroservice.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Sign (
        var name: String = "",
        var extremeLow: Double = 0.0,
        var low: Double = 0.0,
        var high: Double = 0.0,
        var extremeHigh: Double = 0.0
) {
    override fun toString() = "SIGN -> $name: $extremeLow - $low - $high - $extremeHigh"

    fun sanitize() {
        name = name.trim()
    }

    fun isValid(): Boolean {
        return name.isNotEmpty() && extremeLow < low && low < high && high < extremeHigh
    }
}