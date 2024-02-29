package com.onetwo.webservice.dto.comment;

public record CommentDetailResponse(long commentId,
                                    int category,
                                    long targetId,
                                    String userId,
                                    String content,
                                    String createdDate) {
}
