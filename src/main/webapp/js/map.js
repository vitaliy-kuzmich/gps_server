MapWrapper = function (user) {
    var me = this;
    me.waypoints;
    me.user = user;
    me.dialogTitle = user.username;
    me.canv;
    me.dialogDiv;
    me.map = null;
    me.dialog = null;
    me.beginDateId;
    me.endDateId;
    me.mapOptions = {
        zoom: 4,
        center: new google.maps.LatLng(47.7699298, 33.9),
        mapTypeId: google.maps.MapTypeId.TERRAIN
    };
    var templ = $(".mapTemplate").clone();
    me.canv = templ.children(".mapCanvas")[0];
    me.dialogDiv = templ[0];
    me.beginDateId = "begin_date_" + me.dialogTitle;
    me.endDateId = "end_date_" + me.dialogTitle

    $(me.dialogDiv).attr("class", "mapTemplate_" + me.dialogTitle);

    $("body").append(templ);

    $(me.dialogDiv).attr("id", "mapTemplate_" + me.dialogTitle);
    $(me.canv).attr("id", "mapCanvas_" + me.dialogTitle);

    $(me.dialogDiv).find(".begin_date").attr("id", me.beginDateId);
    $(me.dialogDiv).find(".end_date").attr("id", me.endDateId);
    $(me.dialogDiv).find(".ok_btn").attr("id", "ok_btn_" + me.dialogTitle);
    me.showMap = function () {

        var map = new google.maps.Map(me.canv,
            me.mapOptions);


        me.dialog = $(me.dialogDiv).dialog({
            autoOpen: true,
            height: 400,
            width: 800,
            modal: false,
            title: me.dialogTitle,

            resize: function (event, ui) {
                google.maps.event.trigger(map, 'resize');
            },
            close: function (event, ui) {
                $(me.dialogDiv).parent("div[aria-describedby='mapTemplate_" + me.dialogTitle + "']")[0].remove();
            },
            open: function (event, ui) {
                var dat = new Date();
                dat.setHours(dat.getHours() - 1);
                $("#" + me.beginDateId).val((dat.getMonth() + 1) + "/" + dat.getDate() + "/" + dat.getFullYear() + " " + dat.getHours() + ":" + (dat.getMinutes() < 10 ? '0' : '') + dat.getMinutes());
                dat.setHours(dat.getHours() + 1);
                $("#" + me.endDateId).val((dat.getMonth() + 1) + "/" + dat.getDate() + "/" + dat.getFullYear() + " " + dat.getHours() + ":" + (dat.getMinutes() < 10 ? '0' : '') + dat.getMinutes());
                $("#create-user").button().on("click", function () {
                    dialog.dialog("open");
                });
                $(me.dialogDiv).find(".ok_btn").button().on("click", function () {
                    ajx("get-position?userId=" + me.user.id + "&fromPeriod=" + Date.parse($("#" + me.beginDateId).val()) + "&toPeriod=" + Date.parse($("#" + me.endDateId).val()), function (res) {
                        me.waypoints = res;
                    });
                });
            }
        });

        me.map = map;
        google.maps.event.trigger(map, 'resize');


    };
};