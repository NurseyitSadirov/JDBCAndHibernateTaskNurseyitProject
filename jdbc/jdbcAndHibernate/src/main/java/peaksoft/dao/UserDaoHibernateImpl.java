package peaksoft.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.util.*;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSession().openSession();
        session.beginTransaction();
        session.persist(User.class);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSession().openSession();
        session.getTransaction().begin();
        session.createQuery("drop table users").executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully drop users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession().openSession();
        session.beginTransaction().begin();
        User user = new User(name,lastName,age);
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSession().openSession();
        session.getTransaction().begin();
        User user = (User) session.get(User.class, id);
        if (user != null){
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User с id = " + id + " удален из базы данных");
        }else {
            System.out.println("Пользователь с id = " + id + " не найден в базе данных");
        }
        session.close();
        }


    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSession().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("select u from User u");
        List<Integer> userList = Collections.singletonList(query.getFirstResult());
        session.getTransaction().commit();
        session.close();
        return Collections.singletonList((User) userList);
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession().openSession();
        session.getTransaction().begin();
        Query query = session.createQuery("delete from user");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Все пользователи удалены из базы данных");
    }
}
