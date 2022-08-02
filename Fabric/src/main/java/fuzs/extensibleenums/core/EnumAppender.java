package fuzs.extensibleenums.core;

import com.google.common.collect.ImmutableList;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnumAppender<T extends Enum<T>> {
    private final Class<T> enumClazz;
    private final List<Field> fields;

    private EnumAppender(Class<T> enumClazz, List<FieldAccess> fields) {
        this.enumClazz = enumClazz;
        this.fields = fields.stream().map(f -> findField(enumClazz, f.ordinal(), f.clazz())).collect(ImmutableList.toImmutableList());
    }

    public T add(String enumValueName, Object... args) {
        T enumConstant;
        try {
            enumConstant = UnsafeExtensibleEnum.invokeEnumConstructor(this.enumClazz, enumValueName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        List<Field> fieldList = this.fields;
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            Object arg = args[i];
            if (arg == null) continue;
            Class<?> clazz = field.getType();
            if (arg.getClass() != clazz) throw new IllegalArgumentException("Class type mismatch between %s and %s".formatted(arg.getClass(), clazz));
            long objectFieldOffset = UnsafeExtensibleEnum.UNSAFE.objectFieldOffset(field);
            if (clazz == Integer.class) {
                UnsafeExtensibleEnum.UNSAFE.putInt(enumConstant, objectFieldOffset, (Integer) arg);
            } else if (clazz == Boolean.class) {
                UnsafeExtensibleEnum.UNSAFE.putBoolean(enumConstant, objectFieldOffset, (Boolean) arg);
            } else if (clazz == Byte.class) {
                UnsafeExtensibleEnum.UNSAFE.putByte(enumConstant, objectFieldOffset, (Byte) arg);
            } else if (clazz == Short.class) {
                UnsafeExtensibleEnum.UNSAFE.putShort(enumConstant, objectFieldOffset, (Short) arg);
            } else if (clazz == Character.class) {
                UnsafeExtensibleEnum.UNSAFE.putChar(enumConstant, objectFieldOffset, (Character) arg);
            } else if (clazz == Long.class) {
                UnsafeExtensibleEnum.UNSAFE.putLong(enumConstant, objectFieldOffset, (Long) arg);
            } else if (clazz == Float.class) {
                UnsafeExtensibleEnum.UNSAFE.putFloat(enumConstant, objectFieldOffset, (Float) arg);
            } else if (clazz == Double.class) {
                UnsafeExtensibleEnum.UNSAFE.putDouble(enumConstant, objectFieldOffset, (Double) arg);
            } else {
                UnsafeExtensibleEnum.UNSAFE.putObject(enumConstant, objectFieldOffset, arg);
            }
        }
        return enumConstant;
    }

    private static Field findField(Class<? extends Enum<?>> enumClazz, int ordinal, Class<?> clazz) {
        for (Field field : enumClazz.getDeclaredFields()) {
            if (field.getType() == clazz && ordinal-- == 0) return field;
        }
        throw new IllegalStateException("No field of type %s found at ordinal %s in enum class %s".formatted(clazz, ordinal, enumClazz));
    }

    public static <T extends Enum<T>> EnumAppender<T>create(Class<T> enumClazz) {
        return new EnumAppender<>(enumClazz, ImmutableList.of());
    }

    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    private record FieldAccess(int ordinal, Class<?> clazz) {

    }
}
