package com.onetwo.webservice.dto.chat;

import java.util.List;

public record ChatRoomDetailResponse(
        String chatRoomId,
        List<ChatUserDetail> chatUsers,
        LastChatDetail lastChatDetail,
        Boolean unreadMessageExist,
        String createdAt
) {
}
