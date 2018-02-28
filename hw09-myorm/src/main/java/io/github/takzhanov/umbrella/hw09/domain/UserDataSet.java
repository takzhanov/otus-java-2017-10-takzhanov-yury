package io.github.takzhanov.umbrella.hw09.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {
    @Column
    private String name;
    @Column
    private int age;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PhoneDataSet> phones = new ArrayList<>();

    public UserDataSet() {
    }

    public UserDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDataSet(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = new AddressDataSet(address);
    }

    public void addPhone(String number) {
        PhoneDataSet newPhone = new PhoneDataSet(number);
        newPhone.setUser(this);
        getPhones().add(newPhone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDataSet{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", address=").append(address);
        sb.append(", phone=").append(phones);
        sb.append('}');
        return sb.toString();
    }
}
