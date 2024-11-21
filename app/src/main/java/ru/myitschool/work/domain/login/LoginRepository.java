package ru.myitschool.work.domain.login;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.domain.entities.Status;

public interface LoginRepository {

    void isUserExist(@NonNull String login, Consumer<Status<Void>> callback);
    void logoutUser();
}
