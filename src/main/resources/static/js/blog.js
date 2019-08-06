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