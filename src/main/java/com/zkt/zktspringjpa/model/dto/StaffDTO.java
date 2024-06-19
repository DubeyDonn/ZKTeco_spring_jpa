package com.zkt.zktspringjpa.model.dto;

import com.zkt.zktspringjpa.model.TableShift;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffDTO {
    private String id;
    private String name;
    private TableShift shift;
}
