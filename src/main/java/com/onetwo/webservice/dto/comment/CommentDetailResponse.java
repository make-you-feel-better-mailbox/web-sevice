package com.onetwo.webservice.dto.comment;

public record CommentDetailResponse(long commentId,
                                    int category,
                                    long targetId,
                                    String userId,
                                    String userNickname,
                                    String userProfileImageEndPoint,
                                    String content,
                                    String createdDate) {
}
