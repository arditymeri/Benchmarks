package at.ymeri;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.Arrays;
import java.util.List;

public class LoremTest {

    public static void main(String []args) {
        Lorem lorem = LoremIpsum.getInstance();
        String words = lorem.getWords(5);

        List<String> list = Arrays.asList(words.split(" "));
    }
}
