package com.eltae.compareTout.utils;

import com.eltae.compareTout.security.AuthAuthorityEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UtilsAuth {

    private static List<? extends GrantedAuthority> getActualUserAuthorities() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return Arrays.asList(((UserDetails) principal).getAuthorities().toArray(new GrantedAuthority[0]));
        }

        return new ArrayList<>();
    }

    public static boolean actualUserHaveAuthorities(AuthAuthorityEnum... authAuthorities) {
        List<GrantedAuthority> grantedAuths =
                AuthorityUtils.commaSeparatedStringToAuthorityList(Arrays.stream(authAuthorities).map(Enum::name).collect(Collectors.joining(",")));

        return getActualUserAuthorities().containsAll(grantedAuths);
    }
}
