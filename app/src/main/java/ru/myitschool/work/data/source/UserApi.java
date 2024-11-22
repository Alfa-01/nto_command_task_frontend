package ru.myitschool.work.data.source;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import ru.myitschool.work.data.dto.QrDto;
import ru.myitschool.work.data.dto.UserDto;

public interface UserApi {

    @GET("api/{login}/info")
    Call<UserDto> getByLogin(@Path("login") String login);
    @GET("api/{login}/auth")
    Call<Void> isExist(@Path("username") String login);
    @PATCH("api/{login}/open/")
    Call<Void> openDoor(@Path("login") String login, @Body QrDto qrDto);
}
