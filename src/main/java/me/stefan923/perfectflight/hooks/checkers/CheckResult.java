package me.stefan923.perfectflight.hooks.checkers;

public enum CheckResult {

    ALLOWED("Auto Flight Mode.Enabled", true),
    PRIVATE_TERRITORY("Auto Flight Mode.Disabled.Can Not Fly Here", false),
    NEARBY_ENEMIES("Auto Flight Mode.Disabled.Nearby Enemies", false);

    private final String langOption;
    private final boolean canFly;

    CheckResult(String langOption, boolean canFly) {
        this.langOption = langOption;
        this.canFly = canFly;
    }

    public String getLangOption() {
        return langOption;
    }

    public boolean canFly() {
        return canFly;
    }

}
