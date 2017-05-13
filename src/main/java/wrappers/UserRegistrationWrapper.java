package wrappers;

import entities.users.User;

public class UserRegistrationWrapper extends UserWrapper {

    private String dni;

    private String address;

    private String email;

    private boolean active;

    public UserRegistrationWrapper() {

    }

    public UserRegistrationWrapper(long mobile, String username, String password) {
        super(mobile, username, password);

    }

    public UserRegistrationWrapper(User user) {
        super(user);
    }

    public UserRegistrationWrapper(long mobile, String username, String password, String dni, String address, String email,
            boolean active) {
        super(mobile, username, password);
        this.dni = dni;
        this.address = address;
        this.email = email;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserRegistrationWrapper [getDni()=" + getDni() + ", getAddress()=" + getAddress() + ", getEmail()=" + getEmail()
                + ", isActive()=" + isActive() + ", getMobile()=" + getMobile() + ", getUsername()=" + getUsername() + ", getPassword()="
                + getPassword() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((dni == null) ? 0 : dni.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        UserRegistrationWrapper other = (UserRegistrationWrapper) obj;
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
        return true;
    }

}
