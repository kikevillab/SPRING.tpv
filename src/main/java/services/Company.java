package services;

public class Company {
    private String mobile;

    private String username;

    private String password;

    private String email;

    private String company;

    private String logo;

    private String nif;

    private String phone;

    private String postalAddress;

    public Company() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString() {
        return "Company [mobile=" + mobile + ", username=" + username + ", password=" + password + ", email=" + email + ", company="
                + company + ", logo=" + logo + ", nif=" + nif + ", phone=" + phone + ", postalAddress=" + postalAddress + "]";
    }

}
