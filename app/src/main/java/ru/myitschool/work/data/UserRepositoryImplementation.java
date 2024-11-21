package ru.myitschool.work.data;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.data.network.RetrofitFactory;
import ru.myitschool.work.data.source.UserApi;
import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.entities.UserEntity;
import ru.myitschool.work.domain.user.UserRepository;
import ru.myitschool.work.utils.CallToConsumer;

public class UserRepositoryImplementation implements UserRepository {

    private static UserRepositoryImplementation INSTANCE;
    private final UserApi userApi = RetrofitFactory.getInstance().getUserApi();

    private UserRepositoryImplementation() {}

    public static synchronized UserRepositoryImplementation getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImplementation();
        }
        return INSTANCE;
    }
    @Override
    public void getUserById(@NonNull String id, @NonNull Consumer<Status<UserEntity>> callback) {

        userApi.getById(id).enqueue(new CallToConsumer<>(
                callback,
                userDto -> {
                    final String resultId = userDto.id;
                    final String name = userDto.name;
                    if (resultId != null && name != null) {
                        return new UserEntity(
                                resultId,
                                name,
                                userDto.lastVisit,
                                userDto.photoUrl,
                                userDto.position
                        );
                    } else {
                        return null;
                    }
                }
        ));
    }
}
