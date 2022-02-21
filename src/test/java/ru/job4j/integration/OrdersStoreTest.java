package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        String query = getQuery("./db/update_001.sql");
        pool.getConnection().prepareStatement(query).executeUpdate();
    }

    @After
    public void close() throws SQLException {
        String query = getQuery("./db/update_002.sql");
        pool.getConnection().prepareStatement(query).execute();
    }

    private String getQuery(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path))
        )) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("Order", "description");
        store.save(order);
        Order rsl = store.findById(order.getId());
        assertThat(rsl.getId(), is(order.getId()));
        assertThat(rsl.getDescription(), is(order.getDescription()));
    }

    @Test
    public void whenUpdate() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("Order", "description");
        store.save(order);
        store.update(order.getId(), Order.of("new Order", "new Description"));
        Order rsl = store.findById(order.getId());
        assertThat(rsl.getDescription(), is("new Description"));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order order = Order.of("Order", "description");
        Order sameOrder = Order.of("Order", "another Desc");
        store.save(order);
        store.save(sameOrder);
        List<Order> rsl = store.findByName("Order");
        assertThat(rsl.size(), is(2));
    }
}