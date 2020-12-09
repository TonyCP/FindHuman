/***********************************************************************************
 * Chesten VanPelt
 * Tonae Patterson
 * Aaron HaNasi
 * Spencer Mueller
 * CIS 457 Project 3 - Final
 * Dr. El-Said
 * The questionList class contains the list of possible questions the server will
 * ask and then has a getRandomQuestion method that will return one of the questions
 * to the server and present to the clients at random.
 **********************************************************************************/
package server;
//imports
import java.util.Random;
/********************************************************************************************
 * questionList class contains a string array of questions for the server to ask the clients.
 *******************************************************************************************/
public class questionList {
    /**
     * questions is an array of questions used as the prompt for the clients to makeup and guess phrase.
     */
    static String questions[] = new String[]{
            "What does the fox say?",
            "If you were an animal, what would you be?",
            "If stuffed animals could talk, what would they say?",
            "Are you a cat-person or a dog-person?",
            "If your pet could talk, what would it say?",
            "What would you do if I stole your candy?",
            "Would you rather eat cake or ice cream?",
            "What makes you sing?",
            "What is the meaning of life?",
            "What do you want to invent?",
            "If someone gave you a lot of money, how would you spend it?",
            "Who's the boss of you?",
            "Why do you wake up in the morning?",
            "What makes you dance?",
            "Why do you wake up in the morning?",
            "What do you want to be when you grow up?",
            "If you wrote a book, what would its title be?",
            "What color is the sky?",
            "How would you describe a dragon?"
    };

    /**************************************************************************************************
     * getRandomQuestion will create a random number for the number of questions and randomly pick one.
     * @return a random question string containing the question
     *************************************************************************************************/
    public static String getRandomQuestion(){
        //create a new random object
        Random rand = new Random();
        //set int random to a random int from 0 to the number of questions
        int random = rand.nextInt(questions.length);
        //return that random question
        return questions[random];
    }
}
