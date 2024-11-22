package ru.myitschool.work.domain.entities;

import androidx.annotation.NonNull;

public class QrEntity {

    @NonNull
    private final String login;

    @NonNull
    public String getQr() {
        return qr;
    }

    @NonNull
    private final String qr;

    @NonNull
    public String getLogin() {
        return login;
    }

    public QrEntity(@NonNull String login, @NonNull String qr) {
        this.login = login;
        this.qr = qr;
    }
}
