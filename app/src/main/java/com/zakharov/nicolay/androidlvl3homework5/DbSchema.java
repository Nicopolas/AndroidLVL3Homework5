package com.zakharov.nicolay.androidlvl3homework5;

/**
 * Created by 1 on 01.07.2018.
 */

public class DbSchema {

    public static final class UserTable {
        public static final String NAME = "user";

        public static final class Cols {
            public static final String userId = "user_id";
            public static final String login = "login";
            public static final String avatarUrl = "avatarUrl";

            public static final String[] userAllColumn = {
                    userId,
                    login,
                    avatarUrl
            };
        }
    }

}