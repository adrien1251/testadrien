package com.eltae.compareTout.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;
    private UserDto user;
}
