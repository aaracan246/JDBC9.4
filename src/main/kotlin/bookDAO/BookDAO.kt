package org.example.bookDAO
import org.example.consoleIO.Console
import org.example.entity.Book
import org.example.interfaces.IBookDAO
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

class BookDAO(private val database: DataSource, private val console: Console): IBookDAO {

    override fun createBook(book: Book): Book? {
        val sql = "INSERT INTO BOOKS (ID, TITLE, AUTHOR, THEME, PUBLISHED) VALUES (?, ?, ?, ?, ?)"

        return try {
            database.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setString(1, book.id.toString())
                    statement.setString(2, book.title)
                    statement.setString(3, book.author)
                    statement.setString(4, book.theme)
                    statement.setInt(5, book.published)
                    val rs = statement.executeUpdate()
                    if (rs == 1) {
                        book
                    } else {
                        null
                    }
                }
            }
        } catch (e: SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            return null
        }
    }

    override fun getAllBooks(): List<Book>? {
        val sql = "SELECT * FROM BOOKS"
        return try {
            database.connection.use { connection ->
                connection.prepareStatement(sql).use { stmt ->
                    val rs = stmt.executeQuery()
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
                    products
                }
            }
        }catch (e:SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            null

        }catch (e:Exception){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            null
        }
    }


    override fun getBookById(id: UUID): Book? {
        val sql = "SELECT * FROM BOOKS WHERE id = (?)"
        return try {
            database.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setString(1, id.toString())
                    val rs = statement.executeQuery()
                    if (rs.next()) {
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
            null

        } catch (e:Exception){
            console.writer(e.message!!, true)
            null
        }
    }


    override fun updateBooks(book: Book): Book? {
        val sql = "UPDATE BOOKS SET id = ?, title = ?, author = ?, theme = ?, published = ?"
        return try {
            database.connection.use { connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setString(1, book.id.toString())
                    statement.setString(2, book.title)
                    statement.setString(3, book.author)
                    statement.setString(4, book.theme)
                    statement.setInt(5, book.published)
                    statement.executeUpdate()
                    book
                }
            }
        } catch (e:SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            null

        } catch (e:Exception){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            null
        }
    }

    override fun deleteBook(id: UUID): Boolean {
        val sql = "DELETE FROM BOOKS WHERE id = (?)"
        return try {
            database.connection.use {connection ->
                connection.prepareStatement(sql).use { statement ->
                    statement.setString(1, id.toString())
                    statement.executeUpdate()
                    true
                }
            }
        } catch (e:SQLException){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            database.connection?.close()
            false

        }catch (e:Exception){
            console.writer(msg = "Something unexpected happened while trying to connect to the database (${e.message}).", true)
            database.connection?.close()
            false
        }
    }
}