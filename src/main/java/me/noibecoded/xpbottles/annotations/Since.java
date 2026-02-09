package me.noibecoded.xpbottles.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Since {
    String version();
    String note() default "";
}
