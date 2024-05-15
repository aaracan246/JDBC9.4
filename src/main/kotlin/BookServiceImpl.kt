package org.example
import org.example.bookDAO.BookDAO
import org.example.entity.Book
import org.example.interfaces.IBookService
import java.util.*

class BookServiceImpl(private val bookDAO: BookDAO): IBookService {
    override fun createBook(book: Book): Book? {
        return bookDAO.createBook(book)
    }

    override fun getByBookId(id: UUID): Book? {
        return bookDAO.getBookById(id)
    }

    override fun updateBook(book: Book): Book? {
        return bookDAO.updateBooks(book)
    }

    override fun deleteBook(id: UUID) {
        bookDAO.deleteBook(id)
    }

    override fun getAllBooks(): List<Book>? {
        return bookDAO.getAllBooks()
    }
}