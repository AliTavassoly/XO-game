package xo.util;

import java.beans.Encoder;
import java.security.SecureRandom;
import java.util.Base64;

public class AuthToken {
    public static String generateAuthToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        return token;
    }
}
