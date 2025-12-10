package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserJsonPutResponse {
    private String name;
    private String job;
    private String updatedAt;
}
