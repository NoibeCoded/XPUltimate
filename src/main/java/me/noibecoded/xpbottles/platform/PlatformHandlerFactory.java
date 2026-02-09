package me.noibecoded.xpbottles.platform;

import me.noibecoded.xpbottles.api.MinecraftVersion;
import me.noibecoded.xpbottles.api.ServerType;

public class PlatformHandlerFactory {

    private static IPlatformHandler handler;

    public static IPlatformHandler getHandler(MinecraftVersion version, ServerType serverType) {
        if (handler != null) {
            return handler;
        }

        String className = "me.noibecoded.xpbottles.platform.handlers.Handler_" + version.name();

        try {
            Class<?> handlerClass = Class.forName(className);
            handler = (IPlatformHandler) handlerClass.getDeclaredConstructor().newInstance();
            return handler;
        } catch (Exception e) {
            // Fallback to generic handler
            handler = new GenericPlatformHandler();
            return handler;
        }
    }

    public static void setHandler(IPlatformHandler customHandler) {
        handler = customHandler;
    }
}
