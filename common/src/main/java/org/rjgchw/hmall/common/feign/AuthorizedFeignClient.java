package org.rjgchw.hmall.common.feign;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.core.annotation.AliasFor;

/**
 * authorized feign client annotation.
 *
 * @author Huangw
 * @date 2021-02-18 17:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@FeignClient
public @interface AuthorizedFeignClient {

    /**
     * service name.
     *
     * @return name
     */
    @AliasFor(annotation = FeignClient.class, attribute = "name")
    String name() default "";

    /**
     * A custom {@code @Configuration} for the feign client.
     * Can contain override {@code @Bean} definition for the pieces that
     * make up the client, for instance {@link feign.codec.Decoder},
     * {@link feign.codec.Encoder}, {@link feign.Contract}.
     *
     * @return the custom {@code @Configuration} for the feign client.
     * @see FeignClientsConfiguration for the defaults.
     */
    @AliasFor(annotation = FeignClient.class, attribute = "configuration")
    Class<?>[] configuration() default OAuth2InterceptedFeignConfiguration.class;

    /**
     * An absolute URL or resolvable hostname (the protocol is optional).
     *
     * @return the URL
     */
    String url() default "";

    /**
     * Whether 404s should be decoded instead of throwing FeignExceptions.
     *
     * @return true if 404s will be decoded; false otherwise.
     */
    boolean decode404() default false;

    /**
     * Fallback class for the specified Feign client interface. The fallback class must
     * implement the interface annotated by this annotation and be a valid Spring bean.
     *
     * @return the fallback class for the specified Feign client interface.
     */
    Class<?> fallback() default void.class;

    /**
     * Path prefix to be used by all method-level mappings.
     * Can be used with or without {@code @RibbonClient}.
     *
     * @return the path prefix to be used by all method-level mappings.
     */
    String path() default "";
}
