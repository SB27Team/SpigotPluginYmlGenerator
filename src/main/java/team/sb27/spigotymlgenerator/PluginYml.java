package team.sb27.spigotymlgenerator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface PluginYml {

    double version();

    String name();

    String author();

    String[] dependencys() default {};
    String[] softDependencys() default {};

}
