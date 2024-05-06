package com.santeut.common.service;

import com.santeut.common.common.exception.AccessDeniedException;
//import com.santeut.common.common.util.FcmUtils;
import com.santeut.common.dto.FCMCategory;
import com.santeut.common.dto.FCMRequestDto;
import com.santeut.common.dto.request.AlarmRequestDto;
import com.santeut.common.dto.request.CommonHikingStartFeignRequest;
import com.santeut.common.entity.AlarmEntity;
import com.santeut.common.entity.AlarmTokenEntity;
import com.santeut.common.repository.AlarmRepository;
import com.santeut.common.repository.AlarmTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmTokenRepository alarmTokenRepository;
//    private final FcmUtils fcmUtils;

    public void createAlarm(Integer referenceId, Character referenceType, AlarmRequestDto alarmRequestDto) {
        AlarmEntity alarmEntity = AlarmEntity.builder()
                .userId(alarmRequestDto.getUserId())
                .referenceType(referenceType)
                .referenceId(referenceId)
                .alarmTitle(alarmRequestDto.getAlarmTitle())
                .alarmContent(alarmRequestDto.getAlarmContent()).build();
        alarmRepository.save(alarmEntity);
    }

    public void deleteAlarm(int alarmId) {
        try {
            alarmRepository.deleteById(alarmId);
        }catch (Exception e) {
            log.error("delete alarm error", e);
            throw new AccessDeniedException("삭제할 수 없습니다.");
        }
    }

//    public void sendAlarm(CommonHikingStartFeignRequest hikingStartFeignRequest) {
//        List<AlarmTokenEntity> alarmTokenList = alarmTokenRepository.findByIdIn(hikingStartFeignRequest.getTargetUserIds());
//
//        for (AlarmTokenEntity alarmToken : alarmTokenList) {
//            fcmUtils.sendNotificationByToken(alarmToken, FCMRequestDto.of(hikingStartFeignRequest.getTitle(),
//                    String.format(hikingStartFeignRequest.getMessage()),
//                    FCMCategory.HIKING_START));
//        }
//    }
}