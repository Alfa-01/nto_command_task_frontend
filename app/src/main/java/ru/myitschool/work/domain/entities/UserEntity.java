package ru.myitschool.work.domain.entities;

import androidx.annotation.NonNull;

import javax.annotation.Nullable;

public class UserEntity {

    @NonNull
    private final String name;
    @NonNull
    private final String id;
    @Nullable
    private final String last_visit;
    @Nullable
    private final String photoUrl;
    @Nullable
    private final String position;

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getLast_visit() {
        return last_visit;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getPhoto() {
        return photoUrl;
    }

    @Nullable
    public String getPosition() {
        return position;
    }

    public UserEntity(
            @NonNull String id,
            @NonNull String name,
            @Nullable String last_visit,
            @Nullable String photoUrl,
            @Nullable String position) {
        this.id = id;
        this.name = name;
        this.last_visit = last_visit;
        this.photoUrl = photoUrl;
        this.position = position;
    }
}
