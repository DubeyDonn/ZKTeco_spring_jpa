package com.zkt.zktspringjpa.model.request;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthRequest {
    private String username;
    private String password;
}
