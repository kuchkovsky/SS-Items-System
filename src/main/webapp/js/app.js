$('.sidenav').sidenav();

$(".delete-button").click(function(e) {
    e.preventDefault();
    $('.modal-delete').attr('data-id', $(this).attr('data-id'));
    $('.modal').modal();
});

$(".modal-delete").click(function() {
    $.ajax({
        type: "DELETE",
        url: "/user/items?" + $.param({"id": $(this).attr('data-id')}),
        success: function() {
            window.setTimeout(function () {
                location.reload();
            }, 300);
        },
        error: function() {
            $('#error-dialog').modal('open');
        }
    });
});
