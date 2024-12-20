package ru.myitschool.work.domain.user;

import androidx.annotation.NonNull;

import java.util.function.Consumer;


import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.entities.UserEntity;

public interface UserRepository {

    void getUserByLogin(@NonNull String login, @NonNull Consumer<Status<UserEntity>> callback);
}
