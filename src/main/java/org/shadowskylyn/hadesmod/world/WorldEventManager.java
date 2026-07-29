package org.shadowskylyn.hadesmod.world;

import org.shadowskylyn.hadesmod.event.WorldEvent;

public class WorldEventManager {

    private static WorldEvent currentEvent = WorldEvent.NONE;

    public static WorldEvent getCurrentEvent() {
        return currentEvent;
    }

    public static boolean isEvent(WorldEvent event) {
        return currentEvent == event;
    }

    public static void startEvent(WorldEvent event) {
        currentEvent = event;
    }

    public static void endEvent() {
        currentEvent = WorldEvent.NONE;
    }

}