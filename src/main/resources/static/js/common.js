const ROLES = ['ADMIN', 'USER'];

$(function () {
    console.log("email:");
    console.log($.cookie('email'));
    let roles = $.cookie('roles').split('|');
    console.log("roles:");
    console.log(roles);
    $('#navbar-email').text($.cookie('email'));
    $('#navbar-roles').text(roles.join(' '));
    if (roles.includes("ADMIN")) {
        $('#v-pills-admin-tab').removeClass('d-none');
        $('#v-pills-admin-tab').addClass('active');
        $('#v-pills-admin').tab('show');
        updateUserTable();
        $('#save').on('click', saveUser);
        $('#edit').on('click', event => editUser(event));
        $('#delete').on('click', event => deleteUser(event));
    }
    if (roles.includes("USER")) {
        $('#v-pills-user-tab').removeClass('d-none');
        if (!roles.includes("ADMIN")) {
            $('#v-pills-user-tab').addClass('active');
            $('#v-pills-user').tab('show');
        }
        showCurrentUser();
    }
})

function saveUser(e) {
    e.preventDefault();
    let newUser = {
        firstName: $('#add-first-name').val(),
        lastName: $('#add-last-name').val(),
        age: $('#add-age').val(),
        email: $('#add-email').val(),
        password: $('#add-password').val(),
        roles: []
    };
    $('#add-roles').find('option:selected').each((i, option) => {
        newUser['roles'].push({'name': $(option).val()});

    });
    console.log("user:");
    console.log(newUser);
    $.ajax({
        url: '/admin/add',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(newUser),
        complete: function (data) {
            $('#nav-users-tab').tab('show')
            updateUserTable();
        }
    });
}

function editUser(e) {
    e.preventDefault();
    let editUser = {
        id: $('#edit-id').val(),
        firstName: $('#edit-first-name').val(),
        lastName: $('#edit-last-name').val(),
        age: $('#edit-age').val(),
        email: $('#edit-email').val(),
        password: $('#edit-password').val(),
        roles: []
    };
    $('#edit-roles').find('option:selected').each((i, option) => {
        editUser['roles'].push({'name': $(option).val()});

    });
    console.log("user:");
    console.log(editUser);
    $.ajax({
        url: '/admin/edit',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(editUser),
        complete: function (data) {
            $('#edit-modal').modal('hide');
            updateUserTable();
        }
    });
}

function deleteUser(e) {
    e.preventDefault();
    const id = $('#delete-id').val();
    $.ajax({
        url: '/admin/delete/' + id,
        type: 'DELETE',
        complete: function (data) {
            $('#delete-modal').modal('hide');
            updateUserTable();
        }
    });
}

function editUserForm(id) {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/admin/user/' + id,
        contentType: 'application/json',
        success: function (user) {
            console.log("user:");
            console.log(user);
            $('#edit-id').val(user.id);
            $('#edit-first-name').val(user.firstName);
            $('#edit-last-name').val(user.lastName);
            $('#edit-age').val(user.age);
            $('#edit-email').val(user.email);
            $('#edit-password').val(user.password);
            const editRoles = $('#edit-roles');
            const userRoles = user.roles.map(r => r.name);
            console.log("userRoles:");
            console.log(userRoles);
            editRoles.empty();
            ROLES.forEach(role => editRoles.append("<option value='" + role + "'"
                + (userRoles.includes(role) ? " selected" : "") + ">"
                + role + "</option>"));
            $('#edit-modal').modal('show');
        }
    })
}

function deleteUserForm(id) {
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: '/admin/user/' + id,
        success: function (user) {
            $('#delete-id').val(user.id);
            $('#delete-first-name').val(user.firstName);
            $('#delete-last-name').val(user.lastName);
            $('#delete-age').val(user.age);
            $('#delete-email').val(user.email);
            const deleteRoles = $('#delete-roles');
            const userRoles = user.roles.map(r => r.name);
            console.log("userRoles:");
            console.log(userRoles);
            deleteRoles.empty();
            ROLES.forEach(role => deleteRoles.append("<option value='" + role + "'"
                + (userRoles.includes(role) ? " selected" : "") + ">"
                + role + "</option>"));
            $('#delete-modal').modal('show');
        }
    })
}

function updateUserTable() {
    $('#users').empty();
    $.ajax({
        type: 'GET',
        url: '/admin/users',
        success: function (users) {
            users.forEach(user =>
                $('#users')
                    .append("<tr> \
                                    <td>" + user.id + "</td> \
                                    <td>" + user.firstName + "</td> \
                                    <td>" + user.lastName + "</td> \
                                    <td>" + user.age + "</td> \
                                    <td>" + user.email + "</td> \
                                    <td>" + user.roles.map(role => role.name).join(' ') + "</td> \
                                    <td><button type='button' class='btn btn-info' onclick='editUserForm(" + user.id + ")'>Edit</button></td> \
                                    <td><button type='button' class='btn btn-danger' onclick='deleteUserForm(" + user.id + ")'>Delete</button><td> \
                             </tr>"))
        }
    });
}

function showCurrentUser() {
    $.ajax({
        type: 'GET',
        url: '/current',
        success: function (currentUser) {
            $('#logged')
                .append("<tr> \
                               <td>" + currentUser.id + "</td> \
                               <td>" + currentUser.firstName + "</td> \
                               <td>" + currentUser.lastName + "</td> \
                               <td>" + currentUser.age + "</td> \
                               <td>" + currentUser.email + "</td> \
                               <td>" + currentUser.roles.map(role => role.name).join(' ') + "</td> \
                        </tr>")
        }
    });
}