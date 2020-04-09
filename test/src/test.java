import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String args[]){
        //match pattern for base domain and return
        //Pattern pattern = Pattern.compile("[http][s][://][www.](.*)/.*");
        Pattern pattern = Pattern.compile("(http://|https://)?(www\\.)?(.*)");
        Matcher matcher = pattern.matcher("http://www.google.com");

        if (matcher.find()){
            System.out.println(matcher.group(3));
        }


    }
}
