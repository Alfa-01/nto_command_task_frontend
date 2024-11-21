package ru.myitschool.work.domain.user;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.entities.UserEntity;

public class GetUserByIdUseCase {
    private final UserRepository repository;

    public GetUserByIdUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@NonNull String id, @NonNull Consumer<Status<UserEntity>> callback) {
        repository.getUserById(id, callback);
    }
}
