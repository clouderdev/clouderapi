package miscellaneous;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

public class Test {

    public static void main(String[] args) {
        String currentTimeEncoded = new String(
                Base64.encodeBase64(String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8)));
        System.out.println(currentTimeEncoded);

        System.out.println("http://localhost:8080/clouderapi/" + "verifyemail?username=" + "shrinivas93" + "&key="
                + currentTimeEncoded);

    }
}
