/**
 *
 */
$(function () {
    $('.direction-button').each(function (index, elt) {
        $(this).on("click", function () {
            var targetQuote = $(this).attr('targetIndex');
            loadQuote(targetQuote);
        });
    });
    refreshButtonState();
});

function loadQuote(targetQuote) {
    $.ajax({
        url: '/rest/quotes/' + targetQuote,
        dataType: 'json'
    }).done(onLoadSuccess).fail(onLoadFailure);
}

function onLoadSuccess(data) {
    $('#number').text(data.number);
    $('#content').text(data.quote.content);
    $('#author').text(data.quote.author);
    $.each(data.directions, function (index, element) {
        var button = $('#' + element.id);
        button.attr('enabled', element.enabled);
        button.attr('targetIndex', element.indexOfDirection);
    });
    refreshButtonState();
}

function refreshButtonState() {
    $('.direction-button').each(function (index, elt) {
        $(this).prop('disabled', $(this).attr('enabled') == "false");
    });
}

function onLoadFailure(jqXHR, textStatus, errorThrow) {
    $('#content').text(errorThrown);
    $('#author').text(textStatus);
    // activer seulement le premier
}