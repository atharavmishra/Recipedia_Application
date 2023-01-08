package recipedia.myapplication.myapplication;

import recipedia.myapplication.modelClass.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APInterface {
    String BASE_URL="https://api.spoonacular.com/recipes/";
    @GET("complexSearch")
    Call<Root> getImage(
            @Query("apiKey") String authkey,
            @Query("addRecipeInformation") boolean val

    );
    @GET("complexSearch")
    Call<Root> getImage2(
            @Query("apiKey") String authkey,
            @Query("addRecipeInformation") boolean val,
            @Query("query") String query
    );
    @GET("complexSearch")
    Call<Root> getImageveg(
            @Query("apiKey") String authkey,
            @Query("addRecipeInformation") boolean val,
            @Query("diet") String diet

    );

}
