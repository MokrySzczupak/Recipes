package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class Recipe {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column
    private String description;

    @NotBlank
    @Column
    private String category;

    @Column
    private LocalDateTime date = LocalDateTime.now();

    @NotNull(message = "Ingredients cannot be empty")
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NotNull(message = "Directions cannot be empty")
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;

    @JsonIgnore
    private String author;

    public void update(Recipe recipe) {
        this.category = recipe.category;
        this.name = recipe.name;
        this.description = recipe.description;
        this.date = recipe.date;
        this.directions = recipe.directions;
        this.ingredients = recipe.ingredients;
        this.updateDate();
    }

    public void updateDate() {
        this.date = LocalDateTime.now();
    }


}

