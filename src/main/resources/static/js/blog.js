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