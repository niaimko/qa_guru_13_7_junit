package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import tests.TestBase;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ParamsTest extends TestBase {

    // 01 аннотация ValueSource

    @ValueSource(strings = {"Модельный ряд", "Покупателям", "Владельцам", "Авто с пробегом",
        "Дилеры", "Бренд", "Контакты"})
    @ParameterizedTest(name = "При открытии сайта lada.ru в горизонтальном меню сайта " +
            "отображается пункт \"{0}\"")
    void ladaTestHorizontalMenu(String testMenuData) {
        Selenide.open("https://lada.ru/");
        $$(".styles_item__VHi55").find(text(testMenuData)).shouldBe(visible);
    }

    // 02 аннотация CsvSource

    @CsvSource(value = {
            "ТО-0, 6 400",
            "ТО-1, 8 300",
            "ТО-2, 12 800",
            "ТО-3, 18 900",
            "ТО-4, 15 800",
            "ТО-5, 12 900",
            "ТО-6, 18 700"
    })
    @ParameterizedTest(name = "При выборе типа {0} для авто Niva Travel в результатах " +
            "отображается цена {1}")
    void nivaTravelCheckTechnicalInspectionPrice(String technicalInspectionType, String technicalInspectionPrice) {
        Selenide.open("https://lada.ru/");
        $(".styles_item__VHi55[data-index='2']").click();
        $$("li.burger-menu__base-name a").findBy(text("Калькулятор ТО")).click();
        $$(".styles_info__3zElt ul.styles_horizontalTabs__Fy_3W li span")
                .find(text("Niva Travel")).click();
        $(".styles_info__3zElt button[type='button']").click();
        $$(".styles_container__11Yj7 ul.styles_horizontalTabs__Fy_3W li span")
                .find(text(technicalInspectionType)).click();
        $$(".styles_price__2f-DO").find(text(technicalInspectionPrice)).shouldBe(visible);
    }

    // 03 аннотация CsvFileSource

    @CsvFileSource(resources = "/test_data/menu_models.csv")
    @ParameterizedTest(name = "При нажатии на пункт меню \"Модельный ряд\" в открывшемся списке " +
            "доступен пункт \"{0}\"")
    void ladaTestCheckModelRange(String testModelsData) {
        Selenide.open("https://lada.ru/");
        $(".styles_item__VHi55[data-index='0']").click();
        $$("li.burger-menu__base-name").find(text(testModelsData)).shouldBe(visible);
    }

    // 04 аннотация MethodSource

    static Stream<Arguments> checkAccessorySearch() {
        return Stream.of(
                Arguments.of("Магнитола", List.of("99999000420120"), List.of("Автомобильная магнитола 1DIN " +
                        "Pioneer MVH-29BT (укороченное шасси, USB-ресивер, bluetooth)")),
                Arguments.of("Защита картера", List.of("99999219021120"),
                        List.of("Защита картера LADA Granta МКПП/АКПП"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "При поиске аксессуара по запросу {0} в результатах " +
            "отображается аксессуар с артикулем {1} и названием {2}")
    void checkAccessorySearch(String searchAccessory, List<String> expectedAccessoryArticle,
                              List<String> expectedAccessoryTitle) {
        Selenide.open("https://www.lada.ru/cars/accessories/granta/sedan");
        $(".styles_search__26MQV input").setValue(searchAccessory);
        $$(".styles_top__gCwvD p.styles_code__3Zd0y")
                .shouldHave(CollectionCondition.texts(expectedAccessoryArticle));
        $$(".styles_card__1qaPV h2.styles_title__2cnAf")
                .shouldHave(CollectionCondition.texts(expectedAccessoryTitle));
    }

    // 05 аннотация EnumSource

    @EnumSource(VestaEnum.class)
    @ParameterizedTest
    void enumLadaTestCheckVestaBody(VestaEnum vesta) {
        Selenide.open("https://lada.ru/");
        $(".styles_item__VHi55[data-index='0']").click();
        $$("li.burger-menu__base-name").findBy(text("Vesta")).click();
        $$(".styles_name__3_TV1").find(text(vesta.desc)).shouldBe(visible);
    }

}
