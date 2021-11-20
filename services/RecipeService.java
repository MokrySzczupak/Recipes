package recipes.services;

import org.springframework.stereotype.Service;
import recipes.model.Recipe;

import java.util.List;

@Service
public class RecipeService {
    RecipeRepository recipeRepository;

    RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(int id) {
        return recipeRepository.findRecipeById(id);
    }

    public void deleteRecipeById(int id) {
        recipeRepository.deleteRecipeById(id);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findRecipesByCategoryIsLikeIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> getRecipesByName(String name) {
        return recipeRepository.findRecipesByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
