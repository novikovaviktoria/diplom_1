import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;
import java.util.List;
import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient;
    @Mock
    private Ingredient sauce;
    @Mock
    private Ingredient filling;
    private Burger burger;

    @Before
    public void createNewBurger() {
        burger = new Burger();
    }

    @Test
    public void setBuns() {
        burger.setBuns(bun);
        Bun actual = burger.bun;

        assertEquals("Неправильное количество булочек в бургере", bun, actual);
    }

    @Test
    public void addOrderedIngredient() {
        burger.addIngredient(ingredient);
        List<Ingredient> expected = List.of(ingredient);
        List<Ingredient> actual = burger.ingredients;

        assertEquals("Добавление в бургер не заказанного ингредиента", expected, actual);
    }

    @Test
    public void removeExpectedIngredient() {
        burger.addIngredient(ingredient);
        burger.removeIngredient(0);
        List<Ingredient> actual = burger.ingredients;

        assertEquals("Удаление ожидаемого ингредиента", List.of(), actual);
    }

    @Test
    public void moveActualIngredientPlacement() {
        burger.addIngredient(filling);
        burger.addIngredient(sauce);
        burger.moveIngredient(0, 1);
        Ingredient actual = burger.ingredients.get(1);

        assertEquals("Перемещение ингредиента на правильную позицию", filling, actual);
    }

    @Test
    public void getClientOrderedReceipt() {
        Mockito.when(bun.getName()).thenReturn("flureschentnaya");
        Mockito.when(bun.getPrice()).thenReturn(988.0f);
        burger.setBuns(bun);

        Mockito.when(ingredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(ingredient.getName()).thenReturn("spicy-x");
        Mockito.when(ingredient.getPrice()).thenReturn(90.0f);
        burger.addIngredient(ingredient);

        StringBuilder receipt = new StringBuilder(String.format("(==== %s ====)%n", bun.getName()));
        List<Ingredient> ingredients = burger.ingredients;

        for (Ingredient ingredient : ingredients) {
            receipt.append(String.format("= %s %s =%n", ingredient.getType().name().toLowerCase(),
                    ingredient.getName()));
        }

        receipt.append(String.format("(==== %s ====)%n", bun.getName()));
        receipt.append(String.format("%nPrice: %f%n", burger.getPrice()));

        String expected = receipt.toString();
        String actual = burger.getReceipt();

        assertEquals("Неправильный рецепт бургера", expected, actual);
    }
}