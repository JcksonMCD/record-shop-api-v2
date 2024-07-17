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
@Builder // Builder pattern used as Album class has more than four parameters and may have more as application evolves.
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "album_id")
    private String albumName;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    private Genre genre;

    @Column(name = "art_url")
    private String artUrl;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "stock_quantity")
    private int stockQuantity;
}
