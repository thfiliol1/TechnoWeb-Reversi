$( document ).ready(function() {
    refresh();
});

function refresh() {
    setTimeout(gameIsStarted, 5000);
}

function gameIsStarted() {
    $.ajax({
        url: 'rest/game/started',
        success: function (data, textStatus, jqXHR) {
            if(data == true){
                window.location.replace("game");
            }
        }
    });
    refresh();
}

