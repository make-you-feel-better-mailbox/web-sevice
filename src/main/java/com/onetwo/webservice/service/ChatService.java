package com.onetwo.webservice.service;

import com.onetwo.webservice.dto.chat.*;

public interface ChatService {
    ChatRoomListResponse getChatRoomList(String accessToken);

    ChatMessageDetailsResponse getMessageListByChatRoomId(String chatRoomId, String accessToken);

    RegisterChatRoomResponse registerChatRoom(RegisterChatRoomRequestDto registerChatRoomRequestDto);

    ChatRoomExistResponse checkChatRoomExist(RegisterChatRoomRequestDto registerChatRoomRequestDto);

    ChatRoomDetailResponse getChatRoomDetail(String chatRoomId, String accessToken);
}
