function getBaseUrl() {
    let url = window.location;
    return url.protocol + "//" + url.host + "/";
}

function reloadPage() {
    window.location.reload();
}

function logout() {
    window.location.href = getBaseUrl() + "library/ui/logout";
}

function removeUserTableBody() {
    $('#userTable tbody').empty();
}

function removeBookTableBody() {
    $('#bookTable tbody').empty();
}

function removeDeliveryBookTableBody() {
    $('#deliveryBookTable tbody').empty();
}

function removePendingBookTableBody() {
    $('#pendingBookTable tbody').empty();
}

function removeStatus() {
    $('#status').empty();
}

function removeUserTable() {
    $('#userTable').hide();
}

function hideDiveImage(){
    $('#divImage').hide();
}

function removeBookTable() {
    $('#bookTable').hide();
}

function removeDeliveryBookTable() {
    $('#deliveryBookTable').hide();
}

function removePendingBookTable() {
    $('#pendingBookTable').hide();
}

function hideSearchArea() {
    $('#searchForm').hide();
}

function showUserTable() {
    $('#userTable').show();
}

function showBookTable() {
    $('#bookTable').show();
}

function showDeliveryBookTable() {
    $('#deliveryBookTable').show();
}

function showPendingBookTable() {
    $('#pendingBookTable').show();
}

function showSearchArea() {
    $('#searchForm').show();
}


$(document).ready(
    removeUserTable(),
    removeStatus(),
    removeBookTable(),
    removePendingBookTable(),
    hideSearchArea(),
    removeDeliveryBookTable(),
    $.ajax({
        url: getBaseUrl() + 'library/api/user/getUserInfo',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            $('#usernameLi').append(` <a class="nav-link">${data.username}</a>`)
        }
    }),
    $.ajax({
        url: getBaseUrl() + 'library/api/book/getStatus',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            let event_data = '';
            $.each(data, function (index, value) {
                event_data +=`<option value="${value.statusId}">${value.statusName}</option>`;
            });
            $('#status').append(event_data);
            $('#statusUpdate').append(event_data);
        }
    })
);

function remove(){
    removeUserTableBody();
    removeBookTableBody();
    removeDeliveryBookTableBody();
    removePendingBookTableBody();
    hideDiveImage();
}

function getUsers(){
    document.getElementById('searchField').dataset.options='user';
    document.getElementById('searchField').value = '';
    remove();
    //
    removeBookTable();
    removePendingBookTable();
    hideSearchArea();
    removeDeliveryBookTable();
    showUserTable();
    showSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/user/getAllUser',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.username + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' + value.fullName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.registerDate + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.phoneNumber + '</td>';
                event_data += '      <td>';
                event_data += '         <a onclick="editUser(' + value.userId + ')" data-toggle="modal" data-target="#updateModalUser" class="btn btn-primary" >Dəyiş</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '         <a class="btn btn-danger" onclick="deleteUser(' + value.userId + ')" data-toggle="modal" data-target="#deleteModalUser">Sil</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '      </td>';
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#userTable").append(event_data);
        }
    })

}

function getAddModal() {
    document.getElementById('username').value = '';
    document.getElementById('password').value = '';
    $('#addModalUser').modal({
         backdrop: 'static'
    });
}

function addUser(){

    let username = $("#username").val();
    let password = $("#password").val();
    let fullName = $("#fullName").val();
    let phone = $("#phone").val();
    let email = $("#email").val();
    let dob = $("#dob").val();

    $.ajax({
        url: getBaseUrl() + 'library/api/user/getControlUsername',
        type: 'GET',
        data :'username='+username,
        dataType: 'JSON',
        success: function (data) {
            if(data === true || data === 'true' || data === "true"){
                alert('Bu istifadəçi adı artıq mövcuddur!');
            }else{
                if(password === '' || fullName === '' || dob === ''){
                    alert('Məlumatlar tam deyil!')
                }else{
                    let user = `username=${username}&password=${password}&fullName=${fullName}&email=${email}&phoneNumber=${phone}&dateOfBirth=${dob}`;

                    $.ajax({
                        url: getBaseUrl() + 'library/api/user/addUser',
                        method:'GET',
                        data: user,
                        dataType: 'JSON',
                        success: function (data) {
                            if(data === true){
                                document.getElementById('username').value = '';
                                document.getElementById('password').value = '';
                                document.getElementById('fullName').value = '';
                                document.getElementById('email').value = '';
                                document.getElementById('dob').value = '';
                                document.getElementById('phone').value = '';
                                $('#addModalUser').modal('hide');
                                getUsers();
                            }
                        },
                        error:function (){
                            alert('error');
                        }
                    });
                }

            }
        }
    });


}

