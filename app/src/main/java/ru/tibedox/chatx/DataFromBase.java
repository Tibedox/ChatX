package ru.tibedox.chatx;

import com.google.gson.annotations.SerializedName;

public class DataFromBase {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("message")
    String message;

    @SerializedName("created")
    String created;
}
