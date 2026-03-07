package vn.com.go.routex.identity.security.annotation;

import org.springframework.context.annotation.Import;
import vn.com.go.routex.identity.security.config.SecurityAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SecurityAutoConfiguration.class)
public @interface EnableIdentitySecurity {
}