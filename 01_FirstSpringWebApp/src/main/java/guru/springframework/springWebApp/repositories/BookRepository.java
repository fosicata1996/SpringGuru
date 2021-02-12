package guru.springframework.springWebApp.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.springWebApp.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>
{
}
