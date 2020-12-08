package server;

public class gameLogic {
    questionList list = new questionList();
    wordList wordList = new wordList();

    public String generateRandomQuestion(){
        return list.getRandomQuestion();
    }

    public String generateRandomResponse() {
        return wordList.getRandomResponse();
    }
}
