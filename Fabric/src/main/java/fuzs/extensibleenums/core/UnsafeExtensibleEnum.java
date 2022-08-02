package fuzs.extensibleenums.core;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

/**
 * a helper class for extending enums, this uses {@link Unsafe} as reflection is no longer a viable option for extending enums in Java 12+
 * only use for enums which are not used in switch statements since they will break otherwise (mainly the newer fancy ones where the default case can be omitted)
 */
public class UnsafeExtensibleEnum {
    /**
     * the unsafe referent required for most operations
     */
    private static final Unsafe UNSAFE;

    static {
        try {
            Constructor<?> constructor = Unsafe.class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            UNSAFE = (Unsafe) constructor.newInstance();
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     *
     * @param enumMainClass the enum class to add a constant to, also just for instantiating new constant
     * @param internalName name of the new enum value
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, String internalName) throws Throwable {
        return invokeEnumConstructor(enumMainClass, enumMainClass, internalName);
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     *
     * @param enumMainClass the enum class to add a constant to, also just for instantiating new constant
     * @param internalName name of the new enum value
     * @param internalId ordinal id, controls if the new enum constant is properly added to enum values, set this as -1 to do so, specify a different value to skip it
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, String internalName, int internalId) throws Throwable {
        return invokeEnumConstructor(enumMainClass, enumMainClass, internalName, internalId);
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     *
     * @param enumMainClass the enum class to add a constant to
     * @param enumConcreteClass the enum class to create the constant from, should usually be the same as <code>enumMainClass</code>, but in case of an abstract enum supply the class of any of its constants
     * @param internalName name of the new enum value
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, Class<? extends T> enumConcreteClass, String internalName) throws Throwable {
        return invokeEnumConstructor(enumMainClass, enumConcreteClass, internalName, -1);
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     *
     * @param enumMainClass the enum class to add a constant to
     * @param enumConcreteClass the enum class to create the constant from, should usually be the same as <code>enumMainClass</code>, but in case of an abstract enum supply the class of any of its constants
     * @param internalName name of the new enum value
     * @param internalId ordinal id, controls if the new enum constant is properly added to enum values, set this as -1 to do so, specify a different value to skip it
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, Class<? extends T> enumConcreteClass, String internalName, int internalId) throws Throwable {
        T enumValue = enumMainClass.cast(UNSAFE.allocateInstance(enumConcreteClass));
        boolean addToEnumValues = internalId == -1;
        if (addToEnumValues) {
            internalId = addToEnumValues(enumMainClass, enumValue, internalName);
        }
        initEnumFields(enumValue, internalName, internalId);
        if (addToEnumValues) {
            cleanEnumCache(enumMainClass);
        }
        return enumValue;
    }

    /**
     * add new enum constant to enum values array
     *
     * @param enumMainClass enum class containing internal <code>$VALUES</code> field for adding our new constant
     * @param enumValue enum constant to add
     * @param internalName  name required for verifying no enum constant of the same name already exists
     * @param <T> enum type
     * @return ordinal position <code>enumValue</code> was added to <code>$VALUES</code> at
     *
     * @throws ReflectiveOperationException thrown by reflective operations
     */
    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> int addToEnumValues(Class<T> enumMainClass, T enumValue, String internalName) throws ReflectiveOperationException {
        for (Field field : enumMainClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType().isArray()) {
                field.setAccessible(true);
                // does not work in Java 12+ due to private field members of Field.class no longer being accessible via reflection
//                Field modifiers = field.getClass().getDeclaredField("modifiers");
//                modifiers.setAccessible(true);
//                modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                T[] values = (T[]) field.get(null);
                for (T value : values) {
                    if (value.name().equals(internalName)) {
                        throw new IllegalArgumentException(String.format("%s already exists in enum class %s", internalName, enumMainClass.getName()));
                    }
                }
                T[] modifiedValues = Arrays.copyOf(values, values.length + 1);
                modifiedValues[modifiedValues.length - 1] = enumValue;
                // use more unsafe hacks here since modifying final fields no longer works via reflection
//                field.set(null, modifiedValues);
                setStaticField(field, modifiedValues);
                return values.length;
            }
        }
        throw new IllegalAccessException("Could not find enum values field");
    }

    /**
     * since we created our enum constant using unsafe, we need to set all fields manually
     * this only sets default enum fields, every field specific to that particular enum needs to be manually set by the user
     *
     * @param enumValue enum value to set fields for
     * @param internalName value name
     * @param internalId ordinal id, this comes from {@link #addToEnumValues} depending on where in the internal <code>$VALUES</code> array our new constant has been added
     * @throws ReflectiveOperationException thrown by reflective operations duh
     */
    private static void initEnumFields(Enum<?> enumValue, String internalName, int internalId) throws ReflectiveOperationException {
        Field ordinalField = Enum.class.getDeclaredField("ordinal");
//        ordinalField.setAccessible(true);
//        ordinalField.setInt(enumValue, internalId);
        setIntField(ordinalField, enumValue, internalId);
        Field nameField = Enum.class.getDeclaredField("name");
//        nameField.setAccessible(true);
//        nameField.set(enumValue, internalName);
        setField(nameField, enumValue, internalName);
    }

    /**
     * set a value to a field object using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue object value to set
     */
    private static void setField(Field field, Object instance, Object newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putObject(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field object using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue int value to set
     */
    private static void setIntField(Field field, Object instance, int newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putInt(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a static field object using unsafe (since reflection will not work with final fields)
     *
     * @param field static field to set
     * @param newValue object value to set
     */
    private static void setStaticField(Field field, Object newValue) {
        final Object staticFieldBase = UNSAFE.staticFieldBase(field);
        final long staticFieldOffset = UNSAFE.staticFieldOffset(field);
        UNSAFE.putObject(staticFieldBase, staticFieldOffset, newValue);
    }

    /**
     * from Forge, probably supposed to clear enum cache used for switch statements
     * not sure if this is needed, cache doesn't seem to be an issue (maybe with some compilers?)
     *
     * @param enumClass enum class to clear cache for
     */
    private static void cleanEnumCache(Class<? extends Enum<?>> enumClass) {
        findField(Class.class, "enumConstantDirectory").ifPresent(field -> setField(field, enumClass, null));
        findField(Class.class, "enumConstants").ifPresent(field -> setField(field, enumClass, null));
    }

    /**
     * get a field from class as optional to make sure we don't crash if any future versions change field names
     *
     * @param clazz class type
     * @param target field name
     * @return the field
     */
    private static Optional<Field> findField(Class<?> clazz, String target) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(target)) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }
}
