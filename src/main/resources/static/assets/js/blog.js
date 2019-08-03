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