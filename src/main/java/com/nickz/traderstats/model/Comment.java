package com.nickz.traderstats.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder.ObtainVia;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /*
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
	    name = "comments_trader",
	    joinColumns =  @JoinColumn(name = "comment_id", referencedColumnName = "id"),
	    inverseJoinColumns = @JoinColumn(name = "trader_id", referencedColumnName = "id")
	    )
//    @JsonIgnore
    private Trader trader;
    */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Trader trader;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "message")
    private String message;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Override
    public int hashCode() {
	return 31;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!(obj instanceof Comment)) {
	    return false;
	}
	Comment other = (Comment) obj;
	if (this.id != other.getId()) {
	    return false;
	}
	return true;
    }
     
}
