package ru.myitschool.work.data.source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.myitschool.work.data.dto.UserDto;

public interface UserApi {

    @GET("user/{id}")
    Call<UserDto> getById(@Path("id") String id);
}
