package findHuman.server;

import java.util.Random;

public class questionList {

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
            "How would you describe a dragon?",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    public static String getRandomQuestion(){
        Random rand = new Random(questions.length);
        int random = rand.nextInt();
        return questions[random];
    }
}
