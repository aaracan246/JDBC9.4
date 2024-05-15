package org.example.interfaces

import org.example.entity.Book
import java.util.*

interface IBookService {
    fun createBook(book: Book): Book?
    fun getByBookId(id: UUID): Book?
    fun updateBook(book: Book): Book?
    fun deleteBook(id: UUID)
    fun getAllBooks(): List<Book>?
}