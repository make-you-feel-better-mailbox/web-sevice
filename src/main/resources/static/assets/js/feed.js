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

    let template = '<div class="bg-white rounded-xl shadow-sm text-sm font-medium border1 dark:bg-dark2" id="postingId'+ postingId +'">'
                      + '<div class="flex gap-3 sm:p-4 p-2.5 text-sm font-medium">'
                      +     '<a href="timeline.html"> <img th:src="@{/assets/images/avatars/avatar-5.jpg}" alt="" class="w-9 h-9 rounded-full"> </a>'
                      +     '<div class="flex-1">'
                      +         '<a href="timeline.html"> <h4 class="text-black dark:text-white"> '+ postingUserId +' </h4> </a>'
                      +         '<div class="text-xs text-gray-500 dark:text-white/80">'+ localDatetime +'</div>'
                      +     '</div>'
                      +     '<div class="-mr-1">'
                      +         '<button type="button" class="button__ico w-8 h-8" aria-haspopup="true" aria-expanded="false"> <ion-icon class="text-xl md hydrated" name="ellipsis-horizontal" role="img" aria-label="ellipsis horizontal"></ion-icon> </button>'
                      +         '<div class="w-[245px] uk-dropdown" uk-dropdown="pos: bottom-right; animation: uk-animation-scale-up uk-transform-origin-top-right; animate-out: true; mode: click">'
                      +             '<nav>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="bookmark-outline" role="img" aria-label="bookmark outline"></ion-icon>  Add to favorites </a>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="notifications-off-outline" role="img" aria-label="notifications off outline"></ion-icon> Mute Notification </a>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="flag-outline" role="img" aria-label="flag outline"></ion-icon>  Report this post </a>'
                      +                 '<a href="#"> <ion-icon class="text-xl shrink-0 md hydrated" name="share-outline" role="img" aria-label="share outline"></ion-icon>  Share your profile </a>'
                      +                 '<hr>'
                      +                 '<a href="#" class="text-red-400 hover:!bg-red-50 dark:hover:!bg-red-500/50"> <ion-icon class="text-xl shrink-0 md hydrated" name="stop-circle-outline" role="img" aria-label="stop circle outline"></ion-icon>  Unfollow </a>'
                      +             '</nav>'
                      +         '</div>'
                      +     '</div>'
                      + '</div>'
                      + '<div class="sm:px-4 p-2.5 pt-0">'
                      +     '<p class="font-normal"> '+ content +' </p>'
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
                      +         '<div class="!top-2 pr-2 uk-drop" uk-drop="pos: bottom-right; mode: click">'
                      +             '<div class="flex items-center gap-2" uk-scrollspy="target: > svg; cls: uk-animation-slide-right-small; delay: 100 ;repeat: true">'
                      +                 '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" class="w-6 h-6 fill-sky-600" style="opacity: 0;">'
                      +                     '<path fill-rule="evenodd" d="M1.5 6a2.25 2.25 0 012.25-2.25h16.5A2.25 2.25 0 0122.5 6v12a2.25 2.25 0 01-2.25 2.25H3.75A2.25 2.25 0 011.5 18V6zM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0021 18v-1.94l-2.69-2.689a1.5 1.5 0 00-2.12 0l-.88.879.97.97a.75.75 0 11-1.06 1.06l-5.16-5.159a1.5 1.5 0 00-2.12 0L3 16.061zm10.125-7.81a1.125 1.125 0 112.25 0 1.125 1.125 0 01-2.25 0z" clip-rule="evenodd"></path>'
                      +                 '</svg>'
                      +                 '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5 fill-pink-600" style="opacity: 0;">'
                      +                     '<path d="M3.25 4A2.25 2.25 0 001 6.25v7.5A2.25 2.25 0 003.25 16h7.5A2.25 2.25 0 0013 13.75v-7.5A2.25 2.25 0 0010.75 4h-7.5zM19 4.75a.75.75 0 00-1.28-.53l-3 3a.75.75 0 00-.22.53v4.5c0 .199.079.39.22.53l3 3a.75.75 0 001.28-.53V4.75z"></path>'
                      +                 '</svg>'
                      +             '</div>'
                      +         '</div>'
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
    let commentContent = '<div class="flex items-start gap-3 relative" id="commentId'+ commentId +'">'
                            +         '<a href="timeline.html"> <img th:src="@{/assets/images/avatars/avatar-2.jpg}" alt="" class="w-6 h-6 mt-1 rounded-full"> </a>'
                            +         '<div class="flex-1">'
                            +             '<a href="timeline.html" class="text-black font-medium inline-block dark:text-white"> '+ userId +' </a>'
                            +             '<p class="mt-0.5">'+ content +'</p>'
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