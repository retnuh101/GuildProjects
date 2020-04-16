// $(function () {
//     var token = $("meta[name='_csrf']").attr("content");
//     var header = $("meta[name='_csrf_header']").attr("content");
//     $(document).ajaxSend(function(e, xhr, options) {
//         xhr.setRequestHeader(header, token);
//     });
// });
$(document).ready(function() {

    $('#password, #confirm_password').on('keyup', function () {
        if ($('#password').val() == $('#confirm_password').val()) {
            $('#messagePassword').html('The passwords match.').css('color', 'green');
        } else
            $('#messagePassword').html('The passwords do not match.').css('color', 'red');
    });
    $('.modal').on("hidden.bs.modal", function (e) { //fire on closing modal box
        if ($('.modal:visible').length) { // check whether parent modal is opend after child modal close
            $('body').addClass('modal-open'); // if open mean length is 1 then add a bootstrap css class to body of the page
        }
    });
    $('#employee-table').DataTable( {
        destroy: true,
        paging: false,
        initComplete: function () {
            this.api().columns(2).every( function () {
                var column = this;
                var select = $('<select class="form-control"><option value="">Name</option></select>')
                    .appendTo( $(column.header()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );

                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
            this.api().columns(3).every( function () {
                var column = this;
                var select = $('<select class="form-control"><option value="">Job Role</option></select>')
                    .appendTo( $(column.header()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );

                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );

                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
    $('#location-table').DataTable();
    $('#organization-table').DataTable();

    $('#power-table').DataTable();
    $('#super-table').DataTable();

} );
function openPowerModal(id) {
    $.ajax({
        url:"/power/" + id,
        success: function(data) {
            $("#powerModalHolder").html(data);
            $("#powerModal").modal("show");
        }
    })
}

function openSuperModal(id) {
    $.ajax({
        url:"/super/" + id,
        success: function(data) {
            $("#superModalHolder").html(data);
            $("#superModal").modal("show");
        }
    })
}
function openSightingModal(id) {
    $.ajax({
        url:"/sighting/" + id,
        success: function(data) {
            $("#sightingModalHolder").html(data);
            $("#sightingModal").modal("show");
        }
    })
}
function openLocationModal(id) {
    $.ajax({
        url:"/location/" + id,
        success: function(data) {
            $("#locationModalHolder").html(data);
            $("#locationModal").modal("show");
        },
        data : {
            "${_csrf.parameterName}" : "${_csrf.token}"
        }
    })
}
function openEventModal(id) {
    $.ajax({
        url:"/event/" + id,
        success: function(data) {
            $("#eventModalHolder").html(data);
            $("#eventModal").modal("show");
        },
        data : {
            "${_csrf.parameterName}" : "${_csrf.token}"
        }
    })
}

function form_submit() {
    document.getElementById("add-location-form").submit();
}




function confirm(heading, question, cancelButtonTxt, okButtonTxt, callback) {

    var confirmModal =
        $('<div class="modal hide fade">' +
            '<div class="modal-header">' +
            '<a class="close" data-dismiss="modal" >&times;</a>' +
            '<h3>' + heading +'</h3>' +
            '</div>' +

            '<div class="modal-body">' +
            '<p>' + question + '</p>' +
            '</div>' +

            '<div class="modal-footer">' +
            '<a href="#" class="btn" data-dismiss="modal">' +
            cancelButtonTxt +
            '</a>' +
            '<a href="#" id="okButton" class="btn btn-primary">' +
            okButtonTxt +
            '</a>' +
            '</div>' +
            '</div>');

    confirmModal.find('#okButton').click(function(event) {
        callback();
        confirmModal.modal('hide');
    });

    confirmModal.modal('show');
};
jQuery.validator.addMethod("greaterThan",
    function(value, element, params) {

        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) > new Date($(params).val());
        }

        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    },'Must be greater than {0}.');



