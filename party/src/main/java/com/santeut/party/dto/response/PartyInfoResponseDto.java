package com.santeut.party.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.santeut.party.entity.Party;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyInfoResponseDto {

  public List<PartyInfo> partyList;

  @Data
  @Builder
  @JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
  public static class PartyInfo {

    public int partyId;
    public Integer partyUserId;
    public String partyName;
    public String guildName;
    public String schedule;
    public String mountainName;
    public String place;
    public int maxPeople;
    public int curPeople;
    public Boolean isOwner;
    public char status;
    public Boolean isMember;

    public static PartyInfo of(Boolean isOwner, Integer partyUserId, Party entity,
        Boolean isMember, String guildName) {
      return PartyInfo.builder()
          .partyId(entity.getPartyId())
          .partyUserId(partyUserId)
          .partyName(entity.getPartyName())
          .guildName(guildName)
          .schedule(entity.getSchedule().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
          .mountainName(entity.getMountainName())
          .place(entity.getPlace())
          .maxPeople(entity.getMaxParticipants())
          .curPeople(entity.getParticipants())
          .isOwner(isOwner)
          .status(entity.getStatus())
          .isMember(isMember)
          .build();
    }
  }
}
