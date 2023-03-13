package com.cbo.core.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageUploadResponse<T> {

	private String message;
    private T response;

}
