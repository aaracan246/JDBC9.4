package org.example.consoleIO

import org.example.interfaces.IConsole

class Console: IConsole {
    override fun reader(msg: String) {
        readln()
    }

    override fun writer(msg: String, lineBreak: Boolean) {
        if (lineBreak){ println(msg) } else { print(msg) }
    }
}