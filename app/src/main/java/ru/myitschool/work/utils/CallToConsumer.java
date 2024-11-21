package ru.myitschool.work.utils;


import androidx.annotation.NonNull;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.myitschool.work.domain.entities.Status;

public class CallToConsumer<SOURCE, DEST> implements Callback<SOURCE> {
    @NonNull
    private final Consumer<Status<DEST>> callback;
    @NonNull
    private final Mapper<SOURCE, DEST> mapper;

    public CallToConsumer(@NonNull Consumer<Status<DEST>> callback,
                          @NonNull Mapper<SOURCE, DEST> mapper) {
        this.callback = callback;
        this.mapper = mapper;
    }

    @Override
    public void onResponse(@NonNull Call<SOURCE> call, Response<SOURCE> response) {
        callback.accept(
                new Status<>(
                        response.code(),
                        mapper.map(response.body()),
                        null
                )
        );
    }

    @Override
    public void onFailure(@NonNull Call<SOURCE> call, @NonNull Throwable t) {
        callback.accept(
                new Status<>(
                        -1,
                        null,
                        t
                )
        );
    }

    public interface Mapper<SOURCE, DEST> {
        DEST map(SOURCE source);
    }
}
