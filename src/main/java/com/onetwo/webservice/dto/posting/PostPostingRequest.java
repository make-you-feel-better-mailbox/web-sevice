package com.onetwo.webservice.dto.posting;

import jakarta.validation.constraints.NotEmpty;

public record PostPostingRequest(@NotEmpty String content,
                                 Boolean mediaExist) {
}
