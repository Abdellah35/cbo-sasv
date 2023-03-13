package com.cbo.core.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserSavedResponse<T> {

    private String message;
    private T response;
}
