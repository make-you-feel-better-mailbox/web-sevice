package com.onetwo.webservice.dto.posting;

import jakarta.validation.constraints.NotEmpty;

public record UpdatePostingRequest(@NotEmpty String content,
                                   Boolean mediaExist) {
}
