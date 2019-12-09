package com.eltae.compareTout.dto.admin;

import com.eltae.compareTout.dto.user.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AdminDto extends UserDto implements Cloneable  {

    private String phoneNum;

    public AdminDto clone() throws CloneNotSupportedException {
        return (AdminDto) super.clone();
    }

}