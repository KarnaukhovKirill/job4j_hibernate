package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Brand;
import ru.job4j.model.Model;

public class HiberRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model outlander = Model.of("outlander");
            Model lancer = Model.of("lancer");
            Model pajero = Model.of("pajero");
            Model asx = Model.of("asx");
            Model galant = Model.of("galant");
            session.save(outlander);
            session.save(lancer);
            session.save(pajero);
            session.save(asx);
            session.save(galant);
            Brand mitsubishi = Brand.of("Mitsubishi");
            mitsubishi.addModel(session.load(Model.class, outlander.getId()));
            mitsubishi.addModel(session.load(Model.class, lancer.getId()));
            mitsubishi.addModel(session.load(Model.class, pajero.getId()));
            mitsubishi.addModel(session.load(Model.class, asx.getId()));
            mitsubishi.addModel(session.load(Model.class, galant.getId()));
            session.save(mitsubishi);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
