package org.example.interfaces

import org.example.entity.Book

interface IBookDAO {
    fun createBook(book: Book): Book?
    fun getAllBooks(): List<Book>?
    fun getBookById(id: Int): Book?
    fun updateBooks(book: Book): Book?
    fun deleteBook(id: Int): Boolean
}