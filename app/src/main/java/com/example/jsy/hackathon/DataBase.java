package com.example.jsy.hackathon;

import android.provider.BaseColumns;

/**
 * Created by JSY on 2017-06-23.
 */

public class DataBase {
    public static final class CreateDB implements BaseColumns {
        public static final String NAME = "name";
        public static final String COUNTRY = "country";
        public static final int LIKE = 0;
        public static final int DISLIKE = 0;
        public static final String _TABLENAME = "alcohol";
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +NAME+" text not null , "
                        +COUNTRY+" text not null , "
                        +DISLIKE+" int not null , "
                        +LIKE+" int not null );";
    }
}
