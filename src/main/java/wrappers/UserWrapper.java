package wrappers;

import entities.users.User;

public class UserWrapper {
    
    private int id;

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
        this.id=user.getId();
    }

    public UserWrapper(long mobile, String username, String password) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
    }
    
    

    public UserWrapper(int id, long mobile, String username, String password, String dni, String email, String address, boolean active) {
        super();
        this.id = id;
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.address = address;
        this.active = active;
    }

    public UserWrapper(long mobile, String username, String password, String dni, String email, String address, boolean active) {
        super();
        this.mobile = mobile;
        this.username = username;
        this.password = password;
        this.dni = dni;
        this.email = email;
        this.address = address;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserWrapper [id=" + id + ", mobile=" + mobile + ", username=" + username + ", password=" + password + ", dni=" + dni
                + ", email=" + email + ", address=" + address + ", active=" + active + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((dni == null) ? 0 : dni.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + id;
        result = prime * result + (int) (mobile ^ (mobile >>> 32));
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserWrapper other = (UserWrapper) obj;
        if (active != other.active)
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (dni == null) {
            if (other.dni != null)
                return false;
        } else if (!dni.equals(other.dni))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id != other.id)
            return false;
        if (mobile != other.mobile)
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    
}
