function getChatRoomHtml(chatRoomId, userId, chatUsers, unreadMessageExist, lastMessage, lastMessageTime) {
    const chatRoomTitle = getChatRoomTitle(userId, chatUsers);

    let html = '<a href="#" class="relative flex items-center gap-4 p-2 duration-200 rounded-xl hover:bg-secondery">'
    +    '<div class="relative w-14 h-14 shrink-0">'
    +        '<img th:src="@{/assets/images/avatars/avatar-5.jpg}" alt="" class="object-cover w-full h-full rounded-full"/>'
    +    '</div>'
    +    '<div class="flex-1 min-w-0">'
    +        '<div class="flex items-center gap-2 mb-1.5">'
    +            '<div class="mr-auto text-sm text-black dark:text-white font-medium">'+ chatRoomTitle +'</div>'
    +            '<div class="text-xs font-light text-gray-500 dark:text-white/70">'+ lastMessageTime +'</div>';

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
    } else {
        return chatUsers.find(chatUser => chatUser.userId !== userId).userNickname;
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
            response.chatRoomDetailResponses.forEach(function (element){
                const chatRoomHtml = getChatRoomHtml(element.chatRoomId, userId, element.chatUsers, element.unreadMessageExist, null, null);

                $("#chatRoomList").append(chatRoomHtml);
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