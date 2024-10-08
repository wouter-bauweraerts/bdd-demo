package be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ORDER_ID")
    private Integer id;
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ORDER_LINES", joinColumns = { @JoinColumn( name = "ORDER_ID", referencedColumnName = "ORDER_ID")})
    private List<OrderlineEntity> orderlines = new ArrayList<>();
}
