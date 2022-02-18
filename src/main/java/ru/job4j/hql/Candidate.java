package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int experience;
    private double salary;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private VacancyDb vacancyDb;

    public Candidate() {
    }

    public static Candidate of(String name, int experience, double salary, VacancyDb vacancyDb) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        candidate.vacancyDb = vacancyDb;
        return candidate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public VacancyDb getVacancyDb() {
        return vacancyDb;
    }

    public void setVacancyDb(VacancyDb vacancyDb) {
        this.vacancyDb = vacancyDb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", experience=" + experience
                + ", salary=" + salary
                + ", vacancyDb=" + vacancyDb
                + '}';
    }
}
