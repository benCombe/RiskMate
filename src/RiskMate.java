import BasicIO.*;
import GameStats.*;

import java.io.FileNotFoundException;

public class RiskMate {

    Player[] players;
    BasicForm startUp, form;
    boolean originalStart;

    private final static int MAX_PLAYERS = 5;
    int numPlayer;

    public RiskMate(){

        buildStartupForm();
        if (startUp.accept() == 0 && startUp.readInt("numPlayer") <= MAX_PLAYERS){
            numPlayer = startUp.readInt("numPlayer");
            players = new CustomPlayer[numPlayer];
            startUp.close();

            originalStart = true;
            newGame();
            buildForm();
            runProgram();
            form.close();
        }
        startUp.close();
    }

    private void runProgram(){
        boolean running = true;
        while (running) {

            int button = form.accept();
            switch (button){
                case 0: //New Game
                    newGame();
                    break;

                case 1: //Start Battle
                    new RiskRoller();
                    break;

                case 2: //Hand in cards
                    break;

                case 3: // Exit
                    running = false;
                    break;
            }

        }
    }

    private void newGame(){
        for (int i = 0; i < numPlayer; i++){
            try {
                players[i] = new CustomPlayer();
            }
            catch (FileNotFoundException e){

            }
        }
        for (Player p: players) {
            p.addStat("numTerr", 0);
            p.getStat("numTerr").setStatValue(42/numPlayer);

            p.addStat("numArmy", 0);
            p.getStat("numArmy").setStatValue(35 - (5*(numPlayer - 3)));
        }

        if (!originalStart) {
            for (int i = 1; i <= numPlayer; i++) {
                form.writeString("txt_player" + i, players[i - 1].getPlayerName());
                form.writeInt("player" + i + "_troops", players[i - 1].getStat("numArmy").getStatValue());
                form.writeInt("player" + i + "_ter", players[i - 1].getStat("numTerr").getStatValue());
            }
        }
    }

    private void addPlayer(){

    }

    private void buildStartupForm(){
        startUp = new BasicForm("Start", "Cancel");
        startUp.addTextField("numPlayer", "# of Players: ", 5);
        startUp.addRadioButtons("Classic", "option2", "option3");
        startUp.show();

    }

    private void buildForm(){
        form = new BasicForm("New Game", "Start Battle", "Hand In Cards", "Exit");
        form.setTitle("RiskMate");

        for (int i = 1; i <= numPlayer; i++){
            form.addTextField("txt_player" + i, 18);
            form.writeString("txt_player" + i, players[i-1].getPlayerName());
            form.addTextField("player" + i + "_troops", 5, form.getX("txt_player" + i) + 225, form.getY("txt_player" + i));
            form.addTextField("player" + i + "_ter", 5, form.getX("txt_player" + i) + 300, form.getY("txt_player" + i));
        }
        originalStart = false;

    }

    public static void main(String[] args) {
        new RiskMate();
    }

}
