# Recipes

Spring Boot multi-user web service that allows storing, retrieving, updating, and deleting recipes.

# REST API Endpoints
|       METHOD         |URL                          |DESCRIPTION                       |ROLE|
|----------------|-------------------------------|-----------------------------|-----------------------------|
|POST|`/api/register`|To register new user.|EVERYONE|
|POST|`/api/recipe/new`|To create new recipe.|AUTHENTICATED|
|PUT|`/api/recipe/{recipe_id}`|To edit certain recipe.|AUTHENTICATED|
|GET|`/api/recipe/{recipe_id}`|To get certain recipe.|AUTHENTICATED|
|DELETE|`/api/recipe/{recipe_id}`|To delete certain recipe.|AUTHENTICATED|
|GET|`/api/recipe/search`|To search for recipes with category and name.|AUTHENTICATED|

# Security
Service is protected by basic HTTP authentication. For storing users and passwords I added a JDBC implementation of `UserDetailsService` with an **H2 database**;
