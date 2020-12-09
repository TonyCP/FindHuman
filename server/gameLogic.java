/**********************************************************************************
 * Chesten VanPelt
 * Tonae Patterson
 * Aaron HaNasi
 * Spencer Mueller
 * CIS 457 Project 3 - Final
 * Dr. El-Said
 * The gameLogic class will be called when the server needs a random question
 * or a random phrase to use for the game. It also checks if the answer given is
 * the actual answer to the question the human gave. It also sets player 1 and 2
 * as well as gets them if needed.
 *********************************************************************************/
package server;
/*************************************************************************
 * gameLogic class contains random question and phrases methods as well as
 * if the answer the czar give is correct and whose turn it is.
 ************************************************************************/
public class gameLogic{
    /**
     * create a new questionList object.
     */
    questionList list = new questionList();
    /**
     * create a new wordList object.
     */
    wordList wordList = new wordList();
    /**
     * player1 for the first player.
     */
    String player1;
    /**
     * player2 for the second player.
     */
    String player2;
    //randomize list, but keep track of what answers were bots.
    /******************************************************************
     * isCorrect will take the guess and the actual answer and return if
     * the answer given is the correct one.
     * @param guess the czar's guess of who the human response is
     * @param answer the human's actual answer
     * @return if the czar was correct or not
     *****************************************************************/
    public boolean isCorrect(String guess, String answer){
        //boolean for correct or not
        boolean isItCorrect = false;
        //if correct return true
        if(guess.equals(answer)){
            isItCorrect = true;
        }
        //else return false
        else{
            isItCorrect = false;
        }
        //return if true or false
        return isItCorrect;
    }
    /********************************************************************************
     * generateRandomQuestion will pull a random question from he questionList class.
     * @return random question string
     *******************************************************************************/
    public String generateRandomQuestion(){
        //return the random question
        return list.getRandomQuestion();
    }
    /******************************************************************
     * generateRandomResponse will pull a random phrase for the bots to
     * use to try and trick the czar.
     * @return random phrase string
     *****************************************************************/
    public String generateRandomResponse(){
        //return the phrase string
        return wordList.getRandomResponse();
    }
    /***********************************************************************
     * whoseTurn will take the user and set the current player to that user.
     * @param user what player the current player should be
     * @return current player as that player
     **********************************************************************/
    public String whoseTurn(int user){
        //current player string
        String currPlayer = "";
        //if player 1
        if (user == 0){
            //set to player 1
            currPlayer = getPlayer1();
        }
        //else set to player 2
        else if(user == 1){
            currPlayer = getPlayer2();
        }
        //return current player
        return currPlayer;
    }
    /**********************************************
     * setPlayer1 will set player1 to player1Input.
     * @param player1Input what to set player1 to
     *********************************************/
    public void setPlayer1(String player1Input){
        player1 = player1Input;
    }
    /**********************************************
     * setPlayer1 will set player2 to player2Input.
     * @param player2Input what to set player2 to
     *********************************************/
    public void setPlayer2(String player2Input){
        player2 = player2Input;
    }
    /*********************************
     * getPlayer1 will return player1.
     * @return player1
     ********************************/
    public String getPlayer1(){
        return player1;
    }
    /*********************************
     * getPlayer2 will return player2.
     * @return player2
     ********************************/
    public String getPlayer2(){
        return player2;
    }
}

