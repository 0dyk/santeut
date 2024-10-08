package com.santeut.auth.service;


import com.santeut.auth.dto.request.HikingRecordRequest;
import com.santeut.auth.dto.request.PartyMemberInfoRequest;
import com.santeut.auth.dto.request.UpdatePasswordRequest;
import com.santeut.auth.dto.request.UpdateProfileImageRequest;
import com.santeut.auth.dto.request.UpdateProfileRequest;
import com.santeut.auth.dto.response.GetMountainRecordResponse;
import com.santeut.auth.dto.response.GetPartyMemberInfoResponse;
import com.santeut.auth.dto.response.GetMypageProfileResponse;
import com.santeut.auth.dto.response.GetUserInfoResponse;
import com.santeut.auth.dto.response.GetUserLevelResponse;
import com.santeut.auth.entity.UserEntity;
import java.util.List;
import org.hibernate.sql.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    GetUserInfoResponse userLoginInfo(String userId);
    GetUserInfoResponse userInfo(int userId);
    void updatePassword(String userLoginId, UpdatePasswordRequest request);
    void updateProfile(String userLoginId, UpdateProfileRequest request, MultipartFile multipartFile);
    void updateProfileImage(String userLoginId, MultipartFile multipartFile);
    GetMypageProfileResponse getMypageProfile(String userLoginId);
    GetUserLevelResponse getLevel(String userLoginId);
    GetMountainRecordResponse getMountainRecord(String userLoginId);
    void patchMountainRecord(HikingRecordRequest request);
    GetPartyMemberInfoResponse getPartyMemberInfo(PartyMemberInfoRequest requestDto);
}
