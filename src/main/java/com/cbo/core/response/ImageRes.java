package com.cbo.core.response;
import com.cbo.core.model.Division;
import com.cbo.core.model.Employee;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ImageRes {
    private byte[] stamp;

    private byte[] signature;
    private int id;
    private Employee employee;

    private Division division;
    private boolean isActive;
}
