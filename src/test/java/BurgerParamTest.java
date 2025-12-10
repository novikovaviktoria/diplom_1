
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParamTest {
    private Burger burger;
    private Ingredient sauce;
    private Ingredient filling;
    private final String name;
    private final float price;

    public BurgerParamTest(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Before
    public void createNewBurger() {
        sauce = new Ingredient(IngredientType.SAUCE, "spicy-x", 90.0f);
        filling = new Ingredient(IngredientType.FILLING, "cheese", 4142.0f);
        burger = new Burger();
    }

    @Parameterized.Parameters(name = "Price {0} : {1}")
    public static Object[][] getBunData() {
        return new Object[][] {
                {"flureschentnaya", 988.0f},
                {"kratornaya", 1255.0f}
        };
    }

    @Test
    public void getWrightBurgerPrice() {
        Bun bun = new Bun(name, price);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        float expected = bun.price * 2 + sauce.price + filling.price;
        float actual = burger.getPrice();

        assertEquals("Неправильный рассчет стоимости бургера", expected, actual, 0);
    }
}