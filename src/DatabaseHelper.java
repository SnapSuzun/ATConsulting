/**
 * Created by Snap on 16.11.2015.
 */

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.Iterator;
import java.util.List;

public class DatabaseHelper {
    private static SessionFactory dbSessions;
    private void startWorkWithDB(){
        dbSessions = new AnnotationConfiguration()
                .configure("/resource/hibernate.cfg.xml")
                .addAnnotatedClass(MyTable.class)
                .buildSessionFactory();
    }
    public String checkUser(String login, String password){
        startWorkWithDB();
        Session session = dbSessions.openSession();
        try {
            List usersList = session.createQuery("FROM MyTable").list();
            for (Iterator iterator = usersList.iterator(); iterator.hasNext(); ) {
                MyTable counter = (MyTable) iterator.next();

                if (counter.getLogin().equals(login) && counter.getPassword().equals(password)){
                    return "Welcome " + counter.getFirst_name() + " " + counter.getLast_name();
                }

            }
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return "Sorry, your login or password is uncorrect. Please, try again!";
    }

    public void insertUser(int id, String login, String password, String first_name, String last_name, String gender, String birthDate, String phone){
        startWorkWithDB();
        Session session = dbSessions.openSession();
        Transaction tx = null;

        try
        {
            tx = session.beginTransaction();
            MyTable newRecord = new MyTable();
            newRecord.setId(id);
            newRecord.setLogin(login);
            newRecord.setPassword(password);
            newRecord.setLastName(last_name);
            newRecord.setFirstName(first_name);
            newRecord.setGender(gender);
            newRecord.setBirthDate(birthDate);
            newRecord.setPhone(phone);
            session.save(newRecord);
            tx.commit();
        }
        catch (HibernateException e) {if (tx!=null) tx.rollback(); e.printStackTrace();} finally {session.close();}

    }

    public List getAllRecords()
    {
        startWorkWithDB();
        Session session = dbSessions.openSession();
        try {
            List usersList = session.createQuery("FROM MyTable").list();
            return  usersList;
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

}
