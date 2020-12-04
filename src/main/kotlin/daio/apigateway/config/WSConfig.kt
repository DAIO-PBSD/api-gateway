package daio.apigateway.config

import daio.apigateway.orquestadorEventos.OrqEventos
import daio.apigateway.orquestadorEventos.SolicitudEventos
import daio.apigateway.orquestadorInterfacesUsuario.EnviadoMensajes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@Configuration
@EnableWebSocketMessageBroker
class WSConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS()
    }

    @Bean
    fun orqEventos() = OrqEventos()

    @Bean
    fun solicitudEventos() = SolicitudEventos()

    @Bean
    fun enviadoMensajes() = EnviadoMensajes()
}