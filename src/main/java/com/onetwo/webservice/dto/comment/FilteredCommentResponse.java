
package com.onetwo.webservice.dto.comment;

public record FilteredCommentResponse(long commentId,
                                      int category,
                                      long targetId,
                                      String userId,
                                      String userNickname,
                                      String content,
                                      String createdDate) {
}
