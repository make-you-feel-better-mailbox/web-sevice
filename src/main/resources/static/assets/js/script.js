const accessTokenString = "accessToken";
const refreshTokenString = "refreshToken";
const userIdString = "userId";

// On page load or when changing themes, best to add inline in `head` to avoid FOUC
if (localStorage.theme === 'dark' || (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
    document.documentElement.classList.add('dark')
    } else {
    document.documentElement.classList.remove('dark')
    }

// Whenever the user explicitly chooses light mode
localStorage.theme = 'light'

// Whenever the user explicitly chooses dark mode
localStorage.theme = 'dark'

// Whenever the user explicitly chooses to respect the OS preference
localStorage.removeItem('theme')



// add post upload image 
document.getElementById('addPostUrl').addEventListener('change', function(){
if (this.files[0] ) {
    var picture = new FileReader();
    picture.readAsDataURL(this.files[0]);
    picture.addEventListener('load', function(event) {
    document.getElementById('addPostImage').setAttribute('src', event.target.result);
    document.getElementById('addPostImage').style.display = 'block';
    });
}
});


// Create Status upload image 
document.getElementById('createStatusUrl').addEventListener('change', function(){
if (this.files[0] ) {
    var picture = new FileReader();
    picture.readAsDataURL(this.files[0]);
    picture.addEventListener('load', function(event) {
    document.getElementById('createStatusImage').setAttribute('src', event.target.result);
    document.getElementById('createStatusImage').style.display = 'block';
    });
}
});


// create product upload image
document.getElementById('createProductUrl').addEventListener('change', function(){
if (this.files[0] ) {
    var picture = new FileReader();
    picture.readAsDataURL(this.files[0]);
    picture.addEventListener('load', function(event) {
    document.getElementById('createProductImage').setAttribute('src', event.target.result);
    document.getElementById('createProductImage').style.display = 'block';
    });
}
});

function checkTokenExpired(){
    let accessToken = window.localStorage.getItem(accessTokenString);

    let requestEndPoint = userDetailRequestUri + "/" + accessToken;

    $.ajax({
        url: requestEndPoint,
        method: "GET",
        dataType: "JSON",
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            $('#nicknameText').text(response.nickname);
            $('#userIdText').text(response.userId);
            window.localStorage.setItem(userIdString, response.userId);
            $('#notificationsCount').text(0);
        },
        complete: function(response){
        },
        error: function(response){
            let responseString = response.responseJSON;

            if(response.status.toString().startsWith('4') && responseString != null && responseString == accessTokenExpired){

                let responseString = response.responseJSON;

                if (responseString == accessTokenExpired) {
                    reissueAccessToken();
                }

            } else if (response.status.toString().startsWith('4')) {
                allTokenExpired();

                let errorModal = $("#errorModal");

                UIkit.modal(errorModal).show();
            } else {
                let errorModal = $("#errorModal");

                UIkit.modal(errorModal).show();
            }
        }
    });
}

function reissueAccessToken(){
    let accessToken = window.localStorage.getItem(accessTokenString);
    let refreshToken = window.localStorage.getItem(refreshTokenString);

    let requestObject = {
        "accessToken" : accessToken,
        "refreshToken" : refreshToken
    };

    $.ajax({
        url: tokenUri,
        method: "POST",
        dataType: "JSON",
        data: JSON.stringify(requestObject),
        contentType: 'application/json',
        beforeSend: function(request) {
        },
        success: function(response){
            let newAccessToken = response.accessToken;
            window.localStorage.removeItem(accessTokenString);
            window.localStorage.setItem(accessTokenString, newAccessToken);
        },
        complete: function(response){
        },
        error: function(response){
            let responseString = response.responseJSON;

            if(response.status.toString().startsWith('4') && responseString != null && responseString == refreshTokenExpired){
                allTokenExpired();
            } else {
                let errorModal = $("#errorModal");

                UIkit.modal(errorModal).show();
            }
        }
    });
}

function allTokenExpired(){
    alert("로그인 시간이 만료됐습니다. 다시 로그인해주세요.");
    window.localStorage.removeItem(accessTokenString);
    window.localStorage.removeItem(refreshTokenString);
    location.href = rootUri;
}

function isElementInViewport(elem) {
    var $elem = $(elem);
    var windowTop = $(window).scrollTop();
    var windowBottom = windowTop + $(window).height();
    var elemTop = $elem.offset().top;
    var elemBottom = elemTop + $elem.height();

    return ((elemBottom <= windowBottom) && (elemTop >= windowTop));
}

function myConfirm(content, callbackFunction){
    $("#confirmModalText").text(content);

    let confirmModal = $("#confirmModal");

    UIkit.modal(confirmModal).show();

    $('#confirmModalOkBtn').click(function() {
        callbackFunction();
    });
}

$("#confirmModalCancelBtn").on("click", function (){
    let confirmModal = $("#confirmModal");

    UIkit.modal(confirmModal).hide();
});