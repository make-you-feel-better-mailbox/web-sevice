package com.onetwo.webservice.dto.chat;

import java.time.Instant;
import java.util.List;

public record ChatRoomDetailResponse(
        String chatRoomId,
        List<String> chatUsers,
        Boolean unreadMessageExist,
        Instant createdAt
) {
}
