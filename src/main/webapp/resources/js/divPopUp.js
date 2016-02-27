$(document).ready(function () {
    $('.date').prop("min", function () {
        return new Date().toJSON().split('T')[0];
    });

    $("#form-booking").submit(function (e){
        e.preventDefault();
        $(":submit").attr("disabled", true);
        $.ajax({
            url: "/check-date",
            type: "GET",
            data: $("#form-booking").serialize(),
            success: function(data){
                if (data){
                    $("#is-free").html("<img class='image-ok' src='/resources/images/ok.gif'/>")
                } else {
                    $("#is-free").html("<img class='image-ok' src='/resources/images/error.jpg'/>")
                }
            }
        });
        $(":submit").removeAttr("disabled");
    });

    var on = 0;
    function loadPopup() {
        if (on == 0) {
            $("#back").css("opacity", "0.6");
            $("#popup").slideDown(500);
            $("#back").fadeIn(1500);
            on = 1;
        }
    }

    function off() {
        if (on == 1) {
            $("#popup").slideUp("normal");
            $("#back").fadeOut("normal");
            on = 0;
        }
    }

    $("a#showpopup").click(function () {
        loadPopup();
    });
    $("div#back").click(function () {
        off();
    });
    $("div.close").click(function () {
        off();
    });
    $("#loginForm").submit(function (e) {
        e.preventDefault();
        $.ajax({
            url: 'check-user',
            type: 'POST',
            data: $("#loginForm").serialize(),
            success: function (data) {
                if (data == 'logged') {
                    off();
                    setTimeout(function () {
                        location.reload();
                    }, 350);
                } else {
                    $("#ajax-error").text(data);
                }
            }
        });
    });
});