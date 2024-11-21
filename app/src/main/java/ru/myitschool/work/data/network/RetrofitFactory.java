package ru.myitschool.work.data.network;

import static ru.myitschool.work.core.Constants.SERVER_ADDRESS;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.myitschool.work.data.UserRepositoryImplementation;
import ru.myitschool.work.data.source.UserApi;

public class RetrofitFactory {

    private static RetrofitFactory INSTANCE;

    public static synchronized RetrofitFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RetrofitFactory();
        }
        return INSTANCE;
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public UserApi getUserApi() {
        return retrofit.create(UserApi.class);
    }
}
