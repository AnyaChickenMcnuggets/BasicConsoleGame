package net.codestudent.main;

import java.util.List;

public record Npc(String name, String description, List<String> dialogue, Quest quest) {

    public Npc(String name, String description, List<String> dialogue) {
        this(name, description, dialogue, null);
    }
}
