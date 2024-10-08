package be.thebeehive.wouterbauweraerts.bdd.ordersystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
