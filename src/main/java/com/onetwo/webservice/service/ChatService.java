package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.chat.ChatMessageDetailsResponse;
import com.onetwo.webservice.dto.chat.ChatRoomListResponse;

public interface ChatService {
    ChatRoomListResponse getChatRoomList(String accessToken);

    ChatMessageDetailsResponse getMessageListByChatRoomId(String chatRoomId, String accessToken);
}
