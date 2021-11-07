package main

operator fun StringBuilder.plusAssign(string: String) {
    appendLine(string)
}

