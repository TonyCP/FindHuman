import java.util.ArrayList;
import java.util.List;

public class gameLogic implements Runnable{
    gameListElements gle = new gameListElements();      //something got messed up here with the refresh
    public List<player> pList = new ArrayList<player>();
    public List<Responce> responces = new ArrayList<Responce>(); // a "responce" is if it's human and the message

    public String generateRandomQuestion(){
        return gle.getRandomQuestion();
    }

    public void generateBotResponces(){
        if(pList.size()==0){
//don't know if this should really be a thing? 
        }
        else{
            for(int i=0; i<((pList.size()-1)*2); i++){
                String temp = gle.getRandomResponce();
                Responce bots = new Responce(temp, false);
                responces.add(bots);
            }
        }
    }

    public void addPlayerResponce(String re){
        responces.add(new Responce(re, true));
    }

    //randomize list, but keep track of what answeres were bots.  

    public String isCorrect(int guess){
        if(responces.get(guess).isHuman){
            //increment points
            return "Yes guess " + guess + " was a Human!";
        }
        else return "no guess " + guess + " was not a human";
    }

    @Override
    public void run() {
        // initial time add first player @@@@
        while (pList.size() > 1) {
            // this is where we do all the "physical" game logic stuff

            for (int i = 0; i < pList.size(); i++) {
                // turn changing of the Czars
                pList.get(i).makeCzar(true);
                if (i == 0) {
                    pList.get(pList.size()).makeCzar(false);
                } else {
                    pList.get(i - 1).makeCzar(false);
                }
                // sends out the name and prompt to all users
                for (player p : pList) {
                    p.outToClient(pList.get(i).getPlayerName() + "is the Czar");
                    p.outToClient("Prompt: " + this.generateRandomQuestion());
                }
                
                //some kind of waiting function for responces, while having the bot responces

                //jumble responces

                //show to all users the responces 
                for(player p :pList){
                    for (Responce r : responces) {
                        p.outToClient(r.Resp);
                        //this might have some kind of issue for either backing up the stream, or 
                        //overwritting the stream or something, might need to flush each time??
                    }
                }
                //get Czars guess
                
                //check if is valid

                //clear responce list
                responces.clear();
                //

            }
        }
    }


    
    
}
