let chatRoomWebSocket = null;

function getChatRoomHtml(chatRoomId, userId, chatUsers, unreadMessageExist, lastChat) {
    const chatRoomTitle = getChatRoomTitle(userId, chatUsers);
    const chatRoomProfileImageEndPoint = getChatRoomProfileImageEndPoint(userId, chatUsers);

    const lastMessage = lastChat.chatExist ? lastChat.lastChatMessage : "You haven't started message yet!";
    const lastChatDate = instantStringToLocalDateTime(lastChat.lastChatDate);

    let html = '<a href="#" onclick="openChatRoom(\''+ chatRoomId +'\')" class="relative flex items-center gap-4 p-2 duration-200 rounded-xl hover:bg-secondery">'
    +    '<div class="relative w-14 h-14 shrink-0">'
    +        '<img src="'+ chatRoomProfileImageEndPoint +'" alt="" class="object-cover w-full h-full rounded-full"/>'
    +    '</div>'
    +    '<div class="flex-1 min-w-0">'
    +        '<div class="flex items-center gap-2 mb-1.5">'
    +            '<div class="mr-auto text-sm text-black dark:text-white font-medium">'+ chatRoomTitle +'</div>'
    +            '<div class="text-xs font-light text-gray-500 dark:text-white/70">'+ lastChatDate +'</div>';

    if (unreadMessageExist) html += '<div class="w-2.5 h-2.5 bg-blue-600 rounded-full dark:bg-slate-700"></div>';

    html += '</div>'
    +        '<div class="font-medium overflow-hidden text-ellipsis text-sm whitespace-nowrap">'+ lastMessage
    +        '</div>'
    +    '</div>'
    +'</a>';

    return html;
}

function getChatRoomTitle(userId, chatUsers){
    if (chatUsers.length > 2) {
        return chatUsers.map(chatUser => chatUser.userNickname).join(', ');
    } else if (chatUsers.length == 1){
        return "나와의 채팅";
    } else {
        return chatUsers.find(chatUser => chatUser.userId !== userId).userNickname;
    }
}

function getChatRoomProfileImageEndPoint(userId, chatUsers) {
    if (chatUsers.length > 2) {
        return defaultUserProfileImageEndPoint;
    } else if (chatUsers.length == 1){
        let profileEndPoint = chatUsers.find(chatUser => chatUser.userId === userId).userProfileImageEndPoint;

        return getUserProfileEndPoint(profileEndPoint);
    } else {
        let profileEndPoint =  chatUsers.find(chatUser => chatUser.userId !== userId).userProfileImageEndPoint;

        return getUserProfileEndPoint(profileEndPoint);
    }
}

function getChatRoomList(){
    let accessToken = window.localStorage.getItem(accessTokenString);

    let userId = window.localStorage.getItem(userIdString);

    $.ajax({
        url: chatRoomUri + "/" + accessToken,
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            $("#chatRoomList").empty();

            response.chatRoomDetailResponses.forEach(function (element){
                const chatRoomHtml = getChatRoomHtml(element.chatRoomId, userId, element.chatUsers, element.unreadMessageExist, element.lastChatDetail);

                $("#chatRoomList").append(chatRoomHtml);
            });

            if(isFirstLoadPage){
                if (requestChatRoomId != null && requestChatRoomId !== ""){
                    openChatRoom(requestChatRoomId)
                } else if (response.chatRoomDetailResponses.length > 0) {
                    openChatRoom(response.chatRoomDetailResponses[0].chatRoomId)
                }

                isFirstLoadPage = false;
            }
        },
        complete: function(response){
        },
        error: function(response){
            let errorModal = $("#errorModal");

            UIkit.modal(errorModal).show();
        }
    });
}

function connectionChatRoomWebSocket(chatRoomId){
    const webSocketUri = 'ws://localhost:9000/chatting-service/message/' + chatRoomId

    const websocket = new WebSocket(webSocketUri);

    if (websocket.readyState === 1) {
        websocket.close();
    }

    websocket.onmessage = onMessage;
    websocket.onopen = onOpen;
    websocket.onclose = () => onClose(chatRoomId);

    chatRoomWebSocket = websocket;
}

function send() {
    let message = document.getElementById("message");

    let messageObject = {
        "senderId" : window.localStorage.getItem(userIdString),
        "message" : message.value
    }

    chatRoomWebSocket.send(JSON.stringify(messageObject));
    message.value = '';
}

let retryInterval;
const retryDelay = 100;

function onClose(chatRoomId) {
    retryInterval = setInterval(() => {
        console.log('Reconnecting...');
        connectionChatRoomWebSocket(chatRoomId); // 이전의 채팅방 ID를 전달하여 다시 연결 시도
    }, retryDelay);
}

