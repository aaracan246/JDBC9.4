package org.example

import org.example.bookDAO.BookDAO
import org.example.consoleIO.Console
import org.example.db.DataSourceFactory
import org.example.entity.Book
import java.util.*

fun main() {
    val console = Console()

    // Creamos la instancia de la base de datos
    val datasource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    // Creamos la instancia de BookDAO
    val bookDAO = BookDAO(datasource, console)

    // Creamos la instancia de bookService
    val bookService = BookServiceImpl(bookDAO)

    // Creamos un nuevo libro
    val newBook = Book(title = "City of Glass", author ="Paul Auster", theme = "Detectivesca", published = 1985)

    var createdBook = newBook.let { bookService.createBook(it) }
    console.writer("Created user: $createdBook")

    // Obtenemos un libro por su ID
    val foundBook = createdBook?.let { bookService.getByBookId(it.id) }
    console.writer("Found user: $foundBook")

    // Actualizamos el libro
    val updatedBook = foundBook?.copy(title = "City of Glass")
    val savedBook = updatedBook?.let { bookService.updateBook(it) }
    console.writer("Updated user: $savedBook")

    val otherBook = Book(UUID.randomUUID(), "The Yellow Wallpaper", "Charlotte Perkins Gilman", "Ficción", 1892)

    createdBook = bookService.createBook( otherBook)
    console.writer("Created user: $createdBook")


    // Obtenemos todos los libros
    var allBooks = bookService.getAllBooks()
    console.writer(allBooks.toString())

    // Eliminamos el libro
    if (savedBook != null) {
        bookService.deleteBook(savedBook.id)
    }
    console.writer("Book deleted")

    // Obtenemos todos los libros
    allBooks = bookService.getAllBooks()
    console.writer("All books: $allBooks")

    // Eliminamos el libro
    bookService.deleteBook(otherBook.id)
    console.writer("Book deleted")
}

