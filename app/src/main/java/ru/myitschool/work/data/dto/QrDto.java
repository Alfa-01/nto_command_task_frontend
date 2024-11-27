package ru.myitschool.work.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class QrDto {
    @Nullable
    @SerializedName("value")
    public String code;

    public QrDto(@Nullable String code) {
        this.code = code;
    }
}
