/**
 * Created by Snap on 16.11.2015.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class MyTable {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="last_name")
    private String last_name;

    @Column(name="first_name")
    private String first_name;

    @Column(name="gender")
    private String gender;

    @Column(name="birthDate")
    private String birthDate;

    @Column(name="phone")
    private String phone;

    public MyTable() {}

    // getters

    public int getId()
    {
        return id;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public String getGender() {return  gender;}
    public String getBirthDate() {return  birthDate;}
    public String getPhone() {return  phone;}

    // setters

    public void setId(int id)
    {
        this.id = id;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setLastName(String last_name)
    {
        this.last_name = last_name;
    }

    public void setFirstName(String first_name)
    {
        this.first_name = first_name;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setBirthDate(String date)
    {
        this.birthDate = date;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
