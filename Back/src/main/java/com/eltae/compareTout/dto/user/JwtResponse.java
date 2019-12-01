package com.eltae.compareTout.dto.user;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JwtResponse {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;
}
