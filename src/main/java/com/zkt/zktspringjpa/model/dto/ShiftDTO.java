package com.zkt.zktspringjpa.model.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftDTO {
    private String shiftStart;
    private String shiftEnd;
    private String breakStart;
    private String breakEnd;
}
