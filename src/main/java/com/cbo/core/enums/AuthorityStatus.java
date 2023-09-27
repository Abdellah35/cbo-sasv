package com.cbo.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorityStatus {

    Active("Active"), Inactive("Inactive"), Delete("Delete");

    private String type;

    public static AuthorityStatus decode(String type) {
        if (type != null && type.length() > 0) {
            for (AuthorityStatus obj : values()) {
                if (obj.getType().equalsIgnoreCase(type)) {
                    return obj;
                }
            }
        }

        return null;
    }
}
