package com.onetwo.webservice.dto.chat;

import java.util.List;

public record ChatRoomListResponse(List<ChatRoomDetailResponse> chatRoomDetailResponses) {
}
