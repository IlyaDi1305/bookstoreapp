package ru.didorenko.bookstore.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.didorenko.bookstore.common.BaseRepository;
import ru.didorenko.bookstore.model.Book;

@Repository
public interface BookRepository extends BaseRepository<Book>, JpaSpecificationExecutor<Book> {

    @Transactional
    @CacheEvict(value = "books", key = "#book.id")
    default Book prepareAndSave(Book book) {
        book.setVendorCode(book.getVendorCode());
        book.setTitle(book.getTitle());
        book.setYear(book.getYear());
        book.setBrand(book.getBrand());
        book.setStock(book.getStock());
        book.setPrice(book.getPrice());
        return save(book);
    }

    Page<Book> findByTitleAndBrandAndYear(String title, String brand, Integer year, Pageable pageable);

    Page<Book> findByTitleAndBrand(String title, String brand, Pageable pageable);

    Page<Book> findByTitleAndYear(String title, Integer year, Pageable pageable);

    Page<Book> findByBrandAndYear(String brand, Integer year, Pageable pageable);

    Page<Book> findByTitle(String title, Pageable pageable);

    Page<Book> findByBrand(String brand, Pageable pageable);

    Page<Book> findByYear(Integer year, Pageable pageable);


}
