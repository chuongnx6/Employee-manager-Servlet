const DOMAIN = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
const URL_ROOT = `${window.location.origin}${DOMAIN}`;
const URL_SESSION = `${URL_ROOT}/session`;
const URL_EMPLOYEE_LIST = `${URL_ROOT}/employee/list`;
const URL_EMPLOYEE_DETAIL = `${URL_ROOT}/employee/detail`;
const URL_EMPLOYEE_DELETE = `${URL_ROOT}/employee/delete`;
const URL_DEPARTMENT_LIST = `${URL_ROOT}/department/list`;

$(document).ready(() => {
    "use strict";
    showUser();
    $("#logout").click((event) => {
        event.preventDefault();
        sendRedirect($(event.currentTarget));
    });

    let isShowEmployeeManager = true;
    $("#employeeManager").click((event) => {
        event.preventDefault();
        if (isShowEmployeeManager) {
            $("#employeeManager").children(":last").removeClass("fa-chevron-down").addClass("fa-chevron-up")
            $("#employeeList").removeClass("d-none");
            $("#employeeAdd").removeClass("d-none");
        } else {
            $("#employeeManager").children(":last").removeClass("fa-chevron-up").addClass("fa-chevron-down")
            $("#employeeList").addClass("d-none");
            $("#employeeAdd").addClass("d-none");
        }
        isShowEmployeeManager = !isShowEmployeeManager;
    });

    $("#employeeList").click((event) => {
        event.preventDefault();
        loadContent(`${DOMAIN}/page/component/employee-list.html`);
    });

    $("#employeeAdd").click((event) => {
        event.preventDefault();
        loadContent(`${DOMAIN}/page/component/employee-info.html`);
    });
});

const fetchData = (apiUrl, query, method, contentType) => {
    return $.ajax({
        url: apiUrl,
        method: method || "get",
        data: query,
        contentType: contentType || "application/x-www-form-urlencoded; charset=UTF-8",
        dataType: "json",
    });
};

const sendRedirect = (thisButton) => {
    let href = thisButton.attr("href");
    window.location.href = `${URL_ROOT}${href}`;
};

function showUser() {
    let getUser = fetchData(URL_SESSION, {
        account: "account",
    }, "post");
    getUser.done(function (responseData) {
        let account = responseData.account;
        $("#userName").empty().append(account.userName);
    });
}

const loadContent = (apiUrl, bindingData) => {
    $.ajax({
        url: apiUrl,
        method: "get",
        dataType: "html",
    }).done((responseData) => {
        handleLoadContentResponse(responseData, apiUrl, bindingData);
    });
};

function handleLoadContentResponse(responseData, apiUrl, bindingData) {
    $("#contentContainer").empty().append(responseData);
    if (apiUrl.endsWith("employee-list.html")) {
        showEmployeeList(1);
        bindEmployeeListEvents();
    } else if (apiUrl.endsWith("employee-info.html")) {
        let departmentList = fetchData(URL_DEPARTMENT_LIST, "");
        departmentList.done((responseData) => {
            const list = responseData.data;
            const html = list.map((obj, i) => `
                <option value="${obj.id}">${obj.name}</option>`
            );
            $("#department").empty().append(html);

            if (bindingData) {
                $("#employeeInfo").attr("action", "employee/update");
                $("#employeeInfoTitle").text("Edit Employee");
                $("#employeeId").closest(".input-group").removeClass("d-none");
                $("#employeeInfo button[type='reset']").addClass("d-none");
                $("#employeeInfo button[type='submit']").empty().append(`<i class="fa-solid fa-arrows-rotate"></i><span class="ms-2">Update</span>`);
                const data = bindingData.data;
                for (const key in data) {
                    if (data.hasOwnProperty(key)) {
                        const formField = $("#employeeInfo [name='" + key + "']");
                        if (formField.length) {
                            if (formField.is(":radio")) {
                                formField.filter("[value='" + data[key] + "']").prop("checked", true);
                            } else {
                                formField.val(data[key]);
                            }
                        }
                    }
                }
            }
        });
        bindEmployeeInfoEvents();
    }
}

function bindEmployeeListEvents() {
    $("#searchInput").keypress((event) => {
        if (event.which === 13) {
            event.preventDefault();
            showEmployeeList(1);
        }
    });
    $("#searchButton").click((event) => {
        event.preventDefault();
        showEmployeeList(1);
    });
    $("#pageSize").change((event) => {
        event.preventDefault();
        showEmployeeList(1);
    });
}

function bindEmployeeInfoEvents() {
    $("#employeeInfo").submit((event) => {
        event.preventDefault();
        let thisForm = $("#employeeInfo");
        updateEmployee(thisForm);
    });
    $("#backToEmployeeList").click(() => {
        loadContent(`${DOMAIN}/page/component/employee-list.html`);
    });
}

function serializeFormToJson(form) {
    let formDataArray = $(form).serializeArray();
    let formData = {};
    $.each(formDataArray, function (index, field) {
        formData[field.name] = field.value;
    });
    return JSON.stringify(formData);
}

function isFormValid(form) {
    if (form[0].checkValidity()) {
        form.removeClass("was-validated");
        return true;
    } else {
        form.addClass("was-validated");
        return false;
    }
}

function updateEmployee(form) {
    if (!isFormValid(form)) {
        return
    }

    let formData = serializeFormToJson(form);
    let formAction = form.attr("action");
    let formMethod = form.attr("method");
    let addEmployee = fetchData(`${URL_ROOT}/${formAction}`, formData, formMethod, "application/json; charset=utf-8");
    addEmployee.done((responseData) => {
        alert("Success.");
        loadContent(`${DOMAIN}/page/component/employee-list.html`);
    })
    addEmployee.fail((error) => {
        console.log(error);
    });
}

