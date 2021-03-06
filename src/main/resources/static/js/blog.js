function errorMessgae() {
    var type = $('#type').val();
    var original = "原创";
    var reprint = "转载";
    if (type !== original && type !== reprint) {
        alert("类型错误，请输入原创或转载");
    }
}

// function register() {
//     var phone = $("#phone").val();
//     var username = $("#username").val();
//     var password = $("#password").val();
//     if (phone && username && password) {
//         $.ajax({
//             type: "POST",
//             url: "register/registerUser",
//             contentType: "application/json;charset=utf-8",
//             data: JSON.stringify({
//                 phone: phone,
//                 username: username,
//                 password: password
//             }),
//             success: function (response) {
//                 window.location.href = "/login";
//             },
//             dataType: "json"
//         });
//     } else {
//         alert("出错了！！！");
//     }
// }

function modifiedUser() {
    var username = $("#name").val();
    var email = $("#email").val();
    var avatar = $("#avatar").val();
    var intro = $("#intro").val();
    if (username || email || avatar || intro) {
        $.ajax({
            type: "POST",
            url: "/admin/user",
            contentType: "application/json",
            data: JSON.stringify({
                username: username,
                email: email,
                avatarImgUrl: avatar,
                personalBrief: intro
            }),
            success: function (response) {
                alert("修改成功！");
                window.location.reload();
            },
            dataType: "json"
        })
    }
}

function postComment() {
    var articleId = $("#article_id").val();
    var commentContent = $("#comment_content").val();
    if (commentContent) {
        $.ajax({
            type: "POST",
            url: "/comment",
            contentType: "application/json",
            data: JSON.stringify({
                articleId: articleId,
                commentContent: commentContent
            }),
            success: function (response) {
                window.location.reload();
            },
            dataType: "json"
        });
    } else {
        alert("评论不能为空！");
    }
}

function postReply(e) {
    var articleId = $("#article_id").val();
    var respondentId = $("#respondent_id").val();
    var parentId = e.getAttribute("data-id");
    var textareaId = "#reply-" + parentId;
    var replyContent = $(textareaId).val();
    if (replyContent) {
        $.ajax({
            type: "POST",
            url: "/reply",
            contentType: "application/json",
            data: JSON.stringify({
                /* key 要与 CommentDTO 里面属性的名称一一对应 */
                parentId: parentId,
                articleId: articleId,
                respondentId: respondentId,
                commentContent: replyContent
            }),
            success: function (response) {
                window.location.reload();
            },
            dataType: "json"
        });
    } else {
        alert("回复不能为空！");
    }
}

function like(e) {
    var articleId = e.getAttribute("data-articleId");
    if (articleId) {
        $.ajax({
            type: "POST",
            url: "/like",
            contentType: "application/json",
            data: JSON.stringify({
                articleId: articleId
            }),
            success: function (response) {
                if (response.code === 200) {
                    window.location.reload();
                } else {
                    alert(response.message);
                }
            },
            dataType: "json"
        });
    }
}

function messageStatus(e) {
    var messageId = e.getAttribute("data-id");
    if (messageId) {
        $.ajax({
            type: "PUT",
            url: "/message/" + messageId
        })
    }
}