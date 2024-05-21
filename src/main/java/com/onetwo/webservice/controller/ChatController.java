package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.chat.ChatMessageDetailsResponse;
import com.onetwo.webservice.dto.chat.ChatRoomListResponse;
import com.onetwo.webservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(GlobalURI.CHAT_ROOT)
    public ModelAndView getView() {
        return new ModelAndView("main/message");
    }

    @GetMapping(GlobalURI.CHAT_ROOM + GlobalURI.PATH_VARIABLE_ACCESS_TOKEN_WITH_BRACE)
    @ResponseBody
    public ResponseEntity<ChatRoomListResponse> getChatRoomList(@PathVariable(GlobalStatus.ACCESS_TOKEN) String accessToken){
        return ResponseEntity.ok().body(chatService.getChatRoomList(accessToken));
    }

    @GetMapping(GlobalURI.CHAT_MESSAGE + GlobalURI.PATH_VARIABLE_CHAT_ROOM_ID_WITH_BRACE + GlobalURI.PATH_VARIABLE_ACCESS_TOKEN_WITH_BRACE)
    @ResponseBody
    public ResponseEntity<ChatMessageDetailsResponse> getMessageListByChatRoomId(@PathVariable(GlobalURI.PATH_VARIABLE_CHAT_ROOM_ID) String chatRoomId,
                                                                                 @PathVariable(GlobalStatus.ACCESS_TOKEN) String accessToken){
        return ResponseEntity.ok().body(chatService.getMessageListByChatRoomId(chatRoomId, accessToken));
    }
}
