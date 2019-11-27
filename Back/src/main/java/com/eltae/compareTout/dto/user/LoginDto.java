package com.eltae.compareTout.dto.user;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {
    String email;
    String password;
}