function editUser(id) {
    $.ajax({
        url: getBaseUrl() + 'library/api/user/getUserById',
        data: 'userId=' + id,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            $('#usernameUpdate').val(data.username);
            $('#userIdUpdate').val(data.userId);
            $('#fullNameUpdate').val(data.fullName);
            $('#emailUpdate').val(data.email);
            $('#phoneUpdate').val(data.phoneNumber);
            $('#dobUpdate').val(data.dateOfBirth);
        }
    });
}

function updateUser(){
    let username = $("#usernameUpdate").val();
    let fullName = $("#fullNameUpdate").val();
    let phone = $("#phoneUpdate").val();
    let email = $("#emailUpdate").val();
    let dob = $("#dobUpdate").val();

    if(fullName === '' || dob === ''){
        alert('Məlumatlar tam deyil!')
    }else{
        let user = `userId=${$('#userIdUpdate').val()}&username=${username}&fullName=${fullName}&email=${email}&phoneNumber=${phone}&dateOfBirth=${dob}`;

        $.ajax({
            url: getBaseUrl() + 'library/api/user/updateUser',
            method:'GET',
            data: user,
            dataType: 'JSON',
            success: function (data) {
                if(data === true){
                    $('#updateModalUser').modal('hide');
                    getUsers();
                }
            },
            error:function (){
                alert('error');
            }
        });
    }
}

function deleteUser(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/user/deleteUser',
        method:'GET',
        data: "userId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getUsers();
            }
        },
        error:function (){
            alert('error');
        }
    });
}


function getBooks(){
    document.getElementById('searchField').value = '';
    document.getElementById('searchField').dataset.options='book';
    remove();
    //
    removeUserTable();
    removePendingBookTable();
    hideSearchArea();
    removeDeliveryBookTable();
    showBookTable();
    showSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/book/getAllBook',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            
            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' + value.author + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.createDate + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.status.statusName + '</td>';
                event_data += '      <td>';
                event_data += '         <a onclick="editBook(' + value.bookId + ')" data-toggle="modal" data-target="#updateModalBook" class="btn btn-primary" >Dəyiş</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '         <a class="btn btn-danger" onclick="deleteBook(' + value.bookId + ')">Sil</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '      </td>';
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#bookTable").append(event_data);
        }
    })

}

function getAddBookModal() {
    document.getElementById('bookName').value = '';
    document.getElementById('about').value = '';
    document.getElementById('author').value = '';
    document.getElementById('createDate').value = '';
    $('#addModalBook').modal({
        backdrop: 'static'
    });
}

function addBook(){
    let bookName = $("#bookName").val();
    let author = $("#author").val();
    let about = $("#about").val();
    let status = $("#status").val();
    let createDate = $("#createDate").val();
    if (bookName === '' || author === '' || createDate === ''){
        alert('Məlumatlar tam deyil!');
    }else{
        let book = `bookName=${bookName}&author=${author}&about=${about}&status=${status}&createDate=${createDate}`;

        $.ajax({
            url: getBaseUrl() + 'library/api/book/addBook',
            method:'GET',
            data: book,
            dataType: 'JSON',
            success: function (data) {
                if(data === true){
                    document.getElementById('bookName').value = '';
                    document.getElementById('about').value = '';
                    document.getElementById('author').value = '';
                    document.getElementById('createDate').value = '';
                    $('#addModalBook').modal('hide');
                    getBooks();
                }
            },
            error:function (){
                alert('error');
            }
        });
    }

}

function editBook(id) {
    $.ajax({
        url: getBaseUrl() + 'library/api/book/getBookInfo',
        data: 'bookId=' + id,
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            $('#bookNameUpdate').val(data.bookName);
            $('#bookIdUpdate').val(data.bookId);
            $('#authorUpdate').val(data.author);
            $('#aboutUpdate').val(data.about);
            $('#createDateUpdate').val(data.createDate);
            $('#statusUpdate').val(data.status.statusId)
        }
    });
}

