package com.premsuraj.expensemanager.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Category constructor(id: String, var name: String, var parentId: String) : RealmObject() {
    @PrimaryKey
    @Required
    var id: String = id

    constructor() : this("0", "Other", "")
}