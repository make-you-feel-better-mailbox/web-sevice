package com.onetwo.webservice.dto.comment;

import jakarta.validation.constraints.NotEmpty;

public record UpdateCommentRequest(@NotEmpty String content) {
}
