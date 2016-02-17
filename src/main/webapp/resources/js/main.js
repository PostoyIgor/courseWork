$(document).ready(function () {
    //$("#test").click(function () {
    //    $.ajax({
    //        url: 'ajaxtest',
    //        dataType: 'json',
    //        success: function (data) {
    //            var i;
    //            for (i = 0; i < data.length; i++) {
    //                $("#rows")
    //                    .append("<div class='cell'> <img src='/resources/images/" + i + ".jpg'/>")
    //                    .append("Hotel: " + data[i].name + " in " + data[i].city + " city")
    //                    .append("<p>More Description</p>");
    //            }
    //        }
    //    });
    //});
    //$("#registerForm").submit(function (e) {
    //    e.preventDefault();
    //    var user = {};
    //    user["login"] = $("#login").val();
    //    user["firstName"] = $("#firstName").val();
    //    user["lastName"] = $("#lastName").val();
    //    user["email"] = $("#email").val();
    //    user["admin"] = $("input:checkbox").is(":checked")? 1:0;
    //    user["password"] = $("#password").val();
    //    $.ajax({
    //        contentType: 'application/json; charset=utf-8',
    //        url: $("#registerForm").attr("action"),
    //        type: "POST",
    //        dataType: 'json',
    //        data: JSON.stringify(user),
    //        error: function (e) {
    //            alert("ERROR" + e);
    //        }
    //    })
    //})
    //$("#registerForm").submit(function (e) {
    //    e.preventDefault();
    //    var form = $(this);
    //    var error = false;
    //    form.find('input, textarea').each( function(){
    //        if ($(this).val() == '') {
    //            error = true;
    //        }
    //        if (!error) {
    //            var data = form.serialize();
    //            $.ajax({
    //                type: 'POST',
    //                url: $("#registerForm").attr("action"),
    //                contentType: 'application/json; charset=utf-8',
    //                dataType: 'json',
    //                data: data,
    //                beforeSend: function(data) {
    //                    form.find('input[type="submit"]').attr('disabled', 'disabled');
    //                },
    //                success: function(data){
    //                    if (data['error']) {
    //                        alert(data['error']);
    //                    } else {
    //                        alert(' =)');
    //                    }
    //                },
    //                error: function (xhr, ajaxOptions, thrownError) {
    //                    alert(xhr.status);
    //                    alert(thrownError);
    //                },
    //                complete: function(data) {
    //                    form.find('input[type="submit"]').prop('disabled', false);
    //                }
    //            });
    //        }
    //        return false;
    //    });
    //})
});
