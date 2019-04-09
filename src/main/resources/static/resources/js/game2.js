$( document ).ready(function() {
    playerCanPlay(true);
    refreshAndWait();
});

var endGame=false;

function refreshAndWait() {
    setTimeout(playerCanPlay, 5000,false);
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
                console.log("je peux jouer!");
            }
            else if(data == 0){
                $("#msg_info").text("Le joueur adverse est en train de jouer !");
                console.log("je ne peux pas jouer encore!");
                if(!oneCall) {
                    refreshAndWait();
                }
            }
            else{
                endGame = true;
                refreshGrid(-1);
                console.log("fin de la partie!");
               // isWinner();
            }
        }
    });
}

function play(id){
    console.log("click");
    console.log(id);
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
                            console.log("j'ai pose le pion!");
                            $("#msg_info").text("Le joueur adverse est en train de jouer !");
                            refreshGrid(1);
                            refreshAndWait();
                        }
                        else{
                            refreshGrid(0);
                            refreshAndWait();

                        }
                        //console.log(data);
                    }
                });
            }
            else{
                console.log("je ne peux pas jouer!");
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
            }
            else if(winner == 0) {
                $("#msg_info").attr("class", "alert alert-primary");
                $("#msg_info").text("Vous vous êtes neutralisés ! Match nul !");
            }
            else{
                $("#msg_info").attr("class", "alert alert-danger");
                $("#msg_info").text("Vous avez perdu !");
            }

        }
    });
}