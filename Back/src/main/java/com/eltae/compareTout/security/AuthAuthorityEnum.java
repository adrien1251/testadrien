package com.eltae.compareTout.security;

import com.eltae.compareTout.entities.Admin;
import com.eltae.compareTout.entities.Customer;
import com.eltae.compareTout.entities.Supplier;
import com.eltae.compareTout.entities.User;

public enum AuthAuthorityEnum {
    ROLE_ADMIN, ROLE_CUSTOMER, ROLE_SUPPLIER;

    public static AuthAuthorityEnum getRole(User user){
        return user instanceof Admin ? ROLE_ADMIN :
                user instanceof Customer ? ROLE_CUSTOMER :
                        user instanceof Supplier ? ROLE_SUPPLIER : null;
    }
}
