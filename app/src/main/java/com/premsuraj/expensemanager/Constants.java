package com.premsuraj.expensemanager;

public abstract class Constants {
    public abstract class REALM {
        //Google cloud
//        public static final String AUTH_URL = "http://" + "35.186.184.200" + ":9080/auth";
//        public static final String REALM_URL = "realm://" + "35.186.184.200" + ":9080/~/expenses";

        //local machine
        public static final String AUTH_URL = "http://" + "192.168.10.81" + ":9080/auth";
        public static final String REALM_URL = "realm://" + "192.168.10.81" + ":9080/~/expenses";
    }
    public abstract class KEYS {
        public static final String TRANSACTION = "transaction_id";
        public static final String CATEGORY_ID = "category_id";
        public static final String PARENT_ID = "parent_id";
        public static final String CATEGORY_NAME = "category_name";
    }

    public abstract class DbReferences {
        public static final String TRANSACTIONS = "transactions";
        public static final String CATEGORIES = "categories";
    }
}
