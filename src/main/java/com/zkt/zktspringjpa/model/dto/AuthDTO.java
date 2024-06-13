package com.zkt.zktspringjpa.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthDTO {
    private String token;
    private SystemUserDTO user;
}
