package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.services.RecipeService;
import recipes.services.UserDetailsServiceImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/recipe/new")
    public ResponseEntity postRecipe(@Valid @RequestBody Recipe recipe,
                                     @AuthenticationPrincipal UserDetails user) {
        recipe.setAuthor(user.getUsername());
        recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(Map.of("id", recipe.getId()));
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.status(404).body(new ConcurrentHashMap<>(
                    Map.of("error", "Recipe not found")));
        }
        return ResponseEntity.ok(recipe);
    }

    @Transactional
    @DeleteMapping("/recipe/{id}")
    public ResponseEntity deleteRecipe(@PathVariable int id,
                                       @AuthenticationPrincipal UserDetails user) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.status(404).body(new ConcurrentHashMap<>(
                    Map.of("error", "Recipe not found")));
        }
        Utility.recipeBelongsToUser(user, recipe);
        recipeService.deleteRecipeById(id);
        return ResponseEntity.status(204).body(new ConcurrentHashMap<>(
                Map.of("message", "Recipe deleted")));
    }

    @PutMapping("/recipe/{id}")
    public ResponseEntity<Map<String, String>> updateRecipe(@Valid @RequestBody Recipe recipe,
                                                            @AuthenticationPrincipal UserDetails user,
                                                            @PathVariable int id) {
        Recipe recipeToUpdate = recipeService.getRecipeById(id);
        if (recipeToUpdate == null) {
            return ResponseEntity.status(404).body(new ConcurrentHashMap<>(
                    Map.of("error", "Recipe not found")));
        }
        Utility.recipeBelongsToUser(user, recipeToUpdate);
        recipeToUpdate.update(recipe);
        recipeService.saveRecipe(recipeToUpdate);
        return ResponseEntity.status(204).body(new ConcurrentHashMap<>(
                Map.of("message", "Recipe updated")));
    }

    @GetMapping("/recipe/search")
    public List<Recipe> getRecipesWithCategory(@RequestParam(required = false) String category,
                                               @RequestParam(required = false) String name) {
        List<Recipe> recipes;
        if (category != null) {
            recipes = recipeService.getRecipesByCategory(category);
        } else if (name != null) {
            recipes = recipeService.getRecipesByName(name);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return recipes;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody User user) {
        Utility.validateEmail(user.getEmail());
        if (userDetailsService.userExist(user.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new ConcurrentHashMap<>(Map.of("error", "User already exist")));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDetailsService.save(user);
        return ResponseEntity.ok(
                new ConcurrentHashMap<>(Map.of("message", "User registered")));
    }
}
