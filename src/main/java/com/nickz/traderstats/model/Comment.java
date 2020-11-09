package com.nickz.traderstats.model;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /*
     * @ManyToOne(fetch = FetchType.EAGER)
     * 
     * @JoinColumn(name = "trader_id")
     * 
     * @JsonIgnore private Trader trader;
     */

    private int traderId;

    @Column(name = "rating")
    private int rating;

    @Column(name = "message")
    private String message;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate;

    @Column(name = "is_approved")
    private boolean isApproved;
}
