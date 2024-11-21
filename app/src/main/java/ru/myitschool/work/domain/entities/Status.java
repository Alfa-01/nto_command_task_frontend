package ru.myitschool.work.domain.entities;

import javax.annotation.Nullable;

public class Status<T> {

    private final int statusCode;

    @Nullable
    private final T value;

    @Nullable
    private final Throwable errors;

    public Status(int statusCode, @Nullable T value, @Nullable Throwable errors) {
        this.errors = errors;
        this.statusCode = statusCode;
        this.value = value;
    }

    @Nullable
    public Throwable getErrors() {
        return errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Nullable
    public T getValue() {
        return value;
    }
}
