$(document).ready(reloadTable());

function reloadTable() {
    $("#userTable").empty();
    fetch('api/admin/').then(
        response => {
            response.json().then(
                data => {
                    data.forEach((user) => {
                        $('#userTable').append($('<tr>').append(
                            $('<td>').append($('<span>')).text(user.id), //Установить текстовое содержимое для всех элементов <span>
                            $('<td>').append($('<span>')).text(user.name), // firstname
                            $('<td>').append($('<span>')).text(user.password),
                            $('<td>').append($('<span>')).text(user.roles.map(role => role.role)),
                            $('<td>').append($('<button>').text("Edit").attr({ //Метод attr () устанавливает или возвращает атрибуты и значения выбранных элементов.
                                "type": "button",  // .attr(attribute,value)
                                "class": "btn btn-info edit",
                                "data-toggle": "modal",
                                "data-target": "#myModalEdit",
                            }).data("user", user)), //data(name,value) возращает данные из выбранного элемента(здесь button)
                            $('<td>').append($('<button>').text("Delete").attr({
                                "type": "button",
                                "class": "btn btn-danger delete",
                                "data-toggle": "modal",
                                "data-target": "#myModalDelete",
                            }).data("user", user))
                        ))
                    })
                }
            )
        }
    )
}

// u.roles.forEach((lol) => {
//     temp += lol.role + " ";
// });




$('.addNewUser').click(function () {
    $('#allUsers').trigger('click');
    let formData = $('.formAddUser').serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/add',
        data: formData,
        success: function () {
            $('.formAddUser')[0].reset();
            reloadTable();
        }
    });
});

$(document).on("click", ".edit", function () {
    let user = $(this).data('user');
    $('#userNameInput').val(user.name);
    $('#passwordNameInput').val(user.password);
    $('#idInput').val(user.id);
    $('#roleInput').val(user.roles.map(role => role.role));
});

$(document).on("click", ".editUser", function () {
    let formData = $(".formEditUser").serializeArray();
    $.ajax({
        type: 'PUT',
        url: '/api/admin/edit',
        data: formData,
        success: function () {
           reloadTable();
        }
    });
});

$(document).on("click", ".delete", function () {
    let user = $(this).data('user');

    $('#userName').val(user.name);
    $('#password').val(user.password);
    $('#id').val(user.id);
    $(document).on("click", ".deleteUser", function () {
        $.ajax({
            type: 'DELETE',
            url: '/api/admin/delete',
            data: {id: $('#id').val()},
            success: function () {
                reloadTable();
            }
        });
    });

});


