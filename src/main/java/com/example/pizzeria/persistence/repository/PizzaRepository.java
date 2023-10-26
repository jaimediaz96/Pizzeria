package com.example.pizzeria.persistence.repository;

import com.example.pizzeria.persistence.entity.PizzaEntity;
import com.example.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String description);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String description);
    List<PizzaEntity> findTop3ByAvailableTrueOrderByPrice();
    int countByVeganTrue();

    /*
    @Query(value =
            "UPDATE pizza " +
            "SET price = :newPrice " +
            "WHERE id_pizza = :idPizza", nativeQuery = true)
    void updatePrice(@Param("idPizza") int idPizza, @Param("newPrice") double newPrice);
     */
    @Query(value =
            "UPDATE pizza " +
                    "SET price = :#{#newPizzaPrice.newPrice} " +
                    "WHERE id_pizza = :#{#newPizzaPrice.pizzaID}", nativeQuery = true)
    @Modifying
    void updatePrice(@Param("newPizzaPrice") UpdatePizzaPriceDto newPizzaPrice);
}
