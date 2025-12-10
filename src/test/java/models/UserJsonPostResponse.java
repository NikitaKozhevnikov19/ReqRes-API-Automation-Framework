package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserJsonPostResponse {
    private String id;
    private String name;
    private String job;
    private String createdAt;
}
