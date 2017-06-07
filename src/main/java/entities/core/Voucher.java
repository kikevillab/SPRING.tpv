package entities.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import entities.users.Encrypting;

@Entity
public class Voucher {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String reference;

    private BigDecimal value;

    private Calendar created;

    private Calendar expiration;

    private Calendar dateOfUse;

    public Voucher() {
        created = Calendar.getInstance();
        dateOfUse = null;
        updateReference();
    }

    public Voucher(BigDecimal value, Calendar expiration) {
        this();
        setValue(value);
        setExpiration(expiration);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }
    
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;    
    }

    private void updateReference() {
        reference = new Encrypting().encryptInBase64UrlSafe("" + Long.toString(new Date().getTime()) + super.hashCode());
    }

    public Calendar getCreated() {
        return created;
    }

    public Calendar getDateOfUse() {
        return dateOfUse;
    }

    public void setExpiration(Calendar expiration) {
        this.expiration = expiration;
    }

    public Calendar getExpiration() {
        return expiration;
    }

    public void setDateOfUse(Calendar dateOfUse) {
        this.dateOfUse = dateOfUse;
    }

    public boolean used() {
        return dateOfUse != null;
    }

    public void consume() {
        assert dateOfUse == null;
        dateOfUse = Calendar.getInstance();
    }

    public boolean isConsumed() {
        return dateOfUse != null;
    }
    
    public boolean isExpired() {
        return Calendar.getInstance().after(this.expiration);
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
        return id == ((Voucher) obj).id;
    }

    @Override
    public String toString() {
        String createTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(created.getTime());
        String expirationTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(expiration.getTime());
        String useTime;
        if (this.used()) {
            useTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(dateOfUse.getTime());
        } else {
            useTime = "---";
        }
        return "Voucher[" + id + ": reference=" + reference + ", value=" + value + ", created=" + createTime + ", dateOfUse=" + useTime
                + ", expiration=" + expirationTime + "]";
    }

}
