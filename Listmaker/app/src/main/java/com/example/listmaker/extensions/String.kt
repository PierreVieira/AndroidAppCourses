package com.example.listmaker.extensions

fun String.lastIsNumeric() = split(" ").last().toIntOrNull()?.let { true } ?: false

fun String.ignoreLastWord(): String = substring(0 until lastIndexOf(" "))

fun String.uniquify(listStrings: List<String>): String {
    val amountEquals = listStrings.count {
        if (it.lastIsNumeric())
            it.ignoreLastWord() == this
        else it == this
    }
    return if (amountEquals > 0) "$this $amountEquals" else this
}