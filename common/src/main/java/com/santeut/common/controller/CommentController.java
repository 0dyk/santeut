package com.santeut.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.santeut.common.common.response.BasicResponse;
import com.santeut.common.common.util.ResponseUtil;
import com.santeut.common.dto.response.CommentListResponseDto;
import com.santeut.common.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/comment")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 개수 가져오기 (READ)
    @GetMapping("/cnt/{postId}/{postType}")
    public ResponseEntity<BasicResponse> getCommentCnt(@PathVariable Integer postId, @PathVariable Character postType) {
        int commentCnt = commentService.getCommentCnt(postId, postType);
        Map<String, Integer> result = new HashMap<>();
        result.put("commentCnt", commentCnt);
        return ResponseUtil.buildBasicResponse(HttpStatus.OK, result);
    }

    // 댓글 쓰기 (CREATE)
    @PostMapping("/{postId}/{postType}")
    public ResponseEntity<BasicResponse> createComment(
            @RequestBody Map<String,String> commentContent,
            @PathVariable Integer postId, @PathVariable Character postType,
            @RequestHeader int userId
            ) throws JsonProcessingException {
        commentService.createComment(postId, postType, commentContent.get("commentContent"), userId);
        return ResponseUtil.buildBasicResponse(HttpStatus.CREATED, "댓글 작성 성공적으로 수행됨.");
    }

    // 특정 게시글의 댓글 리스트 불러오기 ( READ )
    @GetMapping("/{postId}/{postType}")
    public ResponseEntity<BasicResponse> getComments(@PathVariable Integer postId, @PathVariable Character postType) {
        CommentListResponseDto result = commentService.getComments(postId, postType);
        return ResponseUtil.buildBasicResponse(HttpStatus.OK, result);
    }

    // 댓글 수정하기 ( UPDATE )
    @PatchMapping("/{commentId}")
    public ResponseEntity<BasicResponse> updateComment(@PathVariable Integer commentId, @RequestBody Map<String, String> commentContent, @RequestHeader int userId) {
        commentService.updateComment(commentId, commentContent.get("commentContent"), userId);
        return ResponseUtil.buildBasicResponse(HttpStatus.CREATED, "댓글 수정을 성공했습니다.");
    }

    // 댓글 삭제하기 ( DELETE )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<BasicResponse> deleteComment(@PathVariable Integer commentId, @RequestHeader int userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseUtil.buildBasicResponse(HttpStatus.CREATED, "댓글 삭제를 성공했습니다.");
    }
}
