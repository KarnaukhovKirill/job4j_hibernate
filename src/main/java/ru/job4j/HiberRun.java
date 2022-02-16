package ru.job4j;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.manytomany.Author;
import ru.job4j.manytomany.Book;

public class HiberRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var session = sf.openSession();
            session.beginTransaction();

            Author author1 = Author.of("Author1");
            Author author2 = Author.of("Author2");
            Author author3 = Author.of("Author3");

            Book book1 = Book.of("Book1");
            Book book2 = Book.of("Book2");
            Book book3 = Book.of("Book3");

            author1.addBooks(book1);
            author1.addBooks(book2);
            author1.addBooks(book3);

            author2.addBooks(book1);
            author2.addBooks(book2);

            author3.addBooks(book1);

            session.persist(author1);
            session.persist(author2);
            session.persist(author3);

            Author authorOne = session.get(Author.class, author1.getId());
            Author authorThree = session.get(Author.class, author3.getId());
            session.remove(authorOne);
            session.remove(authorThree);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
