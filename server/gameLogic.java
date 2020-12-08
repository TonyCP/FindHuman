package server;

public class gameLogic {
    questionList list = new questionList();

    public String generateRandomQuestion(){
        return list.getRandomQuestion();
    }

}
