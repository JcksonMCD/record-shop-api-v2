package com.northcoders.record_shop_api_v2.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "albums")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "album_name")
    private String albumName;

    @ManyToOne
    @JoinColumn(name = "artist_name")
    private Artist artist;

    private Genre genre;

    @Column(name = "art_url")
    private String artUrl;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "stock_quantity")
    private int stockQuantity;
}
