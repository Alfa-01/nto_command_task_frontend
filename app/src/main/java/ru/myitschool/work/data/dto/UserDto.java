package ru.myitschool.work.data.dto;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserDto {

    @Nullable
    @SerializedName("id")
    public String id;
    @Nullable
    @SerializedName("name")
    public String name;
    @Nullable
    @SerializedName("lastVisit")
    public String lastVisit;
    @Nullable
    @SerializedName("photo")
    public String photoUrl;
    @Nullable
    @SerializedName("position")
    public String position;
    @Nullable
    @SerializedName("login")
    public String login;
}
