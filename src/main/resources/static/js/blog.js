function json() {
    var json = [];
    var user = {};
    user.phone = document.userForm.phone;
    user.username = document.userForm.username;
    user.password = document.userForm.password;
    json.push(user);
    var userJson = JSON.stringify(json);
    alert(userJson);
}

function errorMessgae() {
    var type = $('#type').val();
    var original = "原创";
    var reprint = "转载";
    if (type !== original && type !== reprint) {
        alert("类型错误，请输入原创或转载");
    }
}

function modifiedUser() {
    alert("修改成功！")
}

function postComment() {
    var articleId = $("#article_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "articleId": articleId,
            "content": commentContent
        }),
        success: function (response) {
            window.location.reload();
        },
        dataType: "json"
    });
}

function postReply(e) {
    var articleId = $("#article_id").val();
    var respondentId = $("#respondent_id").val();
    var commentId = e.getAttribute("data-id");
    var textareaId = "#reply-" + commentId;
    var replyContent = $(textareaId).val();
    $.ajax({
        type: "POST",
        url: "/reply",
        contentType: "application/json",
        data: JSON.stringify({
            /* key 要与 CommentDTO 里面属性的名称一一对应 */
            "commentId": commentId,
            "articleId": articleId,
            "respondentId": respondentId,
            "content": replyContent
        }),
        success: function (response) {
            window.location.reload();
        },
        dataType: "json"
    });
}