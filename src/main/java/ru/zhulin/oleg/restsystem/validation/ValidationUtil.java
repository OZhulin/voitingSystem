package ru.zhulin.oleg.restsystem.validation;

import ru.zhulin.oleg.restsystem.model.ParentEntity;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, Long id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, Long id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String arg) {
        checkNotFound(object != null, arg);
        return object;
    }

    public static void checkNotFound(boolean found, String arg) {
        if (!found) {
            //throw new NotFoundException(arg);
        }
    }

    public static void checkNew(ParentEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(ParentEntity entity, Long id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (!entity.getId().equals(id)) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}