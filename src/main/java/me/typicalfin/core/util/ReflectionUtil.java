package me.typicalfin.core.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ReflectionUtil {

    public Field getField(Class<?> clazz, String name) {
        try {
            final Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);

            return field;
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Object getFieldValue(Class<?> clazz, String name) {
        return getFieldValue(clazz, name, null);
    }

    public Object getFieldValue(Class<?> clazz, String name, Object instance) {
        final Field field = getField(clazz, name);

        try {
            return field.get(instance);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void setFieldValue(Class<?> clazz, String name, Object value) {
        setFieldValue(clazz, name, value, null);
    }

    public void setFieldValue(Class<?> clazz, String name, Object value, Object instance) {
        final Field field = getField(clazz, name);

        try {
            field.set(value, instance);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

}
