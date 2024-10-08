package com.santeut.hiking.dto.websocket;

import com.santeut.hiking.common.code.WebSocketCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckDto {
    private String code;
    private String message;

    public CheckDto(WebSocketCode webSocketCode){
        this.code = webSocketCode.getCode();
        this.message = webSocketCode.getMessage();
    }
}
