package findHuman.server;

public class gameLogic {
	  gameListElements gle = new gameListElements();      //something got messed up here with the refresh
    public int players[] ={ };
    public String responces[];

    public String generateRandomQuestion(){
        //clear all previous
        return gle.getRandomQuestion();
    }

    public void generateResponces(){
        if(players.length==0){

        }
        else{
            for(int i=0; i<(players.length*2); i++){
                String temp = gle.getRandomResponce();
                //add temp to the list
            }
        }
    }

    public void addResponce(String re){
        //add re to list
    }

    //randomize list, but keep track of what answeres were bots.

    public String isCorrect(int guess){
        //if index of guess is player responce
            //gameMaster score ++;
            //return "Yes guess " + guess + " was a Human!";
        //else
        return "no guess " + guess + " was not a human";
    }


}
