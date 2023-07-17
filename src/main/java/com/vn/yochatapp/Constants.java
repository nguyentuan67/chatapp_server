package com.vn.yochatapp;

public interface Constants {
    public interface RestApiReturnCode {
        public int SUCCESS = 200;
        public int BAD_REQUEST = 400;
        public int SYS_ERROR = 500;
        public int UNAUTHORIZED = 401;
        public int NOT_FOUND = 404;
        public String SUCCESS_TXT = "SUCCESS";
        public String BAD_REQUEST_TXT = "BAD REQUEST";
        public String SYS_ERROR_TXT = "SYSTEM ERROR";
        public String UNAUTHORIZED_TXT = "UNAUTHORIZED";

        public String NOT_FOUND_TXT = "NOT FOUND";
        public String ACCESS_TOKEN_TXT = "INVALID ACCESS TOKEN";
        public String NOT_LOGIN_USER = "Username hoặc Password không đúng";

    }

    public interface AuthRoles {
        public long ROLE_ADMIN_ID = 1L;
        public long ROLE_USER_ID = 2L;
        public String ROLE_ADMIN = "ADMIN";
        public String ROLE_USER = "USER";
    }

    public interface Gender {
        public int MALE = 1;
        public int FEMALE = 0;
    }

    public interface TypeConversation {
        public int PRIVATE = 1;
        public int GROUP = 2;
    }
}
