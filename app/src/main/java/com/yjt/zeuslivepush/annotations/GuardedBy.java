package com.yjt.zeuslivepush.annotations;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The presence of this annotation indicates that the field or method must only be accessed when holding the specified
 * lock.
 */
//https://github.com/stephenc/jcip-annotations/blob/master/src/main/java/net/jcip/annotations/GuardedBy.java
@Documented
@Target(value = {FIELD, METHOD})
@Retention(RUNTIME)
public @interface GuardedBy {
    /**
     * The specified lock that guards the annotated field or method. Valid values are:
     * <ul>
     * <li>{@code this} indicates the intrinsic lock of the instance containing the field or method.</li>
     * <li><code><i>class-name</i>.this</code> which allows for disambiguation of which {@code this} when dealing
     * with inner classes</li>
     * <li>{@code itself} which is valid for reference fields only, and indicates that the referenced instance's
     * own intrinsic lock should be used as the guard</li>
     * <li><code><i>field-name</i></code> indicates the named instance or static field is to be used as the guard. If
     * the field type is not a sub-type of {@link java.util.concurrent.locks.Lock} then the intrinsic lock of
     * the referenced instance is to be used</li>
     * <li><code><i>class-name</i>.<i>field-name</i></code> indicates the named static field is to be used as the
     * guard. If the field type is not a sub-type of {@link java.util.concurrent.locks.Lock} then the intrinsic lock of
     * the referenced instance is to be used</li>
     * <li><code><i>method-name</i>()</code> indicates that the zero-argument method should be called to obtain the
     * lock object. If the return type is not a sub-type of {@link java.util.concurrent.locks.Lock} then the intrinsic
     * lock of the returned instance is to be used</li>
     * <li><code><i>class-name</i>.class</code> indicates that the intrinsic lock of the specified class should be used
     * as the guard</li>
     * </ul>
     *
     * @return The specified lock that guards the annotated field or method
     */
    String value();
}
