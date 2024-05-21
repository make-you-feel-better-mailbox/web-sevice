package com.onetwo.webservice.service;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.properties.PropertiesInfo;
import com.onetwo.webservice.common.uri.ChatServiceURI;
import com.onetwo.webservice.dto.chat.*;
import com.onetwo.webservice.exception.BadRequestException;
import com.onetwo.webservice.utils.SenderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public ChatMessageDetailsResponse getMessageListByChatRoomId(String chatRoomId, String accessToken) {
        String requestUri = propertiesInfo.getChattingService().getHost();

        requestUri += ChatServiceURI.CHATTING_MESSAGE + "/" + chatRoomId;

        Map<String, String> headers = new HashMap<>();
        headers.put(GlobalStatus.ACCESS_TOKEN, accessToken);

        ResponseEntity<ChatMessageDetailsResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        headers,
                        null,
                        new ParameterizedTypeReference<ChatMessageDetailsResponse>() {
                        });

        return response.getBody();
    }

    @Override
    public RegisterChatRoomResponse registerChatRoom(RegisterChatRoomRequestDto registerChatRoomRequestDto) {
        String requestUri = propertiesInfo.getChattingService().getHost();

        requestUri += ChatServiceURI.CHATTING_ROOT;

        RegisterChatRoomRequest registerChatRoomRequest = new RegisterChatRoomRequest(registerChatRoomRequestDto.getTargetUserIds());

        ResponseEntity<RegisterChatRoomResponse> response =
                senderUtils.send(
                        HttpMethod.POST,
                        requestUri,
                        senderUtils.getAccessTokenHeader(registerChatRoomRequestDto),
                        registerChatRoomRequest,
                        new ParameterizedTypeReference<RegisterChatRoomResponse>() {
                        });

        return response.getBody();
    }

    @Override
    public ChatRoomExistResponse checkChatRoomExist(RegisterChatRoomRequestDto registerChatRoomRequestDto) {
        if (registerChatRoomRequestDto.getTargetUserIds() == null
                || registerChatRoomRequestDto.getTargetUserIds().isEmpty()) throw new BadRequestException("chat target user ids empty or null");

        String requestUri = propertiesInfo.getChattingService().getHost();

        requestUri += ChatServiceURI.CHATTING_ROOT;

        requestUri = createUrlFromRequest(registerChatRoomRequestDto, requestUri);

        ResponseEntity<ChatRoomExistResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        senderUtils.getAccessTokenHeader(registerChatRoomRequestDto),
                        null,
                        new ParameterizedTypeReference<ChatRoomExistResponse>() {
                        });

        return response.getBody();
    }

    public static String createUrlFromRequest(RegisterChatRoomRequestDto request, String baseUrl) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?");

        List<String> targetUserIds = request.getTargetUserIds();
        for (int i = 0; i < targetUserIds.size(); i++) {
            String userId = targetUserIds.get(i);
            urlBuilder.append("targetUserIds=");
            urlBuilder.append(userId);
            if (i < targetUserIds.size() - 1) {
                urlBuilder.append("&");
            }
        }

        return urlBuilder.toString();
    }

    @Override
    public ChatRoomDetailResponse getChatRoomDetail(String chatRoomId, String accessToken) {
        String requestUri = propertiesInfo.getChattingService().getHost();

        requestUri += ChatServiceURI.CHATTING_ROOM_DETAIL + "/" + chatRoomId;

        Map<String, String> headers = new HashMap<>();
        headers.put(GlobalStatus.ACCESS_TOKEN, accessToken);

        ResponseEntity<ChatRoomDetailResponse> response =
                senderUtils.send(
                        HttpMethod.GET,
                        requestUri,
                        headers,
                        null,
                        new ParameterizedTypeReference<ChatRoomDetailResponse>() {
                        });

        return response.getBody();
    }
}
