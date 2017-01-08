package com.yjt.zeuslivepush.utils;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/17
 */
public class Assert {
    public Assert() {
    }

    public static <T> T fail(Object detailMessage) {
        throw new AssertionError(detailMessage);
    }

    public static <T> T fail() {
        throw new AssertionError("failure");
    }

    public static void assertNotNull(Object value) {
        if(value == null) {
            throw new AssertionError("unexpected null");
        }
    }

    public static void assertNull(Object actual) {
        assertEquals((Object)null, actual);
    }

    public static void assertTrue(Object actual) {
        assertEquals(Boolean.valueOf(true), actual);
    }

    public static void assertFalse(Object actual) {
        assertEquals(Boolean.valueOf(false), actual);
    }

    public static void assertEquals(int expected, int actual) {
        assertEquals(Integer.valueOf(expected), Integer.valueOf(actual));
    }

    public static void assertEquals(Object expected, Object actual) {
        if(expected != actual) {
            if(expected == null || actual == null || !expected.equals(actual)) {
                throw new AssertionError("expected " + expected + ", got " + actual);
            }
        }
    }

    public static void assertNotEquals(Object expected, Object actual) {
        if(expected.equals(actual)) {
            throw new AssertionError("unexpected equality: " + actual);
        }
    }

    public static void assertSame(Object expected, Object actual) {
        if(expected != actual) {
            throw new AssertionError("expected " + expected + ", got " + actual);
        }
    }

    public static void assertNotSame(Object expected, Object actual) {
        if(expected == actual) {
            throw new AssertionError("unexpected " + actual);
        }
    }

    public static void assertGreaterThan(int actual, int value) {
        if(actual <= value) {
            throw new AssertionError("unexpected " + actual + " <= " + value);
        }
    }

    public static void assertLessOrEqual(int actual, int value) {
        if(actual > value) {
            throw new AssertionError("unexpected " + actual + " > " + value);
        }
    }
}
