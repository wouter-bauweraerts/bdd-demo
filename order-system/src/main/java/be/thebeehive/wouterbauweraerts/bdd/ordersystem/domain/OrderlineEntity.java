package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderlineEntity {
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "QUANTITY")
    private Integer quantity;
}
