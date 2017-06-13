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
    private long id;

    @Id
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
        created.set(Calendar.HOUR_OF_DAY, 0);
        created.set(Calendar.MINUTE, 0);
        created.set(Calendar.SECOND, 0);
        created.set(Calendar.MILLISECOND, 0);
        shoppingList = new ArrayList<>();
    }

    public Ticket(long id) {
        this();
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        updateReference();
    }

    private void updateReference() {
        reference = new Encrypting().encryptInBase64UrlSafe("" + this.getId() + Long.toString(new Date().getTime()));
        qrReference = QRCode.from(reference).to(ImageType.JPG).stream().toByteArray();
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

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
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
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Ticket other = (Ticket) obj;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (id != other.id)
            return false;
        return true;
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
