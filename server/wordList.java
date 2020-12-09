/**********************************************************************************
 * Chesten VanPelt
 * Tonae Patterson
 * Aaron HaNasi
 * Spencer Mueller
 * CIS 457 Project 3 - Final
 * Dr. El-Said
 * The wordList class is used to keep the nouns, verbs, and subjects used to create
 * the random bot response. All the server has to do is call the method to grab
 * the random phrase to use in the game.
 *********************************************************************************/
package server;
//imports
import java.util.Random;
/***************************************************************************************
 * wordList contains 3 string arrays, one for nouns, one for verbs, and one for subjects
 * and a getRandomResponse method to return the random bot phrases.
 **************************************************************************************/
public class wordList {
    /**
     * noun array containing various nouns that can be used to create phrase.
     */
    static String noun[] = new String[]{"Man","State","Country","Bed",
            "Sock","Movie","Milk","Mike","David","Fish","Dog","Cat",
            "Monkey","Ship","Babies","Book","Women","City","Rover","Maya",
            "Juice","Van","Computer","Tissue","Hand","Space","Dirt","Boy",
            "Girl","Building","Mountain","Ocean","Website","GPU","CPU","Ram",
            "Tablet","Laptop","Apple","Window","Pickle","Xbox","Playstation" };
    /**
     * verb array containing various verbs that can be used to create phrase.
     */
    static String verb[] = new String[]{"is","are","were","grow","look","smell","seem","stay",
            "become","remain","taste","turn","broke","act","answer","approve",
            "break","build","eat","edit","enter","play","shop","shout",
            "sneeze","solve","skip","replace","see","zip","doubt","feel",
            "notice","own","recognize","think","like","look","love","hate","fear",
            "can","could","may","might","must","shall","should","would"};

    /**
     * subject array containing various subjects that can be used to create phrase.
     */
    static String subject[] = new String[]{"coffee","tea","food","pizza","soup","sad",
            "happy","school","dorm","room","big","small","vast","bad",
            "good","soft","scratchy","problems","music","far","nothing","party",
            "people","puppies","zippers","pants","coats","games","things","keys",
            "earbuds","cups","still","shoes","closed","open","late","early"};
    /*********************************************************************************
     * getRandomResponse will create 3 random integers and then pick the corresponding
     * noun, verb, and subject and add them to a string response, returning it as one
     * of the bot phrases.
     * @return response string with the random phrase
     ********************************************************************************/
    public String getRandomResponse(){
        //create new response string
        String response;
        //create new noun, verb, and subject random object
        Random nRand = new Random();
        Random vRand = new Random();
        Random sRand = new Random();
        //set corresponding numbers to the random integers from the size of the arrays
        int nounRandNum = nRand.nextInt(noun.length);
        int verbRandNum = vRand.nextInt(verb.length);
        int subRandNum = sRand.nextInt(subject.length);
        //add a corresponding random noun, verb, and subject to the resonse
        response = noun[nounRandNum] + " " + verb[verbRandNum] + " " + subject[subRandNum];
        //return the response
        return response;
    }
}
