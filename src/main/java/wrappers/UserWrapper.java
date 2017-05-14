package wrappers;

import entities.users.User;

public class UserWrapper {

    private long mobile;

    private String username;

    private String password;
    
    private String dni;
    
    private String email;
    
    private String address;
    
    private boolean active;

    public UserWrapper() {
    }

    public UserWrapper(User user) {
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.dni = user.getDni();
        this.email = user.getEmail();
        this.address=user.getAddress();
        this.active=user.isActive();
    }

    public UserWrapper(long mobile, String username, String password) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
    }
    
    public UserWrapper(long mobile, String username, String password, String dni, String address, String email,
            boolean active) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.dni = dni;
        this.address = address;
        this.email = email;
        this.active = active;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserWrapper [mobile=" + mobile + ", username=" + username + ", password=" + password + ", dni=" + dni + ", email=" + email
                + ", address=" + address + ", active=" + active + "]";
    }

    

    

}
