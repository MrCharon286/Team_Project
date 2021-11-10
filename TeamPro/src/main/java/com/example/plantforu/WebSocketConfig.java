package com.example.plantforu;

import org.springframework.context.annotation.*;
import org.springframework.web.socket.config.annotation.*;

import com.example.plantforu.websocket.*;

import lombok.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private final MessageWebSocketHandler handler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, "/web/socket").setAllowedOrigins("*");
	}
}	
