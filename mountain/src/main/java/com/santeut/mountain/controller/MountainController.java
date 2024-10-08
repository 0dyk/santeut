package com.santeut.mountain.controller;

import com.santeut.mountain.common.response.BasicResponse;
import com.santeut.mountain.common.response.PagingResponse;
import com.santeut.mountain.common.util.ResponseUtil;
import com.santeut.mountain.dto.request.PartyTrackDataReginRequest;
import com.santeut.mountain.dto.response.CourseInfoResponseDto;
import com.santeut.mountain.service.CourseService;
import com.santeut.mountain.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
public class MountainController {

  private final MountainService mountainService;
  private final CourseService courseService;

  @GetMapping("/")
  public ResponseEntity<BasicResponse> searchMountainByName(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "region", required = false) String region) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK,
        mountainService.findByNameAndRegion(name, region));
  }

  @GetMapping("/popular")
  public ResponseEntity<BasicResponse> getPopularMountain() {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK, mountainService.getMountainByViews());
  }

  @GetMapping("/{mountainId}")
  public ResponseEntity<BasicResponse> getMountainById(
      @PathVariable("mountainId") int mountainId) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK,
        mountainService.findMountainById(mountainId));
  }

  @GetMapping("/v2/{mountainId}")
  public ResponseEntity<BasicResponse> getMountainDetailById(
      @PathVariable("mountainId") int mountainId
  ) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK,
        mountainService.getMountainInfoById(mountainId));
  }

  @GetMapping("/v2/{mountainId}/course")
  public ResponseEntity<BasicResponse> getCourseListInMountain(
      @PathVariable("mountainId") int mountainId
  ) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK,
        courseService.findCourseByMountainId(mountainId));
  }

  @GetMapping("/v2/course/{courseId}")
  public ResponseEntity<BasicResponse> getCourseCoord(
      @PathVariable("courseId") int courseId
  ) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK, courseService.findCourseCoordByCourseId(courseId));
  }

  @GetMapping("/v2/{mountainId}/all-course")
  public ResponseEntity<BasicResponse> getAllCourseOfMountain(
      @PathVariable("mountainId") int mountainId) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK, courseService.findAllCourseByMountainId(mountainId));
  }


  @PostMapping("/course/coordinates")
  public ResponseEntity<BasicResponse> getCourseCoordInCourseId(
      @RequestBody PartyTrackDataReginRequest request
  ) {
    return ResponseUtil.buildBasicResponse(HttpStatus.OK,
        courseService.findCourseCoordsByCourseId(request));
  }

}