function updateBook(){
    let bookName = $("#bookNameUpdate").val();
    let author = $("#authorUpdate").val();
    let about = $("#aboutUpdate").val();
    let status = $("#statusUpdate").val();
    let createDate = $("#createDateUpdate").val();
    if (bookName === '' || author === '' || createDate === ''){
        alert('Məlumatlar tam deyil!');
    }else{
        let book = `bookId=${$("#bookIdUpdate").val()}&bookName=${bookName}&author=${author}&about=${about}&status=${status}&createDate=${createDate}`;

        $.ajax({
            url: getBaseUrl() + 'library/api/book/updateBook',
            method:'GET',
            data: book,
            dataType: 'JSON',
            success: function (data) {
                if(data === true){
                    $('#updateModalBook').modal('hide');
                    getBooks();
                }
            },
            error:function (){
                alert('error');
            }
        });
    }


}

function deleteBook(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/book/deleteBook',
        method:'GET',
        data: "bookId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getBooks();
            }
        },
        error:function (){
            alert('error');
        }
    });
}


function getDeliveryBooks(){
    remove();
    //
    removeUserTable();
    removeBookTable();
    removePendingBookTable();
    hideSearchArea();
    showDeliveryBookTable();
    hideSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/getDeliveryTransactions',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            
            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.books.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' + value.user.fullName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.trDate + '</td>';
                event_data += '      <td>';
                event_data += '         <a class="btn btn-danger" onclick="deleteDeliveryBook(' + value.trId + ')">Ləğv et</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '      </td>';
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#deliveryBookTable").append(event_data);
        }
    })
}

function deleteDeliveryBook(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/deleteTransaction',
        method:'GET',
        data: "trId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getDeliveryBooks();
            }
        },
        error:function (){
            alert('error');
        }
    });
}

function getPendingBooks(){
    remove();
    //
    removeUserTable();
    removeBookTable();
    removeDeliveryBookTable();
    hideSearchArea();
    showPendingBookTable();
    hideSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/getPendingTransactions',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {
            
            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.books.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' + value.user.fullName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.trDate + '</td>';
                event_data += '      <td>';
                event_data += '         <a class="btn btn-danger" onclick="deletePendingBook(' + value.trId + ')">Sil</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '         <a class="btn btn-success" onclick="accessPendingBook(' + value.trId + ')">Təsdiqlə</a>';
                event_data += '      </td>';
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#pendingBookTable").append(event_data);
        }
    })

}

function deletePendingBook(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/deleteTransaction',
        method:'GET',
        data: "trId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getPendingBooks();
            }
        },
        error:function (){
            alert('error');
        }
    });
}

function accessPendingBook(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/markTransactionDelivery',
        method:'GET',
        data: "trId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getPendingBooks();
            }
        },
        error:function (){
            alert('error');
        }
    });
}


function search(){
    let value = document.getElementById('searchField').dataset.options;
    if(value === 'user'){
        remove();
        //
        removeBookTable();
        removePendingBookTable();
        removeDeliveryBookTable();
        showUserTable();
        $.ajax({
            url: getBaseUrl() + 'library/api/user/getUserByUsername',
            type: 'GET',
            data :'username='+$('#searchField').val(),
            dataType: 'JSON',
            success: function (data) {
                var event_data = '';
                $.each(data, function (index, value) {
                    event_data += '<tbody class="ui-widget-content">';
                    event_data += '   <tr>';
                    event_data += '      <td>&nbsp;&nbsp;' + value.username + '</td>';
                    event_data += '      <td>&nbsp;&nbsp;' + value.fullName + '</td>';
                    event_data += '      <td>&nbsp;&nbsp;' +value.registerDate + '</td>';
                    event_data += '      <td>&nbsp;&nbsp;' +value.phoneNumber + '</td>';
                    event_data += '      <td>';
                    event_data += '         <a onclick="editUser(' + value.userId + ')" data-toggle="modal" data-target="#updateModalUser" class="btn btn-primary" >Dəyiş</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    event_data += '         <a class="btn btn-danger" onclick="deleteUser(' + value.userId + ')" data-toggle="modal" data-target="#deleteModalUser">Sil</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    event_data += '      </td>';
                    event_data += '   </tr>';
                    event_data += '</tbody>';
                });
                $("#userTable").append(event_data);
            }
        })
    }else{
        remove();
        //
        removeUserTable();
        removePendingBookTable();
        removeDeliveryBookTable();
        showBookTable();
        $.ajax({
            url: getBaseUrl() + 'library/api/book/getBookByName',
            type: 'GET',
            data:'bookName='+$('#searchField').val(),
            dataType: 'JSON',
            success: function (data) {
                
                var event_data = '';
                $.each(data, function (index, value) {
                    event_data += '<tbody class="ui-widget-content">';
                    event_data += '   <tr>';
                    event_data += '      <td>&nbsp;&nbsp;' + value.bookName + '</td>';
                    event_data += '      <td>&nbsp;&nbsp;' + value.author + '</td>';
                    event_data += '      <td>&nbsp;&nbsp;' +value.createDate + '</td>';
                    event_data += '      <td>&nbsp;&nbsp;' +value.status.statusName + '</td>';
                    event_data += '      <td>';
                    event_data += '         <a onclick="editBook(' + value.bookId + ')" data-toggle="modal" data-target="#updateModalBook" class="btn btn-primary" >Dəyiş</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    event_data += '         <a class="btn btn-danger" onclick="deleteBook(' + value.bookId + ')">Sil</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    event_data += '      </td>';
                    event_data += '   </tr>';
                    event_data += '</tbody>';
                });
                $("#bookTable").append(event_data);
            }
        })
    }
}

