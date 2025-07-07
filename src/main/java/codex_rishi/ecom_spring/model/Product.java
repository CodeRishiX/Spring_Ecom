package codex_rishi.ecom_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private BigDecimal price;
    @Lob
    private String description;
    private String brand;
    private String category;
    private Date releaseDate;
    private int quantity;
    private String image;
    private String imagetype;
    @Lob //Large object
    private byte[] imagedata;



}
