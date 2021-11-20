package recipes.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.model.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    Recipe findRecipeById(int id);
    void deleteRecipeById(int id);
    List<Recipe> findRecipesByCategoryIsLikeIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findRecipesByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
