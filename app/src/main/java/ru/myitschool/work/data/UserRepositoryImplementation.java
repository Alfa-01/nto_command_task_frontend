package ru.myitschool.work.data;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import ru.myitschool.work.data.dto.QrDto;
import ru.myitschool.work.data.network.RetrofitFactory;
import ru.myitschool.work.data.source.Credentials;
import ru.myitschool.work.data.source.UserApi;
import ru.myitschool.work.domain.entities.QrEntity;
import ru.myitschool.work.domain.entities.Status;
import ru.myitschool.work.domain.entities.UserEntity;
import ru.myitschool.work.domain.login.LoginRepository;
import ru.myitschool.work.domain.qr.QrRepository;
import ru.myitschool.work.domain.user.UserRepository;
import ru.myitschool.work.utils.CallToConsumer;

public class UserRepositoryImplementation implements UserRepository, LoginRepository, QrRepository {

    private static UserRepositoryImplementation INSTANCE;
    private final UserApi userApi = RetrofitFactory.getInstance().getUserApi();
    private final Credentials credentials = Credentials.getInstance();

    private UserRepositoryImplementation() {}

    public static synchronized UserRepositoryImplementation getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImplementation();
        }
        return INSTANCE;
    }
    @Override
    public void getUserByLogin(@NonNull String login, @NonNull Consumer<Status<UserEntity>> callback) {

        userApi.getByLogin(login).enqueue(new CallToConsumer<>(
                callback,
                userDto -> {
                    final String resultLogin = userDto.login;
                    final String id = userDto.id;
                    final String name = userDto.name;
                    if (resultLogin != null && id != null && name != null) {
                        return new UserEntity(
                                id,
                                resultLogin,
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

    @Override
    public void isUserExist(@NonNull String login, Consumer<Status<Void>> callback) {
        userApi.isExist(login).enqueue(new CallToConsumer<>(
                callback,
                dto -> null
        ));
    }

    @Override
    public void logoutUser() {
        credentials.setAuthData(null);
    }

    @Override
    public void pushQr(@NonNull QrEntity qrEntity, @NonNull Consumer<Status<Void>> callback) {
        userApi.openDoor(qrEntity.getLogin(),
                new QrDto(qrEntity.getQr())).enqueue(new CallToConsumer<>(
                        callback,
                        dto -> null
        ));
    }
}
