package ru.didorenko.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.didorenko.bookstore.common.model.BaseEntity;
import ru.didorenko.bookstore.common.validation.NoHtml;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity {

    @Column(name = "vendor_code", nullable = false, unique = true)
    @NotBlank(message = "Vendor code must not be blank")
    @Size(max = 50, message = "Vendor code must be at most 50 characters long")
    @NoHtml
    private String vendorCode;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title must not be blank")
    @Size(min = 2, max = 128)
    private String title;

    @Column(name = "year_book")
    private Integer year;

    @Column(name = "brand", nullable = false)
    @NotBlank(message = "Brand must not be blank")
    @Size(min = 2, max = 128)
    private String brand;

    @Column(name = "stock")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @Min(value = 0, message = "Price cannot be negative")
    @Column(name = "price")
    @NotNull
    private Integer price; // Price in cents for better precision
}
