const DOMAIN = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
const URL_ROOT = `${window.location.origin}${DOMAIN}`;
const URL_SESSION = `${URL_ROOT}/session`;

$(() => {
    "use strict";
    showUserLoginFail();
    bindFormValidation($("#loginForm"));
});

function bindFormValidation(form) {
    form.submit(function (event) {
        if (!this.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            $(this).addClass("was-validated");
        } else {
            $(this).removeClass("was-validated");
        }
    });
}

let fetchData = (apiUrl, query, method = "get") => {
    return $.ajax({
        url: apiUrl,
        method: method,
        data: query,
        dataType: "json",
    });
};

function showUserLoginFail() {
    let getUser = fetchData(URL_SESSION, {
        userName: "userNameFail",
        password: "passwordFail",
        loginMessage: "loginMessage",
    }, "post");
    getUser.done(function (responseData) {
        if (!$.isEmptyObject(responseData)) {
            $("#username").val(responseData.userNameFail);
            $("#password").val(responseData.passwordFail);
            $("#loginMessage").empty().append(responseData.loginMessage);
        }
    });
}