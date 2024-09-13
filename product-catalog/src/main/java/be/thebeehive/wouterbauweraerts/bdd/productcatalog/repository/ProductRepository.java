package be.thebeehive.wouterbauweraerts.bdd.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
