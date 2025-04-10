package ru.didorenko.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.didorenko.bookstore.model.Book;
import ru.didorenko.bookstore.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> findBooks(String title, String brand, Integer year, Pageable pageable) {
        if (title != null && brand != null && year != null) {
            return bookRepository.findByTitleAndBrandAndYear(title, brand, year, pageable);
        } else if (title != null && brand != null) {
            return bookRepository.findByTitleAndBrand(title, brand, pageable);
        } else if (title != null && year != null) {
            return bookRepository.findByTitleAndYear(title, year, pageable);
        } else if (brand != null && year != null) {
            return bookRepository.findByBrandAndYear(brand, year, pageable);
        } else if (title != null) {
            return bookRepository.findByTitle(title, pageable);
        } else if (brand != null) {
            return bookRepository.findByBrand(brand, pageable);
        } else if (year != null) {
            return bookRepository.findByYear(year, pageable);
        }
        return bookRepository.findAll(pageable);
    }

    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

}