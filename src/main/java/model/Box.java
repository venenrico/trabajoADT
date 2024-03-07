package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String label;
    String location;
    @OneToMany(mappedBy = "items")
    List<Item> item = new ArrayList<>();

    public Box() {
    }

    public Box(String label, String location) {
        this.label = label;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", location='" + location + '\'' +
                ", item=" + item +
                '}';
    }
}
