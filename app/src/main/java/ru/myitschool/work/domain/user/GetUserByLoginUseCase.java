package ru.myitschool.work.domain.user;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.entities.UserEntity;

public class GetUserByLoginUseCase {
    private final UserRepository repository;

    public GetUserByLoginUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@NonNull String login, @NonNull Consumer<Status<UserEntity>> callback) {
        repository.getUserByLogin(login, callback);
    }
}
