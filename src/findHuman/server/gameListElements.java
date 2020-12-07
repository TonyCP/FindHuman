package findHuman.server;

import java.util.Random;

public class gameListElements {
    static String noun[] = new String[]{"Man","State","Country","Bed",
            "Sock","Movie","Milk","Mike","David","Fish","Dog","Cat",
            "Monkey","Ship","Babies","Book","Women","City","Rover","Maya",
            "Juice","Van","Computer","Tissue","Hand","Space","Dirt","Boy",
            "Girl","Building","Mountain","Ocean","Website","GPU","CPU","Ram",
            "Tablet","Laptop","Apple","Window","Pickle","Xbox","Playstation" };
    static String verb[] = new String[]{"is","are","were","grow","look","smell","seem","stay",
            "become","remain","taste","turn","broke","act","answer","approve",
            "break","build","eat","edit","enter","play","shop","shout",
            "sneeze","solve","skip","replace","see","zip","doubt","feel",
            "notice","own","recognize","think","like","look","love","hate","fear",
            "can","could","may","might","must","shall","should","would"};
    static String subject[] = new String[]{"coffee","tea","food","pizza","soup","sad",
            "happy","school","dorm","room","big","small","vast","bad",
            "good","soft","scratchy","problems","music","far","nothing","party",
            "people","puppies","zippers","pants","coats","games","things","keys",
            "earbuds","cups","still","shoes","closed","open","late","early"};

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
            "Who’s the boss of you?",
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


    public static String[] getNouns(){
        return noun;
    }

    public static String[] getVerb() {
        return verb;
    }

    public static String[] getSubject() {
        return subject;
    }

    public static String getRandomQuestion(){
        Random rand = new Random(questions.length);
        int random = rand.nextInt();
        return questions[random];
    }
}
