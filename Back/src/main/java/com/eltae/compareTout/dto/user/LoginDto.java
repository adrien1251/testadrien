package com.eltae.compareTout.dto.user;

import lombok.*;

import java.io.Serializable;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

    private String email;
    private String password;
}
