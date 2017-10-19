package com.premsuraj.expensemanager.data

class Category constructor(val id: String, val name: String, val parentId: String) {
    constructor() : this("0", "Other", "")
}