package com.clouder.clouderapi.pojo;

public final class Constants {

    public static final String CRYPT_ALGO = "CRYPT_ALGO";
    public static final String CRYPT_ALGO_TRANSFORMATION = "RSA/NONE/NoPadding";

    public static final long TIMEOUT_MINUTES = 60;

    public static final String NO_SUCH_CLOUD_EXIST = "No such cloud exists";
    public static final int DBX_UPLOAD_LIMIT = 150 * 1024;

    public static final String AUTH_HEADER = "auth-token";

    private Constants() {
    }

}
