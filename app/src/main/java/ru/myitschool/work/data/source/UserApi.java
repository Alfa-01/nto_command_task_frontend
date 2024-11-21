package ru.myitschool.work.data.source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.myitschool.work.data.dto.UserDto;

public interface UserApi {

    @GET("user/{login}")
    Call<UserDto> getByLogin(@Path("login") String login);
    @GET("user/username/{username}")
    Call<Void> isExist(@Path("username") String login);
}
