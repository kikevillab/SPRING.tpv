package entities.users;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private long mobile;

    private String username;

    private String dni;

    private String address;

    @Column(unique = true)
    private String email;

    private String password;

    private Calendar registrationDate;

    // @Type(type="yes_no")
    private boolean active;

    public User() {
        this.registrationDate = Calendar.getInstance();
    }

    public User(long mobile, String username, String password) {
        this();
        this.mobile = mobile;
        this.username = username;
        setPassword(password);
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public long getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Calendar getRegistrationDate() {
        return registrationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((User) obj).id;
    }

    @Override
    public String toString() {
        String date = new SimpleDateFormat("dd-MMM-yyyy ").format(registrationDate.getTime());
        return "User [id=" + id + ", mobile=" + mobile + ", username=" + username + ", dni=" + dni + ", address=" + address + ", email="
                + email + ", password=" + password + ", registrationDate=" + date + ", active=" + active + "]";
    }

}
