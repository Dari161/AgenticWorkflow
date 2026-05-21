import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

final class StructureAssertions {
    private StructureAssertions() {
    }

    static Class<?> classNamed(String className) {
        return assertDoesNotThrow(() -> Class.forName(className), "Missing class: " + className);
    }

    static void assertPublicClass(Class<?> type) {
        assertTrue(Modifier.isPublic(type.getModifiers()), type.getName() + " should be public");
    }

    static void assertPublicEnumElements(Class<?> enumType, String... expectedNames) {
        assertTrue(enumType.isEnum(), enumType.getName() + " should be an enum");
        List<String> actualNames = Arrays.stream(enumType.getEnumConstants()).map(Object::toString).toList();
        assertEquals(List.of(expectedNames), actualNames);
    }

    static Field assertPrivateField(Class<?> type, String name, Class<?> fieldType) {
        Field field = assertDoesNotThrow(() -> type.getDeclaredField(name), "Missing field: " + name);
        assertEquals(fieldType, field.getType(), "Wrong type for field: " + name);
        assertTrue(Modifier.isPrivate(field.getModifiers()), name + " should be private");
        assertFalse(Modifier.isStatic(field.getModifiers()), name + " should not be static");
        return field;
    }

    static Method assertPublicMethod(Class<?> type, String name, Class<?> returnType, Class<?>... parameterTypes) {
        Method method = assertDoesNotThrow(() -> type.getDeclaredMethod(name, parameterTypes), "Missing method: " + name);
        assertTrue(Modifier.isPublic(method.getModifiers()), name + " should be public");
        assertEquals(returnType, method.getReturnType(), "Wrong return type for method: " + name);
        return method;
    }

    static Method assertPrivateStaticMethod(Class<?> type, String name, Class<?> returnType, Class<?>... parameterTypes) {
        Method method = assertDoesNotThrow(() -> type.getDeclaredMethod(name, parameterTypes), "Missing method: " + name);
        assertTrue(Modifier.isPrivate(method.getModifiers()), name + " should be private");
        assertTrue(Modifier.isStatic(method.getModifiers()), name + " should be static");
        assertEquals(returnType, method.getReturnType(), "Wrong return type for method: " + name);
        return method;
    }

    static Constructor<?> assertPublicConstructor(Class<?> type, Class<?>... parameterTypes) {
        Constructor<?> constructor = assertDoesNotThrow(
                () -> type.getDeclaredConstructor(parameterTypes),
                "Missing constructor on " + type.getName()
        );
        assertTrue(Modifier.isPublic(constructor.getModifiers()), "Constructor should be public");
        return constructor;
    }

    static void assertNoPublicSetter(Class<?> type, String propertyName) {
        String setterName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        boolean hasSetter = Arrays.stream(type.getMethods()).anyMatch(method -> method.getName().equals(setterName));
        assertFalse(hasSetter, "Unexpected public setter: " + setterName);
    }

    static void assertCheckedException(Class<?> type) {
        assertTrue(Exception.class.isAssignableFrom(type), type.getName() + " should extend Exception");
        assertFalse(RuntimeException.class.isAssignableFrom(type), type.getName() + " should be checked");
    }
}
