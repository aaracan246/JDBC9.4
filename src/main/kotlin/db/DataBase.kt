package org.example.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DataBase {
    const val URL = "jdbc:h2:./BooksDAO"
    const val USER = "user"
    const val PASSWORD = "user"
    const val DRIVERNAME = "org.h2.Driver"

    fun getConnection(): Connection? {
        var conn: Connection? = null
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return conn
    }

    // Función para cerrar una conexión a la base de datos
    fun closeConnection(conn: Connection?) {
        try {
            conn?.close()
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }
}