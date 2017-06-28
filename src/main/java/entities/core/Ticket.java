package entities.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entities.users.Encrypting;
import entities.users.User;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@Entity
@IdClass(TicketPK.class)
public class Ticket {

    @Id
    private int date;
    
    @Id
    private int id;

    @Temporal(TemporalType.DATE)
    private Calendar created;

    @Column(unique = true, nullable = false)
    private String reference;

    @Lob
    @Column
    private byte[] qrReference;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Shopping> shoppingList;

    @ManyToOne
    @JoinColumn
    private User user;

    public Ticket() {
        created = Calendar.getInstance();
        created.set(Calendar.MILLISECOND, 0);
        date = Integer.parseInt((new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime())));
        reference = new Encrypting().encryptInBase64UrlSafe("" + this.getId() + Long.toString(new Date().getTime()));
        qrReference = QRCode.from(reference).to(ImageType.JPG).stream().toByteArray();
        shoppingList = new ArrayList<>();
    }

    public Ticket(int id) {
        this();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public void addShopping(Shopping shopping) {
        shoppingList.add(shopping);
    }

    public List<Shopping> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Shopping> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getCreated() {
        return created;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public byte[] getQrReference() {
        return qrReference;
    }

    public BigDecimal getTicketTotal() {
        double total = 0.0;
        for (Shopping shopping : shoppingList) {
            total += shopping.getShoppingTotal();
        }
        return new BigDecimal(total);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + date;
        return result;
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
        return (id == ((Ticket) obj).id) && (date == ((Ticket) obj).date);
    }

    @Override
    public String toString() {
        String createTime = new SimpleDateFormat("HH:mm dd-MMM-yyyy ").format(created.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ticket[" + id + ": created=" + createTime + ", shoppingList=" + shoppingList);
        if (user != null) {
            stringBuilder.append(", userId=" + user.getId());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
