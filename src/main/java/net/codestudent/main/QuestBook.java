package net.codestudent.main;

public class QuestBook {

    public static final Quest WOLVES_AT_THE_DOOR = new Quest(
        "wolves_at_the_door",
        "Wolves at the Door",
        "Marsh wolves have been picking off livestock at the village edge. Elder Maren wants three of them dead.",
        Bestiary.MARSH_WOLF.id(),
        3,
        30,
        20,
        ItemCatalog.WARDENS_CLOAK.id()
    );

    private QuestBook() {}
}
