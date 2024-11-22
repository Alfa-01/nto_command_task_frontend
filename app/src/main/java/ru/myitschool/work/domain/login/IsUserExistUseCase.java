package ru.myitschool.work.domain.login;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.domain.entities.Status;

public class IsUserExistUseCase {

    private final LoginRepository repository;

    public IsUserExistUseCase(LoginRepository repository) {
        this.repository = repository;
    }

    public void execute(@NonNull String login, Consumer<Status<Boolean>> callback) {
        repository.isUserExist(login, status -> {
            boolean isAvailable = status.getStatusCode() == 200 || status.getStatusCode() == 401;
            callback.accept(
                    new Status<>(
                            status.getStatusCode(),
                            isAvailable ? status.getStatusCode() == 200 : null,
                            status.getErrors()
                    )
            );
        });
    }
}
