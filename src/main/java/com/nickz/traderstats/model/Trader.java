package com.nickz.traderstats.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trader")
@Getter
@Setter
public class Trader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "is_approved")
    private Boolean isApproved;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, optional = true)
    @JoinTable(
	    name = "trader_user",
	    joinColumns = @JoinColumn(name = "trader_id", referencedColumnName = "id"),
	    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	    )
     
    private User user;
    
}
