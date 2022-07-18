package guru.qa;

public enum VestaEnum {
    VESTA_SEDAN("Vesta седан"),
    VESTA_CROSS("Vesta Cross"),
    VESTA_SW("Vesta SW"),
    VESTA_SW_CROSS("Vesta SW Cross"),
    VESTA_CNG("Vesta CNG"),
    VESTA_SPORT("Vesta Sport");

    public final String desc;

    VestaEnum(String desc) {
        this.desc = desc;
    }
}
