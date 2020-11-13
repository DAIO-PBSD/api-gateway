package daio.apigateway

import com.fasterxml.jackson.databind.ObjectMapper
import daio.apigateway.model.Sign
import org.springframework.stereotype.Controller
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Controller
class SMPHandler: TextWebSocketHandler() {
    var sessionList = HashMap<WebSocketSession, String>()
    var subscribedSMPs = HashMap<String, MutableList<WebSocketSession>>()

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val patientID = sessionList[session] ?: return
        sessionList.remove(session)
        if (subscribedSMPs[patientID]!!.size > 1)
            subscribedSMPs[patientID]!!.remove(session)
        else
            subscribedSMPs.remove(patientID)
        println(sessionList.size)
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json = ObjectMapper().readTree(message.payload)
        // {type: "join/say", data: "name/msg"}
        when (json.get("type").asText()) {
            "subscribe" -> {
                val patientID = json.get("data").asText()
                sessionList[session] = patientID
                if (subscribedSMPs.containsKey(patientID))
                    subscribedSMPs[patientID]!!.add(session)
                else
                    subscribedSMPs[patientID] = mutableListOf(session)
            }
        }
        println(sessionList.size)
        println(json)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("Connected to: $session.id")
    }

    fun sendSigns(sign: Sign) {
        if (subscribedSMPs.containsKey(sign.patient))
            for (socket in subscribedSMPs[sign.patient]!!)
                socket.sendMessage(TextMessage(SocketMessage(sign).toString()))
    }


}