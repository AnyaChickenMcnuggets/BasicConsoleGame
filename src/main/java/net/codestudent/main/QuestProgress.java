package net.codestudent.main;

public class QuestProgress {

    public enum State { NOT_STARTED, ACTIVE, READY_TO_TURN_IN, COMPLETE }

    public State state = State.NOT_STARTED;
    public int killCount = 0;
}
