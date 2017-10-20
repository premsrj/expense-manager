package com.premsuraj.expensemanager.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Category constructor(id: String, var name: String, var parentId: String) : RealmObject() {
    @PrimaryKey
    @Required
    var id: String = id

    constructor() : this("0", "Other", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        if (name != other.name) return false
        if (parentId != other.parentId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + parentId.hashCode()
        return result
    }


}