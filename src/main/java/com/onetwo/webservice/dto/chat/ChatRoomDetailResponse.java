package com.onetwo.webservice.dto.chat;

import java.util.List;

public record ChatRoomDetailResponse(
        String chatRoomId,
        List<ChatUserDetail> chatUsers,
        Boolean unreadMessageExist,
        String createdAt
) {
}
