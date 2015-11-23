/**
 * Created by Snap on 16.11.2015.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "A")
public class MyTable {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="last_name")
    private String last_name;

    @Column(name="first_name")
    private String first_name;

    public MyTable() {}

    // getters

    public String getId()
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

    // setters

    public void setId(String id)
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

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }
}
