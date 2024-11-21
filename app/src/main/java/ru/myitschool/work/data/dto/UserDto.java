package ru.myitschool.work.data.dto;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class UserDto {

    @Nullable
    @SerializedName("name")
    public String name;
    @SerializedName("lastVisit")
    @Nullable
    public String lastVisit;
    @SerializedName("photoUrl")
    @Nullable
    public String photoUrl;
    @SerializedName("position")
    @Nullable
    public String position;
    @SerializedName("id")
    @Nullable
    public String id;
}
