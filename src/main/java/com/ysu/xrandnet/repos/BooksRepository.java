package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    Book findById(int id);
}
