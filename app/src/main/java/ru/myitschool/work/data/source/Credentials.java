package ru.myitschool.work.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Credentials {

    private static Credentials INSTANCE;

    private Credentials() {}

    public static synchronized Credentials getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Credentials();
        }
        return INSTANCE;
    }

    @Nullable
    private String authData = null;

    public void setAuthData(@Nullable String authData) {
        this.authData = authData;
    }

    @Nullable
    public String getAuthData() {
        return authData;
    }
}
