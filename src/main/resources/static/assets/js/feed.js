const postingCategory = 1;

function getPosting(){
    let pageSize = 5;

    let formObject = {
        "pageNumber" : postPageNumber,
        "pageSize" : pageSize
    }

    $.ajax({
        url: postingFilterUri,
        method: "GET",
        data: formObject,
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            $('#postingPlaceHolder').remove();

            if(response != null && response.content != null && response.content.length > 0){
                response.content.forEach(function(element, index){
                    if($("#postingId" + element.postingId).length === 0){
                        let postingTemplate = getPostingTemplate(element.userId,
                            element.content,
                            element.postedDate,
                            element.postingId);

                        $('#feed').append(postingTemplate);
                    }
                })

                isLastPage = response.last;

                if(response.content.length === pageSize) postPageNumber = postPageNumber + 1;

                if (!isLastPage) $('#feed').append(getPlaceHolder());
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

function getPostingTemplate(postingUserId, content, insertDateTime, postingId){
    const dateString = insertDateTime;

    const localDatetime = moment(dateString).tz('Asia/Seoul').format('YYYY-MM-DD HH:mm');

    const writerControlBox = '<hr>'
                    +                 '<a onclick="updatePosting('+ postingId +')" class="notWorkA"> <ion-icon class="text-xl shrink-0 md hydrated" name="pencil-outline" role="img" aria-label="bookmark outline"></ion-icon> Update Posting </a>'
                    +                 '<a onclick="deletePosting('+ postingId +')"  class="text-red-400 hover:!bg-red-50 dark:hover:!bg-red-500/50 notWorkA"> <ion-icon class="text-xl shrink-0 md hydrated" name="trash-outline" role="img" aria-label="stop circle outline"></ion-icon> Delete </a>';

    const isUserPostingWriter = window.localStorage.getItem(userIdString) === postingUserId;

    let template = '<div class="bg-white rounded-xl shadow-sm text-sm font-medium border1 dark:bg-dark2" id="postingId'+ postingId +'">'
                      + '<div class="flex gap-3 sm:p-4 p-2.5 text-sm font-medium">'
                      +     '<a href="timeline.html"> <img th:src="@{/assets/images/avatars/avatar-5.jpg}" alt="" class="w-9 h-9 rounded-full"> </a>'
                      +     '<div class="flex-1">'
                      +         '<a href="timeline.html"> <h4 class="text-black dark:text-white"> '+ postingUserId +' </h4> </a>'
                      +         '<div class="text-xs text-gray-500 dark:text-white/80">'+ localDatetime +'</div>'
                      +     '</div>'
                      +     '<div class="-mr-1">'
                      +         '<button type="button" class="button__ico w-8 h-8" aria-haspopup="true" aria-expanded="false"> <ion-icon class="text-xl md hydrated" name="ellipsis-horizontal" role="img" aria-label="ellipsis horizontal"></ion-icon> </button>'
                      +         '<div class="w-[245px] uk-dropdown" id="ukDropDownPosting'+ postingId +'" uk-dropdown="pos: bottom-right; animation: uk-animation-scale-up uk-transform-origin-top-right; animate-out: true; mode: click">'
                      +             '<nav>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="bookmark-outline" role="img" aria-label="bookmark outline"></ion-icon>  Add to favorites </a>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="notifications-off-outline" role="img" aria-label="notifications off outline"></ion-icon> Mute Notification </a>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="flag-outline" role="img" aria-label="flag outline"></ion-icon>  Report this post </a>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="share-outline" role="img" aria-label="share outline"></ion-icon>  Share your profile </a>'
                      +                 '<hr>'
                      +                 '<a href="#" class="text-red-400 hover:!bg-red-50 dark:hover:!bg-red-500/50"> <ion-icon class="text-xl shrink-0 md hydrated" name="stop-circle-outline" role="img" aria-label="stop circle outline"></ion-icon> Unfollow </a>';

    template += isUserPostingWriter ? writerControlBox : '';

    let realContent = content.replace(/\n/g, "<br>");

    template +=             '</nav>'
                      +         '</div>'
                      +     '</div>'
                      + '</div>'
                      + '<div class="sm:px-4 p-2.5 pt-0" id="contentDiv'+ postingId +'">'
                      +     '<p class="font-normal"> '+ realContent +' </p>'
                      + '</div>'
                      + '<div class="sm:p-4 p-2.5 flex items-center gap-4 text-xs font-semibold">'
                      +     '<div>'
                      +         '<div class="flex items-center gap-2.5 postingLikeDiv" id="postingLikeCountTarget'+ postingId +'">'
                      +             '<button type="button" id="postingLikeBtn'+ postingId +'" class="button-icon-heart text-red-500 bg-slate-200/70 dark:bg-slate-700" onclick="likePosting('+ postingId +')"> <ion-icon class="text-lg" name="heart"></ion-icon> </button>'
                      +             '<input type="hidden" class="postingId" value="'+ postingId+ '">'
                      +             '<input type="hidden" class="userLikeIt" value="false">'
                      +             '<p class="postingLikeCountText">0</p>'
                      +         '</div>'
                      +     '</div>'
                      +     '<div class="flex items-center gap-3" id="postingCommentCountTarget'+ postingId +'">'
                      +         '<button type="button" class="button-icon bg-slate-200/70 dark:bg-slate-700"> <ion-icon class="text-lg" name="chatbubble-ellipses"></ion-icon> </button>'
                      +         '<span class="postingCommentCountText">0</span>'
                      +     '</div>'
                      +     '<button type="button" class="button-icon ml-auto"> <ion-icon class="text-xl" name="paper-plane-outline"></ion-icon> </button>'
                      +     '<button type="button" class="button-icon"> <ion-icon class="text-xl" name="share-outline"></ion-icon> </button>'
                      + '</div>'
                      + '<div class="sm:p-4 p-2.5 border-t border-gray-100 font-normal space-y-3 relative dark:border-slate-700/40" id="commentArea'+ postingId +'">'
                      +     '<input type="hidden" id="commentPageNumber'+ postingId +'" value="0">'
                      +     getMoreCommentBtn(postingId)
                      + '</div>'
                      + '<div class="sm:px-4 sm:py-3 p-2.5 border-t border-gray-100 flex items-center gap-1 dark:border-slate-700/40">'
                      +     '<img th:src="@{/assets/images/avatars/avatar-7.jpg}" alt="" class="w-6 h-6 rounded-full">'
                      +     '<div class="flex-1 relative overflow-hidden h-10">'
                      +         '<textarea id="commentContent'+ postingId +'" placeholder="Add Comment...." rows="1" class="w-full resize-none !bg-transparent px-4 py-2 focus:!border-transparent focus:!ring-transparent" aria-haspopup="true" aria-expanded="false"></textarea>'
                      +     '</div>'
                      +     '<button type="button" onclick="registerComment('+ postingId +')" class="text-sm rounded-full py-1.5 px-3.5 bg-secondery"> Replay</button>'
                      + '</div>'
                   + '</div>';

    getPostingLikeCount(postingId);

    getPostingCommentCount(postingId);

    getCommentList(postingId);

    let accessToken = window.localStorage.getItem(accessTokenString);

    if(accessToken != null && accessToken !== "") checkPostingUserLike(postingId);

    return template;
}

function likePosting(postingId){
    let accessToken = window.localStorage.getItem(accessTokenString);

    if (accessToken == null || accessToken == ""){
        alert("You can like it after sign in");
        return;
    }

    checkTokenExpired();

    let isLiked = $('#postingLikeCountTarget'+postingId).find('.userLikeIt').val();

    if (isLiked == "true"){
        deleteLikeRequest(postingId);
    } else {
        likePostingRequest(postingId);
    }
}

function likePostingRequest(postingId){
    let accessToken = window.localStorage.getItem(accessTokenString);

    let formObject = {
        "accessToken" : accessToken,
        "category" : postingCategory,
        "targetId" : postingId
    }

    $.ajax({
        url: likeUri,
        method: "POST",
        data: JSON.stringify(formObject),
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            if(response.isRegisterSuccess){
                $('#postingLikeBtn' + postingId).remove();

                let buttonClass = "button-icon text-red-500 bg-red-100 dark:bg-slate-700";

                let content = '<button type="button" id="postingLikeBtn'+ postingId +'" class="'+ buttonClass +'" onclick="likePosting('+ postingId +')"> <ion-icon class="text-lg" name="heart"></ion-icon> </button>'

                $('#postingLikeCountTarget'+postingId).prepend(content);

                $('#postingLikeCountTarget'+postingId).find('.userLikeIt').val("true");

                let likeCount = $('#postingLikeCountTarget'+postingId).find('.postingLikeCountText').text() * 1;

                $('#postingLikeCountTarget'+postingId).find('.postingLikeCountText').remove();

                likeCount = likeCount + 1;

                let countText = '<p class="postingLikeCountText">'+ likeCount.toLocaleString() +'</p>';

                $('#postingLikeCountTarget'+postingId).append(countText);
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

function deleteLikeRequest(postingId){
    let accessToken = window.localStorage.getItem(accessTokenString);

    let formObject = {
        "accessToken" : accessToken,
        "category" : postingCategory,
        "targetId" : postingId
    }

    $.ajax({
        url: likeUri,
        method: "DELETE",
        data: JSON.stringify(formObject),
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            if(response.isDeleteSuccess){
                $('#postingLikeBtn' + postingId).remove();

                let content = '<button type="button" id="postingLikeBtn'+ postingId +'" class="button-icon-heart text-red-500 bg-slate-200/70 dark:bg-slate-700" onclick="likePosting('+ postingId +')"> <ion-icon class="text-lg" name="heart"></ion-icon> </button>'

                $('#postingLikeCountTarget'+postingId).prepend(content);

                $('#postingLikeCountTarget'+postingId).find('.userLikeIt').val("false");

                let likeCount = $('#postingLikeCountTarget'+postingId).find('.postingLikeCountText').text() * 1;

                $('#postingLikeCountTarget'+postingId).find('.postingLikeCountText').remove();

                likeCount = likeCount - 1;

                if(likeCount < 0) likeCount = 0;

                let countText = '<p class="postingLikeCountText">'+ likeCount.toLocaleString() +'</p>';

                $('#postingLikeCountTarget'+postingId).append(countText);
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

function checkPostingUserLike(postingId){
    let accessToken = window.localStorage.getItem(accessTokenString);

    let formObject = {
        "accessToken" : accessToken,
        "category" : postingCategory,
        "targetId" : postingId
    }

    $.ajax({
        url: likeUri,
        method: "GET",
        data: formObject,
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            if (response.isUserLikeTarget) {
                $('#postingLikeBtn' + postingId).remove();

                let buttonClass = "button-icon text-red-500 bg-red-100 dark:bg-slate-700";

                let content = '<button type="button" id="postingLikeBtn'+ postingId +'" class="'+ buttonClass +'" onclick="likePosting('+ postingId +')"> <ion-icon class="text-lg" name="heart"></ion-icon> </button>'

                $('#postingLikeCountTarget'+postingId).prepend(content);

                $('#postingLikeCountTarget'+postingId).find('.userLikeIt').val("true");
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

function getPlaceHolder(){
    let placeHolder = '<div class="rounded-xl shadow-sm p-4 space-y-4 bg-slate-200/40 animate-pulse border1 dark:bg-dark2" id="postingPlaceHolder">'
                      +      '<div class="flex gap-3">'
                      +          '<div class="w-9 h-9 rounded-full bg-slate-300/20"></div>'
                      +          '<div class="flex-1 space-y-3">'
                      +              '<div class="w-40 h-5 rounded-md bg-slate-300/20"></div>'
                      +              '<div class="w-24 h-4 rounded-md bg-slate-300/20"></div>'
                      +          '</div>'
                      +          '<div class="w-6 h-6 rounded-full bg-slate-300/20"></div>'
                      +      '</div>'
                      +      '<div class="w-full h-52 rounded-lg bg-slate-300/10 my-3"> </div>'
                      +      '<div class="flex gap-3">'
                      +          '<div class="w-16 h-5 rounded-md bg-slate-300/20"></div>'
                      +          '<div class="w-14 h-5 rounded-md bg-slate-300/20"></div>'
                      +          '<div class="w-6 h-6 rounded-full bg-slate-300/20 ml-auto"></div>'
                      +          '<div class="w-6 h-6 rounded-full bg-slate-300/20  "></div>'
                      +      '</div>'
                      +  '</div>';
    return placeHolder;
}

function getPostingLikeCount(postingId){
    $.ajax({
        url: likeUri + "/" + postingCategory + "/" + postingId,
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            $('#postingLikeCountTarget'+postingId).find('.postingLikeCountText').remove();

            let countText = '<p class="postingLikeCountText">'+ response.likeCount.toLocaleString() +'</p>';

            $('#postingLikeCountTarget'+postingId).append(countText);
        },
        complete: function(response){
        },
        error: function(response){
            let errorModal = $("#errorModal");

            UIkit.modal(errorModal).show();
        }
    });
}

function getPostingCommentCount(postingId){
    $.ajax({
        url: commentCountUri + "/" + postingCategory + "/" + postingId,
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            $('#postingCommentCountTarget'+postingId).find('.postingCommentCountText').remove();

            let countText = '<span class="postingCommentCountText">'+ response.commentCount.toLocaleString() +'</span>';

            $('#postingCommentCountTarget'+postingId).append(countText);
        },
        complete: function(response){
        },
        error: function(response){
            let errorModal = $("#errorModal");

            UIkit.modal(errorModal).show();
        }
    });
}

// 화면에 보이는지 여부를 확인하고 AJAX 요청을 보내는 함수
function sendAjaxRequestIfVisible() {
    let visibleDivs = [];

    $('.postingLikeDiv').each(function() {
        let $div = $(this);
        if (isElementInViewport($div)) {
            visibleDivs.push($div);
        }
    });

    visibleDivs.forEach(function (element){
        getPostingLikeCount(element.find('.postingId').val());
        getPostingCommentCount(element.find('.postingId').val());
    });
}

function getCommentList(postingId){
    let pageSize = 3;

    let commentPageNumber = $("#commentPageNumber"+postingId).val();

    if(commentPageNumber === 0 || commentPageNumber === "0" || commentPageNumber == null) commentPageNumber = 0;
    else commentPageNumber = commentPageNumber * 1;

    let formObject = {
        "pageNumber" : commentPageNumber,
        "pageSize" : pageSize,
        "category" : postingCategory,
        "targetId" : postingId
    }

    $.ajax({
        url: commentFilterUri,
        method: "GET",
        data: formObject,
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            $('#moreCommentBtn'+postingId).remove();

            if(response != null && response.content != null && response.content.length > 0){
                response.content.forEach(function(element, index){
                    if($("#commentId" + element.commentId).length === 0){
                        let commentTemplate = getCommentContent(element.commentId,
                            element.userId,
                            element.content);

                        $('#commentArea'+postingId).append(commentTemplate);
                    }
                })

                if(response.content.length === pageSize) {
                    commentPageNumber = commentPageNumber + 1;

                    $("#commentPageNumber"+postingId).val(commentPageNumber);
                }
            }

            $('#commentArea'+postingId).append(getMoreCommentBtn(postingId));
        },
        complete: function(response){
        },
        error: function(response){
            let errorModal = $("#errorModal");

            UIkit.modal(errorModal).show();
        }
    });
}

function getCommentContent(commentId,userId, content){
    let realContent = content.replace(/\n/g, "<br>");

    let commentContent = '<div class="flex items-start gap-3 relative" id="commentId'+ commentId +'">'
                            +         '<a href="timeline.html"> <img th:src="@{/assets/images/avatars/avatar-2.jpg}" alt="" class="w-6 h-6 mt-1 rounded-full"> </a>'
                            +         '<div class="flex-1">'
                            +             '<a href="timeline.html" class="text-black font-medium inline-block dark:text-white"> '+ userId +' </a>'
                            +             '<p class="mt-0.5">'+ realContent +'</p>'
                            +         '</div>'
                            +     '</div>';
    return commentContent;
}

function getMoreCommentBtn(postingId){
    let moreCommentBtn = '<button type="button" id="moreCommentBtn'+ postingId +'"  onclick="getCommentList('+ postingId+ ')" class="flex items-center gap-1.5 text-gray-500 hover:text-blue-500 mt-2">'
                            +        '<ion-icon name="chevron-down-outline" class="ml-auto duration-200 group-aria-expanded:rotate-180"></ion-icon>'
                            +        'More Comment'
                            +    '</button>';
    return moreCommentBtn;
}

function registerComment(postingId){
    checkTokenExpired();

    let commentContent = $('#commentContent'+postingId).val();

    if (commentContent == null || commentContent === ""){
        alert("댓글을 입력해주세요");
        return null;
    }

    var formObj = {
        "content" : commentContent,
        "targetId" : postingId,
        "category" : postingCategory
    };

    formObj[accessTokenString] = window.localStorage.getItem(accessTokenString);

    let successNotification = {
        message: '<div class="flex gap-5 items-center"> <div class="rounded-full bg-slate-200 p-1.5 inline-flex ring ring-slate-100 ring-offset-1"> <ion-icon name="checkmark-circle-outline" class="text-xl text-slate-600 drop-shadow-md"></ion-icon> </div> <div class="flex-1"> Comment successfully done! </div> </div>',
        pos: 'top-center',
        timeout: '6000'
    }

    $.ajax({
        url: commentUri,
        method: "POST",
        data: JSON.stringify(formObj),
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            if(response.isRegisterSuccess){
                $('#commentContent'+postingId).val('');

                UIkit.notification(successNotification);

                if($("#commentId" + response.commentId).length === 0){
                    let commentTemplate = getCommentContent(response.commentId,
                        $('#userIdText').text(),
                        commentContent);

                    $('#commentArea'+postingId).prepend(commentTemplate);
                }
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

function getPostPostingTemplate(){
    const postPostingTemplate = '<div class="bg-white rounded-xl shadow-sm md:p-4 p-2 space-y-4 text-sm font-medium border1 dark:bg-dark2" id="postPostingBox">'
                                        +    '<div class="flex items-center md:gap-3 gap-1">'
                                        +        '<div onclick="resetCreateStatus()" class="flex-1 bg-slate-100 hover:bg-opacity-80 transition-all rounded-lg cursor-pointer dark:bg-dark3" uk-toggle="target: #create-status">'
                                        +            '<div class="py-2.5 text-center dark:text-white"> What do you have in mind? </div>'
                                        +        '</div>'
                                        +        '<div onclick="resetCreateStatus()" class="cursor-pointer hover:bg-opacity-80 p-1 px-1.5 rounded-xl transition-all bg-pink-100/60 hover:bg-pink-100 dark:bg-white/10 dark:hover:bg-white/20" uk-toggle="target: #create-status">'
                                        +            '<svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8 stroke-pink-600 fill-pink-200/70" viewBox="0 0 24 24" stroke-width="1.5" stroke="#2c3e50" fill="none" stroke-linecap="round" stroke-linejoin="round">'
                                        +                '<path stroke="none" d="M0 0h24v24H0z" fill="none"/>'
                                        +                '<path d="M15 8h.01" />'
                                        +                '<path d="M12 3c7.2 0 9 1.8 9 9s-1.8 9 -9 9s-9 -1.8 -9 -9s1.8 -9 9 -9z" />'
                                        +                '<path d="M3.5 15.5l4.5 -4.5c.928 -.893 2.072 -.893 3 0l5 5" />'
                                        +                '<path d="M14 14l1 -1c.928 -.893 2.072 -.893 3 0l2.5 2.5" />'
                                        +            '</svg>'
                                        +        '</div>'
                                        +    '</div>'
                                        +'</div>';
    return postPostingTemplate;
}

function updatePosting(postingId){
    UIkit.dropdown($("#ukDropDownPosting"+postingId)).hide(0);

    $.ajax({
        url: postingUri + "/" + postingId,
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            if(response != null){
                $('#content').val(response.content);
                $("#postPostingContentId").val(response.postingId);

                let createStatusModal = $("#create-status");

                UIkit.modal(createStatusModal).show();
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

function updatePostingRequest(){
    let requestContent = $('#content').val();

    let formObj = {
        "accessToken" : window.localStorage.getItem(accessTokenString),
        "content" : requestContent,
        "mediaExist" : false
    };

    let postingId = $("#postPostingContentId").val();

    let successNotification = {
        message: '<div class="flex gap-5 items-center"> <div class="rounded-full bg-slate-200 p-1.5 inline-flex ring ring-slate-100 ring-offset-1"> <ion-icon name="checkmark-circle-outline" class="text-xl text-slate-600 drop-shadow-md"></ion-icon> </div> <div class="flex-1"> Update Posting successfully done! </div> </div>',
        pos: 'top-center',
        timeout: '6000'
    }

    $.ajax({
        url: postingUri + "/" + postingId,
        method: "PUT",
        data: JSON.stringify(formObj),
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            if(response.isUpdateSuccess){
                UIkit.notification(successNotification);

                $("#contentDiv" + postingId).empty();

                let content = '<p class="font-normal"> '+ requestContent.replace(/\n/g, "<br>") +' </p>'

                $("#contentDiv" + postingId).append(content);

                $("#postPostingContentId").val(0);

                let createStatusModal = $("#create-status");

                UIkit.modal(createStatusModal).hide();
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

function deletePosting(postingId){
    UIkit.dropdown($("#ukDropDownPosting"+postingId)).hide(0);

    myConfirm("정말 삭제하시겠습니까?", function(){
        checkTokenExpired();

        let formObj = {
            "accessToken" : window.localStorage.getItem(accessTokenString)
        };

        let successNotification = {
            message: '<div class="flex gap-5 items-center"> <div class="rounded-full bg-slate-200 p-1.5 inline-flex ring ring-slate-100 ring-offset-1"> <ion-icon name="checkmark-circle-outline" class="text-xl text-slate-600 drop-shadow-md"></ion-icon> </div> <div class="flex-1"> Delete Posting successfully done! </div> </div>',
            pos: 'top-center',
            timeout: '6000'
        }

        $.ajax({
            url: postingUri + "/" + postingId,
            method: "DELETE",
            data: JSON.stringify(formObj),
            dataType: "JSON",
            contentType: 'application/json',
            beforeSend: function(request) {
            },
            success: function(response){
                if(response.isDeleteSuccess){
                    $("#postingId" + postingId).remove();

                    UIkit.notification(successNotification);
                }
            },
            complete: function(response){
            },
            error: function(response){
                let errorModal = $("#errorModal");

                UIkit.modal(errorModal).show();
            }
        });
    });
}