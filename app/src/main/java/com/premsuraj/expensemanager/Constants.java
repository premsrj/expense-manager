package com.premsuraj.expensemanager;

public abstract class Constants {
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
