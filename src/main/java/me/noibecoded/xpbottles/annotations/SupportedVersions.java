package me.noibecoded.xpbottles.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportedVersions {
    String[] versions();
    String serverType() default "BOTH"; // SPIGOT, PAPER, BOTH, FORK
}
