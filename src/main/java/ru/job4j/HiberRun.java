package ru.job4j;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.lazy.Brand;
import ru.job4j.lazy.Model;

public class HiberRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var session = sf.openSession();
            session.beginTransaction();

            Brand toyota = Brand.of("toyota");

            Model avensis = Model.of("avensis", toyota);
            Model auris = Model.of("auris", toyota);
            Model chaser = Model.of("chaser", toyota);

            session.save(avensis);
            session.save(auris);
            session.save(chaser);

            toyota.getModels().add(avensis);
            toyota.getModels().add(auris);
            toyota.getModels().add(chaser);

            session.save(toyota);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
