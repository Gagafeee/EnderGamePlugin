package com.gagafeee.endergameplugin.database;

import javax.annotation.Nullable;

public class Notification {
    int id;
    String content = "";
    String date;
    boolean hasReaded = false;
    int type = 0;
    @Nullable String mcUserName;
    @Nullable boolean isLinked = false;
}
