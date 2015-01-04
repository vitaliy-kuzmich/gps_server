function ajx(url, callback) {
    $.ajax({
        url: "/GPS/" + url,
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            if (token.length > 0)
                xhr.setRequestHeader(header, token);
        },
        success: callback,
        error: function (err) {
            alert("can not process request " + err);
            console.log(err);
        }
    });

}

function ReservationsViewModel() {
    var self = this;
    self.users = ko.observableArray();
    //initializing
    $("#create-user").button();
    ajx("all-users", function (data) {
        data.forEach(function (entry) {
            self.users.push(entry);
        });

    });

    self.deleteUser = function (user) {
        ajx("delete-user?userId=" + user.id, function (res) {
            self.users.remove(user);
            self.newUserDialog.dialog("close");
        });
    };
    self.addNewUser = function (event) {

        ajx(("add-user?" + $("#addUserForm").serialize()) + "&role=ROLE_USER&enabled=true", function (addedUser) {
            self.users.push(addedUser);
            self.newUserDialog.dialog("close");
        });
        if (event) {
            event.preventDefault();
        }
    };

    self.showOnMap = function (user) {
        var wrp = new MapWrapper(user);
        wrp.showMap();
    };
    self.openDialog = function () {
        self.newUserDialog.dialog("open");
    };
    self.newUserDialog = $("#addUserForm").dialog({
        autoOpen: false,
        height: 250,
        width: 300,
        modal: true,
        buttons: {
            Okay: self.addNewUser,
            Cancel: function () {
                self.newUserDialog.dialog("close");
            }
        }
    });
    self.mapDialog = null;
}
$(function () {

    ko.applyBindings(new ReservationsViewModel());

});