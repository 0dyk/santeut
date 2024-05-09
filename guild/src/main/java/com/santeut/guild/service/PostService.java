package com.santeut.guild.service;

import com.santeut.guild.dto.request.PostCreateRequestDto;
import com.santeut.guild.dto.request.GuildPostUpdateRequestDto;
import com.santeut.guild.dto.response.PostListResponseDto;
import com.santeut.guild.dto.response.PostReadResponseDto;

import java.util.List;

public interface PostService {

    void createPost(PostCreateRequestDto postCreateRequestDto, int userId);

    List<PostListResponseDto> getPosts(int guildId, int categoryId, int lastSeenId);

    PostReadResponseDto readPost(int guildPostId, int userId);

    void deletePost(int guildPostId, int userId);

    void updatePost(GuildPostUpdateRequestDto guildPostUpdateRequestDto, int guildPostId, int userId);

}