function onOpen() {
    clearInterval(retryInterval);
}

function onMessage(msg) {
    let data = JSON.parse(msg.data);

    console.log(data);

    let userId = data.senderId;
    let message = data.message;
    let userProfileEndPoint = data.userProfileImageEndPoint;

    let html = "";

    const isSender = userId === window.localStorage.getItem(userIdString);

    let now = new Date();

    if(isSender) {
        html = getSentMessageBox(message, now.toISOString(), getUserProfileEndPoint(userProfileEndPoint));
    } else {
        html = getReceivedMessageBox(message, now.toISOString(), userId, getUserProfileEndPoint(userProfileEndPoint));
    }

    $('#chatBoxArea').append(html);

    $("#chatRoomArea").scrollTop($("#chatRoomArea").prop('scrollHeight'));
}

function getReceivedMessageBox(message, sendDateString, senderId, userProfileEndPoint) {
    const sendDate = instantStringToLocalDateTime(sendDateString);

    let html = '<div class="flex gap-3">'
    +   '<img src="'+ userProfileEndPoint +'" alt="" class="w-9 h-9 rounded-full shadow" onclick="moveToFeedDetail(\''+ senderId +'\')"/>'
    +    '<div class="px-4 py-2 rounded-[20px] max-w-sm bg-secondery">' + message
    +    '</div>'
    + '<div class="text-xs font-light text-gray-500 dark:text-white/70" style="margin-top: auto;">'+ sendDate +'</div>'
    +'</div>';
    return html;
}

function getSentMessageBox(message, sendDateString, profileImageEndPoint) {
    const sendDate = instantStringToLocalDateTime(sendDateString);

    let html = '<div class="flex gap-2 flex-row-reverse items-end">'
        + '<img src="'+ profileImageEndPoint +'" alt="" class="w-4 h-4 rounded-full shadow">'
        + '<div class="px-4 py-2 rounded-[20px] max-w-sm bg-gradient-to-tr from-sky-500 to-blue-500 text-white shadow">' + message
        + '</div>'
        + '<div class="text-xs font-light text-gray-500 dark:text-white/70">'+ sendDate +'</div>'
        + '</div>';
    return html;
}

function moveToFeedDetail(userId){
    location.href = feedDetailUri + '/' + userId
}

function openChatRoom(chatRoomId) {
    checkTokenExpired();

    if (chatRoomWebSocket != null) chatRoomWebSocket.close();

    $('#chatBoxArea').empty();

    getChatRoomDetail(chatRoomId);
    getChatMessages(chatRoomId);

    connectionChatRoomWebSocket(chatRoomId);
}

function getChatMessages(chatRoomId){
    let accessToken = window.localStorage.getItem(accessTokenString);

    $.ajax({
        url: chatMessageUri + "/" + chatRoomId + "/" + accessToken,
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            response.chatMessageDetails.forEach(function (element){
                const chatMessageBoxHtml = getChatMessageBoxHtml(element, window.localStorage.getItem(userIdString));

                $('#chatBoxArea').prepend(chatMessageBoxHtml);

                $("#chatRoomArea").scrollTop($("#chatRoomArea").prop('scrollHeight'));
            });
        },
        complete: function(response){
        },
        error: function(response){
            let errorModal = $("#errorModal");

            UIkit.modal(errorModal).show();
        }
    });
}

function getChatMessageBoxHtml(chatMessageDetail, userId){
    const isSender = chatMessageDetail.senderId === userId;

    let html = "";

    if(isSender) {
        html = getSentMessageBox(chatMessageDetail.message, chatMessageDetail.createdAt, getUserProfileEndPoint(chatMessageDetail.userProfileEndPoint));
    } else {
        html = getReceivedMessageBox(chatMessageDetail.message, chatMessageDetail.createdAt, chatMessageDetail.senderId, getUserProfileEndPoint(chatMessageDetail.userProfileEndPoint));
    }

    return html;
}

function getChatRoomDetail(chatRoomId){
    $.ajax({
        url: chatRoomDetailUri + "/" + chatRoomId + "/" +window.localStorage.getItem(accessTokenString),
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            let requestUserId = window.localStorage.getItem(userIdString);

            const chatRoomTitle = getChatRoomTitle(requestUserId, response.chatUsers);
            const chatRoomImageFileEndPoint = getChatRoomProfileImageEndPoint(requestUserId, response.chatUsers);

            $("#chatRoomTitle").text(chatRoomTitle)
            $("#chatRoomProfileImage").attr("src", chatRoomImageFileEndPoint);
        },
        complete: function(response){
        },
        error: function(response){
            let errorModal = $("#errorModal");

            UIkit.modal(errorModal).show();
        }
    });
}