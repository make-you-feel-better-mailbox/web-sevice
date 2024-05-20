function getChatRoomHtml(chatRoomId, userId, chatUserIds, unreadMessageExist, lastMessage, lastMessageTime) {
    const chatRoomTitle = getChatRoomTitle(userId, chatUserIds);

    let html =
     '<a href="#" className="relative flex items-center gap-4 p-2 duration-200 rounded-xl hover:bg-secondery">'
    +    '<div className="relative w-14 h-14 shrink-0">'
    +        '<img th:src="@{/assets/images/avatars/avatar-5.jpg}" alt="" className="object-cover w-full h-full rounded-full"/>'
    +        '<div className="w-4 h-4 absolute bottom-0 right-0  bg-green-500 rounded-full border border-white dark:border-slate-800"></div>'
    +    '</div>'
    +    '<div className="flex-1 min-w-0">'
    +        '<div className="flex items-center gap-2 mb-1.5">'
    +            '<div className="mr-auto text-sm text-black dark:text-white font-medium">'+ chatRoomTitle +'</div>'
    +            '<div className="text-xs font-light text-gray-500 dark:text-white/70">'+ lastMessageTime +'</div>';

    if (unreadMessageExist) html += '<div class="w-2.5 h-2.5 bg-blue-600 rounded-full dark:bg-slate-700"></div>';

    html +=
    '</div>'
    +        '<div className="font-medium overflow-hidden text-ellipsis text-sm whitespace-nowrap">'+ lastMessage
    +        '</div>'
    +    '</div>'
    +'</a>';

    return html;
}

function getChatRoomTitle(userId, chatUserIds){
    return "test";
}

function getChatRoomList(){
    let accessToken = window.localStorage.getItem(accessTokenString);

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