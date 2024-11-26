package com.onetwo.webservice.dto.chat;

import java.util.List;

public record RegisterChatRoomRequest(
        List<String> targetUserIds
) {
}
