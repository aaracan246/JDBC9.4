package org.example.bookDAO

import org.example.db.DataBase
import org.example.consoleIO.Console
import org.example.entity.Book
import org.example.interfaces.IBookDAO
import java.sql.SQLException
import java.util.*

class BookDAO(private val console: Console): IBookDAO {
    private val connection = DataBase.getConnection()

    override fun createBook(book: Book): Book? {
        val sql = "INSERT INTO PRODUCTS (ID, TITLE, AUTHOR, THEME, PUBLISHED) VALUES (?, ?, ?, ?, ?)"

        return try {
            connection.use { connection ->
                connection?.prepareStatement(sql).use { statement ->
                    statement?.setString(1, book.id.toString())
                    statement?.setString(2, book.title)
                    statement?.setString(3, book.author)
                    statement?.setString(4, book.theme)
                    statement?.setInt(5, book.published)
                    val rs = statement?.executeUpdate()
                    if (rs == 1) {
                        connection?.close()
                        book
                    } else {
                        connection?.close()
                        null
                    }
                }
            }
        } catch (e: SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            return null
        }
    }

    override fun getAllBooks(): List<Book>? {
        val sql = "SELECT * FROM BOOKS"
        return try {
            connection.use { connection ->
                connection?.prepareStatement(sql).use { stmt ->
                    val rs = stmt?.executeQuery()
                    val products = mutableListOf<Book>()
                    while (rs!!.next()) {
                        products.add(
                            Book(
                                id = UUID.fromString(rs.getString("id")),
                                title = rs.getString("title"),
                                author = rs.getString("author"),
                                theme = rs.getString("theme"),
                                published = rs.getInt("published")
                            )
                        )
                    }
                    connection?.close()
                    products
                }
            }
        }catch (e:SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            connection?.close()
            null

        }catch (e:Exception){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            connection?.close()
            null
        }
    }


    override fun getBookById(id: Int): Book? {
        val sql = "SELECT * FROM BOOKS WHERE id = (?)"
        return try {
            connection.use { connection ->
                connection?.prepareStatement(sql).use { statement ->
                    statement?.setString(1, id.toString())
                    val rs = statement?.executeQuery()
                    if (rs!!.next()) {
                        Book(
                            id = UUID.fromString(rs.getString("id")),
                            title = rs.getString("title"),
                            author = rs.getString("author"),
                            theme = rs.getString("theme"),
                            published = rs.getInt("published")
                        )
                    } else {
                        console.writer("** ERROR AL OBTENER EL LIBRO **", true)
                        null
                    }
                }
            }
        }catch (e:SQLException){
            console.writer(e.message!!, true)
            connection?.close()
            null

        } catch (e:Exception){
            console.writer(e.message!!, true)
            connection?.close()
            null
        }
    }


    override fun updateBooks(book: Book): Book? {
        val sql = "UPDATE BOOKS SET id = ?, title = ?, author = ?, theme = ?, published = ?"
        return try {
            connection.use { connection ->
                connection?.prepareStatement(sql).use { statement ->
                    statement?.setString(1, book.id.toString())
                    statement?.setString(2, book.title)
                    statement?.setString(3, book.author)
                    statement?.setString(4, book.theme)
                    statement?.setInt(5, book.published)
                    statement?.executeUpdate()
                    connection?.close()
                    book
                }
            }
        } catch (e:SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            connection?.close()
            null

        } catch (e:Exception){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            connection?.close()
            null
        }
    }

    override fun deleteBook(id: Int): Boolean {
        val sql = "DELETE FROM BOOKS WHERE id = (?)"
        return try {
            connection.use {connection ->
                connection?.prepareStatement(sql).use { statement ->
                    statement?.setString(1, id.toString())
                    statement?.executeUpdate()
                    connection?.close()
                    true
                }
            }
        } catch (e:SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            connection?.close()
            false

        }catch (e:Exception){
            console.writer(msg = "Something unexpected happened while trying to connect to the database.", true)
            connection?.close()
            false
        }
    }
}