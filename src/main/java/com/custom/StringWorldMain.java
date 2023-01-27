package com.custom;


import akka.actor.typed.ActorSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringWorldMain {

    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create(StringProcessor.create(), "palindrome-system");
        system.tell(new StringProcessor.Request("Wow, It is a beautiful day."));
        try {
            System.out.println("Press ENTER to exit the system");
            System.in.read();
        } finally {
            system.terminate();
        }
    }
}