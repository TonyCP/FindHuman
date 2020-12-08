package server;


import java.util.Random;

public class wordList {

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

    public static String[] getNoun(){
        return noun;
    }

    public static String[] getVerb() {
        return verb;
    }

    public static String[] getSubject() {
        return subject;
    }

    public String getRandomResponse() {
        String response;
        Random nRand = new Random(noun.length);
        Random vRand = new Random(verb.length);
        Random sRand = new Random(subject.length);
        int nounRandNum = nRand.nextInt();
        int verbRandNum = vRand.nextInt();
        int subRandNum = sRand.nextInt();

        response = noun[nounRandNum] + " " + verb[verbRandNum] + " " + subject[subRandNum];
        return response;
    }
}
