package me.typicalfin.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface CommandInfo {
    String name();
    String description() default "";
    String aliases() default "";
    String permission() default "";
    String permissionMessage() default "";
    String usage() default "";

    boolean tabCompletes() default false;
    CommandType type() default CommandType.BOTH;
}
