package io.github.takzhanov.umbrella.hw09.domain;


import javax.persistence.*;

@MappedSuperclass
public abstract class DataSet {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
