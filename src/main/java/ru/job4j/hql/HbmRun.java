package ru.job4j.hql;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var session = sf.openSession();
            session.beginTransaction();
            /**
            var candidateOne = Candidate.of("Anton Loginov", 1, 100.00);
            var candidateTwo = Candidate.of("J.T.Boe", 2, 200.00);
            var candidateThree = Candidate.of("R.Rees", 0, 50.00);

            session.persist(candidateOne);
            session.persist(candidateTwo);
            session.persist(candidateThree);
             **/

            var allCandidates = session.createQuery("from Candidate").list();
            allCandidates.forEach(System.out::println);

            var queryCandidate = session.createQuery("from Candidate c where c.id = 1");
            var candidate = (Candidate) queryCandidate.uniqueResult();
            System.out.println(candidate.toString());

            var queryCandidateName = session.createQuery("from Candidate c where c.name = :name");
            queryCandidateName.setParameter("name", "J.T.Boe");
            var candidateName = queryCandidateName.getResultList();
            candidateName.forEach(System.out::println);

            var query = session.createQuery(
                    "update Candidate c set c.experience = :newExperience, c.salary = :newSalary where c.id = :fId"
            );
            query.setParameter("newExperience", 4);
            query.setParameter("newSalary", 400.00);
            query.setParameter("fId", 1);
            query.executeUpdate();

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
