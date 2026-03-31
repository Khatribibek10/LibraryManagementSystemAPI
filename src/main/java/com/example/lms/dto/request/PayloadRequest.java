package com.example.lms.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayloadRequest {
    private int page;
    private int size;
}