function showEmployeeList(pageNumber) {
    const keyword = $("#searchInput").val();
    const filterBy = $("#searchFilter").val();
    const pageSize = $("#pageSize").val();
    const contentPosition = $("#contentContainer table tbody");

    const getEmployeeList = fetchData(URL_EMPLOYEE_LIST, {
        keyword: keyword,
        filterBy: filterBy,
        pageNumber: pageNumber,
        pageSize: pageSize,
    });
    getEmployeeList.done((responseData) => {
        handleEmployeeListResponse(responseData, contentPosition);
    });
    getEmployeeList.fail(() => {
        handleEmployeeListError(contentPosition);
    });
}

function handleEmployeeListResponse(responseData, contentPosition) {
    const list = responseData.data;

    if (list.length === 0) {
        const row = `<tr><td colspan="7" class="text-center">No matches</td></tr>`;
        contentPosition.empty().append(row);
        $("#tableInfoContainer").addClass("d-none");
    } else {
        const firstRow = +responseData.firstRow;
        const html = list.map((obj, i) => `
            <tr employeeId="${obj.id}">
                <th scope="row" class="text-center">${firstRow + i}</th>
                <td>${obj.firstName} ${obj.lastName}</td>
                <td class="text-center">${obj.dateOfBirth}</td>
                <td>${obj.address}</td>
                <td class="text-end">${obj.phone}</td>
                <td>${obj.departmentName}</td>
                <td class="d-flex justify-content-evenly">
                    <a href="#" class="editEmployee"><i class="fa-regular fa-pen-to-square"></i></a>
                    <a href="#" class="deleteEmployee"><i class="fa-solid fa-trash"></i></a>
                </td>
            </tr>`
        );
        contentPosition.empty().append(html);
        $("#tableInfoContainer").removeClass("d-none");

        $(".editEmployee").click(function () {
            const thisRow = $(this).closest("tr");
            let getEmployee = fetchData(URL_EMPLOYEE_DETAIL, {
                employeeId: thisRow.attr("employeeId")
            });
            getEmployee.done((responseData) => {
                loadContent(`${DOMAIN}/page/component/employee-info.html`, responseData);
            });
        });

        $(".deleteEmployee").click(function () {
            const thisRow = $(this).closest("tr");
            const result = confirm(
                `Are you sure you want to delete the employee with the name ${$(":nth-child(2)", thisRow).text()}?`
            );
            if (result) {
                deleteEmployee(thisRow);
            }
        });
        changeTableInfo(responseData);
    }
}

function handleEmployeeListError(contentPosition) {
    const row = `<tr><td colspan="7" class="text-center">Database connection error</td></tr>`;
    contentPosition.empty().append(row);
    $("#tableInfoContainer").addClass("d-none");
}

function changeTableInfo(responseData) {
    const currentPage = +responseData.currentPage;
    const pageSize = +responseData.pageSize;
    const totalResult = +responseData.totalResult;
    const totalPage = +responseData.totalPage;
    const firstRow = +responseData.firstRow;
    const lastRow = Math.min(totalResult, firstRow + pageSize - 1);
    $("#tableInfo").empty().append(`${firstRow}-${lastRow} of ${totalResult}`);

    const paginationContainer = $("#paginationContainer");
    paginationContainer.empty().append(`<li class="page-item"><button class="page-link">Previous</button></li>`);

    for (let i = 0; i < totalPage; i++) {
        paginationContainer.append(`<li class="page-item"><button class="page-link">${i + 1}</button></li>`);
    }

    paginationContainer.append(`<li id="currentPage" hidden>${currentPage}</li>`);
    paginationContainer.append(`<li class="page-item"><button class="page-link">Next</button></li>`);

    if (currentPage === 1) {
        paginationContainer.find("li:first-child").addClass("disabled");
    }
    if (currentPage === totalPage) {
        paginationContainer.find("li:last-child").addClass("disabled");
    }

    $(".page-item").each(function () {
        if ($(this).text().trim() === $("#currentPage").text()) {
            $(this).addClass("active");
        } else {
            $(this).removeClass("active");
        }
    });

    $(".page-link").click((event) => {
        event.preventDefault();
        const buttonIndex = +$(event.currentTarget).index(".page-link");
        let pageNumber = buttonIndex;

        if (buttonIndex === 0) {
            pageNumber = Math.max(1, currentPage - 1);
        } else if (buttonIndex === $(".page-link").length - 1) {
            pageNumber = Math.min($(".page-link").length - 2, currentPage + 1);
        }

        showEmployeeList(pageNumber);
    });
}

function deleteEmployee(thisRow) {
    const deleteEmployee = fetchData(URL_EMPLOYEE_DELETE, {
        employeeId: thisRow.attr("employeeId"),
    }, "post");

    deleteEmployee.done((responseData) => {
        handleDeleteEmployeeResponse(responseData, thisRow);
    });

    deleteEmployee.fail(() => {
        alert("Database connection error.");
    });
}

function handleDeleteEmployeeResponse(responseData, thisRow) {
    if (responseData.isSuccess) {
        thisRow.addClass("deleted");
        $(":last-child", thisRow).empty();
    } else {
        alert(`There was an error when deleting the employee with the name ${$(":nth-child(2)", thisRow).text()}.`);
    }
}
