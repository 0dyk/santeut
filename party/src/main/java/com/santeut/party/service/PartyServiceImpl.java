package com.santeut.party.service;

import com.santeut.party.common.exception.AccessDeniedException;
import com.santeut.party.common.exception.DataNotFoundException;
import com.santeut.party.dto.request.CreatePartyRequestDto;
import com.santeut.party.dto.request.ModifyPartyRequestDto;
import com.santeut.party.dto.response.GetPartyUserIdResponse;
import com.santeut.party.dto.response.PartyInfoResponseDto;
import com.santeut.party.dto.response.PartyInfoResponseDto.PartyInfo;
import com.santeut.party.entity.Party;
import com.santeut.party.feign.GuildAccessUtil;
import com.santeut.party.repository.PartyRepository;
import com.santeut.party.repository.PartyUserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartyServiceImpl implements PartyService {

  private final PartyRepository partyRepository;
  private final PartyUserRepository partyUserRepository;
  private final PartyUserService partyUserService;
  private final GuildAccessUtil guildAccessUtil;

  @Override
  @Transactional
  public void createParty(int userId, CreatePartyRequestDto requestDto) {
    log.info("소모임 생성 정보: " + requestDto);
    String selectedCourse = Arrays.stream(requestDto.getSelectedCourse())
        .mapToObj(String::valueOf)
        .collect(Collectors.joining("."));

    Party entity = partyRepository.save(Party.createEntity(userId, requestDto, selectedCourse));
    partyUserService.joinUserParty(userId, entity.getPartyId());
  }

  @Override
  @Transactional
  public PartyInfo modifyParty(int userId, int partyId,
      ModifyPartyRequestDto requestDto) {
    log.info("소모임 수정" + requestDto.getPlace() + ", " + requestDto.getSchedule() + ", "
        + requestDto.getPartyName());
    Party entity = partyRepository.findById(partyId)
        .orElseThrow(() -> new DataNotFoundException("[party] 해당 소모임이 존재하지 않습니다"));
    if (entity.getUserId() != userId) {
      log.error("소모임 수정 권한이 없음");
      throw new AccessDeniedException("[party] 소모임 수정 권한이 없습니다");
    }
    entity.modifyPartyInfo(requestDto.getPartyName(), requestDto.getSchedule(),
        requestDto.getPlace(), requestDto.getMaxPeople());
    String guildName = (entity.getGuildId() == null) ? ""
        : guildAccessUtil.getGuildInfo(entity.getGuildId(), userId).getGuildName();
    return PartyInfo.of(
        userId==entity.getUserId()
        ,null
        , entity
        , true
        , guildName);
  }

  @Override
  @Transactional
  public void deleteParty(int userId, int partyId) {
    log.info("소모임 삭제 요청: "+partyId);
    Party party = partyRepository.findById(partyId)
        .orElseThrow(() -> new DataNotFoundException("해당 소모임이 존재하지 않습니다"));
    if(userId != party.getUserId()) {
      throw new AccessDeniedException("소모임 삭제 권한이 없습니다");
    }
    party.deleteParty('I');
    partyUserService.deleteAllPartyUser(partyId, 'I');
  }

  @Override
  public PartyInfoResponseDto findParty(int userId, Integer guildId, String name,
      LocalDate startDate, LocalDate endDate) {
    log.info("소모임 조회 요청: userId, guildId, name" + userId + ", " + guildId + ", " + name);
    List<Party> parties = partyRepository.findPartyWithSearchConditions(userId, guildId, name,
        startDate, endDate);

    // userInfoAccessUtil.getUserInfo(p.getUserId()).getUserNickname()
    return new PartyInfoResponseDto(parties.stream().map(p ->
            PartyInfo.of(p.getUserId()==userId,
                null, p,
                partyUserRepository.existsByUserIdAndPartyId(userId, p.getPartyId()),
                (guildId == null) ? ""
                    : guildAccessUtil.getGuildInfo(guildId, userId).getGuildName()))
        .collect(Collectors.toList()));
  }

  @Override
  public PartyInfo findPartyById(int userId, int partyId) {
    log.info("소모임 조회 요청: partyId=" + partyId);
    Party party = partyRepository.findById(partyId)
        .orElseThrow(() -> new DataNotFoundException("해당 소모임이 존재하지 않습니다"));
//    String owner = userInfoAccessUtil.getUserInfo(party.getUserId()).getUserNickname();
    String guildName = (party.getGuildId() == null) ? ""
        : guildAccessUtil.getGuildInfo(party.getGuildId(), userId).getGuildName();
    boolean isMember = partyUserRepository.existsByUserIdAndPartyId(userId, partyId);
    return PartyInfo.of(userId == party.getUserId(), null, party, isMember, guildName);
  }

  @Override
  public GetPartyUserIdResponse findUserIdById(int partyId) {
    Party party = partyRepository.findById(partyId)
            .orElseThrow(() -> new DataNotFoundException("해당 소모임이 존재하지 않습니다"));
    GetPartyUserIdResponse partyUserInfo=GetPartyUserIdResponse.builder()
            .partyId(partyId)
            .userId(party.getUserId())
            .build();
    return partyUserInfo;
  }
}
