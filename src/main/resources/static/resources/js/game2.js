$( document ).ready(function() {
    playerCanPlay(true);
    refreshAndWait();
});

var endGame=false;

function refreshAndWait() {
    setTimeout(playerCanPlay, 1000,false);
}

function playerCanPlay(oneCall){
    $.ajax({
        url: 'rest/game/canPlay',
        success: function (data, textStatus, jqXHR) {
            if(data == 1){
                if(!oneCall) {
                    refreshGrid(0);
                }
                else{
                    $("#msg_info").text("A vous de jouer ! (jetons: blanc)");
                }
            }
            else if(data == 0){
                $("#msg_info").text("Le joueur adverse est en train de jouer !");
                if(!oneCall) {
                    refreshAndWait();
                }
            }
            else{
                endGame = true;
                refreshGrid(-1);
            }
        }
    });
}

function play(id){
    $.ajax({
        url: 'rest/game/canPlay',
        success: function (data, textStatus, jqXHR) {
            if(data == 1){
                $.ajax({
                    url: 'rest/game/play',
                    data:{
                        idBox: id
                    },
                    success: function (data, textStatus, jqXHR) {
                        if(data == -1){
                            alert("Cette case ne peut pas être jouée !")
                        }
                        else if (data == 1){
                            $("#msg_info").text("Le joueur adverse est en train de jouer !");
                            refreshGrid(1);
                            refreshAndWait();
                        }
                        else{
                            refreshGrid(0);
                            refreshAndWait();
                        }
                    }
                });
            }
            else if(data == -1){
                endGame = true;
                refreshGrid(0);
            }
        }
    });
}

function refreshGrid(changePlayer){
    $.ajax({
        url: 'game/grid',
        success: function (data, textStatus, jqXHR) {
            $("#content-displayed").html(data);
            if(changePlayer == 1)
                $("#msg_info").text("Le joueur adverse est en train de jouer !");
            else if(changePlayer == 0)
                $("#msg_info").text("A vous de jouer ! (jetons: blanc)");
            if(endGame){
                isWinner();
            }
        }
    });
}

function isWinner(){
    $.ajax({
        url: 'rest/game/winner',
        success: function (winner, textStatus, jqXHR) {
            if(winner == 1) {
                $("#msg_info").attr("class", "alert alert-success");
                $("#msg_info").text("Bravo vous avez gagné !");
                $("#msg_info").append("<a class=\"btn btn-primary\" style=\"margin-left: 25%;\" href=\"/main\" role=\"button\">Nouvelle partie</a>");
            }
            else if(winner == 0) {
                $("#msg_info").attr("class", "alert alert-primary");
                $("#msg_info").text("Vous vous êtes neutralisés ! Match nul !");
                $("#msg_info").append("<a class=\"btn btn-primary\" style=\"margin-left: 25%;\" href=\"/main\" role=\"button\">Nouvelle partie</a>");
            }
            else{
                $("#msg_info").attr("class", "alert alert-danger");
                $("#msg_info").text("Vous avez perdu !");
                $("#msg_info").append("<a class=\"btn btn-primary\" style=\"margin-left: 25%;\" href=\"/main\" role=\"button\">Nouvelle partie</a>");
            }
        }
    });
}