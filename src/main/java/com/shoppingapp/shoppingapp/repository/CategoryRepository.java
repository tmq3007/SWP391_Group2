package com.shoppingapp.shoppingapp.repository;

import com.shoppingapp.shoppingapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //boolean existsByCategoryId(Long categoryId);
    boolean existsByCategoryName(String categoryName);

    @Query(value = "SELECT c.* FROM category c JOIN product p ON c.category_id = p.category_id GROUP BY c.category_id ORDER BY COUNT(p.product_id) DESC LIMIT 10", nativeQuery = true)
    List<Category> findTop10ByMostProducts();
}
