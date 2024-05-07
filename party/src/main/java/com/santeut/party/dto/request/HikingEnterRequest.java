package com.santeut.party.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HikingEnterRequest {
    int partyId;
//    int userId;
    int distance;
    int bestHeight;
    LocalDateTime startTime;
}
