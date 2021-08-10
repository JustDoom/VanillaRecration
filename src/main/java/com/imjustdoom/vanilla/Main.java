package com.imjustdoom.vanilla;

import net.minestom.server.extensions.Extension;

public class Main extends Extension {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public Main(){
        instance = this;
    }

    public void initialize() {
        
    }

    public void terminate() {

    }
}