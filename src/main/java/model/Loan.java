package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String User;
    @OneToOne
    @JoinColumn(name="item_id", unique = true)
    Item item;
    Long checkout_date;
    Long due_date;
    Long returned_date;

    public Loan() {
    }

    public Loan(String user, Long checkout_date, Long due_date, Long returned_date) {
        User = user;
        this.checkout_date = checkout_date;
        this.due_date = due_date;
        this.returned_date = returned_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Long checkout_date) {
        this.checkout_date = checkout_date;
    }

    public Long getDue_date() {
        return due_date;
    }

    public void setDue_date(Long due_date) {
        this.due_date = due_date;
    }

    public Long getReturned_date() {
        return returned_date;
    }

    public void setReturned_date(Long returned_date) {
        this.returned_date = returned_date;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", User='" + User + '\'' +
                ", item=" + item +
                ", checkout_date=" + checkout_date +
                ", due_date=" + due_date +
                ", returned_date=" + returned_date +
                '}';
    }
}
