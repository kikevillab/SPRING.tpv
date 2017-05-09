package wrappers;

public class UserWrapper {

    private long mobile;

    private String username;

    private String password;

    public UserWrapper() {
    }

    public UserWrapper(long mobile, String username, String password) {
        this.mobile = mobile;
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "UserWrapper [mobile=" + mobile + ", username=" + username + ", password=" + password + "]";
    }

}
