package com.santeut.mountain.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse {

  private final int status;
  private final Object data;
}
