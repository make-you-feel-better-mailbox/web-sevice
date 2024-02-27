package com.onetwo.webservice.dto.posting;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FilteredPostingResponse {

    private Long postingId;

    private String userId;

    private String content;

    private Boolean mediaExist;

    private String postedDate;
}
