package org.entando.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockModel {

    private String name;
    private String familyName;
    private boolean active;
    private int age;

}
