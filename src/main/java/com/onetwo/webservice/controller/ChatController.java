package com.onetwo.webservice.controller;

import com.onetwo.webservice.common.GlobalStatus;
import com.onetwo.webservice.common.GlobalURI;
import com.onetwo.webservice.dto.chat.*;
import com.onetwo.webservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(GlobalURI.CHAT_ROOT)
    public ModelAndView getView(@RequestParam(value = GlobalURI.PATH_VARIABLE_CHAT_ROOM_ID, required = false) String chatRoomId) {
        ModelAndView modelAndView = new ModelAndView("main/message");

        modelAndView.addObject("chatRoomId", chatRoomId);

        return modelAndView;
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

    @PostMapping(GlobalURI.CHAT_ROOM)
    @ResponseBody
    public ResponseEntity<RegisterChatRoomResponse> registerChatRoom(@RequestBody RegisterChatRoomRequestDto registerChatRoomRequestDto){
        return ResponseEntity.ok().body(chatService.registerChatRoom(registerChatRoomRequestDto));
    }

    @GetMapping(GlobalURI.CHAT_ROOM)
    @ResponseBody
    public ResponseEntity<ChatRoomExistResponse> checkChatRoomExist(@ModelAttribute RegisterChatRoomRequestDto registerChatRoomRequestDto){
        return ResponseEntity.ok().body(chatService.checkChatRoomExist(registerChatRoomRequestDto));
    }

    @GetMapping(GlobalURI.CHAT_ROOM_DETAIL + GlobalURI.PATH_VARIABLE_CHAT_ROOM_ID_WITH_BRACE + GlobalURI.PATH_VARIABLE_ACCESS_TOKEN_WITH_BRACE)
    @ResponseBody
    public ResponseEntity<ChatRoomDetailResponse> getChatRoomDetail(@PathVariable(GlobalURI.PATH_VARIABLE_CHAT_ROOM_ID) String chatRoomId,
                                                                     @PathVariable(GlobalStatus.ACCESS_TOKEN) String accessToken){
        return ResponseEntity.ok().body(chatService.getChatRoomDetail(chatRoomId, accessToken));
    }
}
