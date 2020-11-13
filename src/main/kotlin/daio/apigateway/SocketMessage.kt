package daio.apigateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import daio.apigateway.model.Sign

class SocketMessage {
    var type: String = ""
    var data: String = ""

    constructor(sign: Sign) {
        this.type = "sign"
        this.data = jacksonObjectMapper().writeValueAsString(sign)
    }

    override fun toString(): String {
        return jacksonObjectMapper().writeValueAsString(this)
    }
}