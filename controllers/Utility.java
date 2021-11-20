package recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;

public class Utility {
    static void recipeBelongsToUser(UserDetails user, Recipe recipe) throws ResponseStatusException {
        if (!user.getUsername().equals(recipe.getAuthor())) {
            System.out.println("user: " + user.getUsername() + " author: " + recipe.getAuthor());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You dont have permission");
        }
    }

    static void validateEmail(String email) throws ResponseStatusException {
        if (!email.matches(".*[@].*[.].*")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email");
        }
    }
}
