package io.github.takzhanov.umbrella.hw09.domain;

import javax.persistence.*;


@Entity
@Table(name = "phones")
public class PhoneDataSet extends DataSet {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDataSet user;
    @Column(name = "number")
    private String number;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
