import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String a = "";
        System.out.println(a.toString());

        Map<String, Object> temp = new HashMap();
        temp.put("a", "");
        temp.put("b", null);
        System.out.println(temp.get("a").toString());
        System.out.println((temp.get("b")));
        System.out.println(String.valueOf(temp.get("c")) == "null");
    }
}
