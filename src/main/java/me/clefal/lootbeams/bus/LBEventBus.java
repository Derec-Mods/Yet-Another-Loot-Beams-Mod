package me.clefal.lootbeams.bus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * From https://github.com/TUsama/NirvanaLib
 * Nirvana was out of date for 1.21.x and we needed this in-tree.
 */
public class LBEventBus {
    private final List<Listener> listeners = new CopyOnWriteArrayList<>();

    public void register(Object target) {
        if (target == null) {
            return;
        }
        unregister(target);
        Class<?> type = target.getClass();
        List<Listener> added = new ArrayList<>();
        while (type != null && type != Object.class) {
            for (Method method : type.getDeclaredMethods()) {
                SubscribeEvent subscribe = method.getAnnotation(SubscribeEvent.class);
                if (subscribe == null) {
                    continue;
                }
                Class<?>[] parameters = method.getParameterTypes();
                if (parameters.length != 1 || !Event.class.isAssignableFrom(parameters[0])) {
                    continue;
                }
                method.setAccessible(true);
                added.add(new Listener(target, method, parameters[0], subscribe.priority()));
            }
            type = type.getSuperclass();
        }
        added.sort(Comparator.comparingInt(listener -> listener.priority.ordinal()));
        listeners.addAll(added);
        listeners.sort(Comparator.comparingInt(listener -> listener.priority.ordinal()));
    }

    public void unregister(Object target) {
        if (target == null) {
            return;
        }
        listeners.removeIf(listener -> listener.target == target);
    }

    public void post(Event event) {
        if (event == null) {
            return;
        }
        for (Listener listener : listeners) {
            if (!listener.eventType.isAssignableFrom(event.getClass())) {
                continue;
            }
            if (event instanceof ICancellableEvent && event.isCanceled()) {
                continue;
            }
            try {
                listener.method.invoke(listener.target, event);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e.getCause() != null ? e.getCause() : e);
            }
        }
    }

    private record Listener(Object target, Method method, Class<?> eventType, EventPriority priority) {
    }
}
