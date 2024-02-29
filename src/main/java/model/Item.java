package model;

import jakarta.persistence.*;
import org.hibernate.boot.model.relational.Sequence;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(length = 50, nullable = false)
    String name;
    String description;
    @ManyToOne
    @JoinColumn(name = "box_id")
    List<Box> box = new ArrayList<>();
    @ManyToMany(mappedBy = "item")
    @JoinTable(name = "item_category", joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))

    List<Category> categories = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "loan_id", unique = true)
    Loan loan;


    public Item() {

    }

    public Item(String name, String description) {

        this.name = name;
        this.description = description;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Box> getBox() {
        return box;
    }

    public void setBox(List<Box> box) {
        this.box = box;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", box=" + box +
                ", categories=" + categories +
                ", loan=" + loan +
                '}';
    }
}