document.getElementById("navbarDropdownProfile").onclick = function (){
    logout();
}

function getBooksUser(){
    document.getElementById('searchField').dataset.options='book';
    remove();
    //
    removePendingBookTable();
    hideSearchArea();
    removeDeliveryBookTable();
    showBookTable();
    showSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/book/getAllBook',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {

            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' + value.author + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.createDate + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.status.statusName + '</td>';

                if(value.status.statusId === 1){
                    event_data += '      <td>';
                    event_data += '         <a onclick="markBook(' + value.bookId + ')" class="btn btn-primary" >Rezerv et</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    event_data += '      </td>';
                }else{
                    event_data += '      <td>';
                    event_data += '      </td>';
                }
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#bookTable").append(event_data);
        }
    })

}

function markBook(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/addTransaction',
        method:'GET',
        data: "bookId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getBooksUser();
            }
        },
        error:function (){
            alert('error');
        }
    });
}

function getPendingBooksUser(){
    remove();
    //
    removeBookTable();
    removeDeliveryBookTable();
    hideSearchArea();
    showPendingBookTable();
    hideSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/getPendingTransactionByUserId',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {

            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.books.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.trDate + '</td>';
                event_data += '      <td>';
                event_data += '         <a class="btn btn-danger" onclick="deletePendingBookUser(' + value.trId + ')">Ləğv et</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '      </td>';
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#pendingBookTable").append(event_data);
        }
    })

}

function getDeliveryBooksUser(){
    remove();
    //
    removeBookTable();
    removePendingBookTable();
    hideSearchArea();
    showDeliveryBookTable();
    hideSearchArea();
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/getDeliveryTransactionByUserId',
        type: 'GET',
        dataType: 'JSON',
        success: function (data) {

            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.books.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.trDate + '</td>';
                event_data += '      <td>';
                event_data += '         <a class="btn btn-danger" onclick="deleteDeliveryBookUser(' + value.trId + ')">Təhvil vermək</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                event_data += '      </td>';
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#deliveryBookTable").append(event_data);
        }
    })
}

function deleteDeliveryBookUser(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/deleteTransaction',
        method:'GET',
        data: "trId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getDeliveryBooksUser();
            }
        },
        error:function (){
            alert('error');
        }
    });
}

function deletePendingBookUser(id){
    $.ajax({
        url: getBaseUrl() + 'library/api/tr/deleteTransaction',
        method:'GET',
        data: "trId="+id,
        dataType: 'JSON',
        success: function (data) {
            if(data === true){
                getPendingBooksUser();
            }
        },
        error:function (){
            alert('error');
        }
    });
}

function searchUser(){

    remove();
    //
    removeUserTable();
    removePendingBookTable();
    removeDeliveryBookTable();
    showBookTable();
    $.ajax({
        url: getBaseUrl() + 'library/api/book/getBookByName',
        type: 'GET',
        data:'bookName='+$('#searchField').val(),
        dataType: 'JSON',
        success: function (data) {

            var event_data = '';
            $.each(data, function (index, value) {
                event_data += '<tbody class="ui-widget-content">';
                event_data += '   <tr>';
                event_data += '      <td>&nbsp;&nbsp;' + value.bookName + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' + value.author + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.createDate + '</td>';
                event_data += '      <td>&nbsp;&nbsp;' +value.status.statusName + '</td>';

                if(value.status.statusId === 1){
                    event_data += '      <td>';
                    event_data += '         <a onclick="markBook(' + value.bookId + ')" class="btn btn-primary" >Rezerv et</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                    event_data += '      </td>';
                }else{
                    event_data += '      <td>';
                    event_data += '      </td>';
                }
                event_data += '   </tr>';
                event_data += '</tbody>';
            });
            $("#bookTable").append(event_data);
        }
    })
}