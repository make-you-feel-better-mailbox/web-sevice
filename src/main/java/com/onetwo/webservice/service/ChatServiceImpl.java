package com.onetwo.webservice.service;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.properties.PropertiesInfo;
import com.onetwo.webservice.common.uri.ChatServiceURI;
import com.onetwo.webservice.dto.chat.ChatRoomListResponse;
import com.onetwo.webservice.utils.SenderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final PropertiesInfo propertiesInfo;
    private final SenderUtils senderUtils;

    @Override
    public ChatRoomListResponse getChatRoomList(String accessToken) {
        String requestUri = propertiesInfo.getChattingService().getHost();

        requestUri += ChatServiceURI.CHATTING_ROOM;

        Map<String, String> headers = new HashMap<>();
        headers.put(GlobalStatus.ACCESS_TOKEN, accessToken);

        ResponseEntity<ChatRoomListResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        headers,
                        null,
                        new ParameterizedTypeReference<ChatRoomListResponse>() {
                        });

        return response.getBody();
    }
}
