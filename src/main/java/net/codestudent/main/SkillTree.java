package net.codestudent.main;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkillTree {

    public static final SkillNode MIGHT_ROOT = new SkillNode(
        "might_root", "Path of Might", "You favor the edge of a blade over a shield's cover.",
        SkillNode.Branch.MIGHT, 0, 0, 0, 0, 0);

    public static final SkillNode GUARD_ROOT = new SkillNode(
        "guard_root", "Path of the Guard", "You favor sturdy footing and a raised shield.",
        SkillNode.Branch.GUARD, 0, 0, 0, 0, 0);

    public static final SkillNode POWER_STRIKE = new SkillNode(
        "power_strike", "Power Strike", "You put your full weight behind every swing.",
        SkillNode.Branch.MIGHT, 1, 1, 0, 0, 0);

    public static final SkillNode BULWARK = new SkillNode(
        "bulwark", "Bulwark", "You've learned to turn a blade rather than take it.",
        SkillNode.Branch.GUARD, 1, 0, 1, 0, 0);

    public static final SkillNode CLEAVE = new SkillNode(
        "cleave", "Cleave", "Your strikes carry through, opening deeper wounds.",
        SkillNode.Branch.MIGHT, 2, 0, 0, 0, 2);

    public static final SkillNode DEADEYE = new SkillNode(
        "deadeye", "Deadeye", "You find the gaps in any guard.",
        SkillNode.Branch.MIGHT, 2, 2, 0, 0, 0);

    public static final SkillNode PARRY = new SkillNode(
        "parry", "Parry", "You read an opponent's attack before it lands.",
        SkillNode.Branch.GUARD, 2, 0, 2, 0, 0);

    public static final SkillNode SECOND_WIND = new SkillNode(
        "second_wind", "Second Wind", "You've learned to keep fighting past the pain.",
        SkillNode.Branch.GUARD, 2, 0, 0, 8, 0);

    public static final SkillNode CHAMPIONS_RESOLVE = new SkillNode(
        "champions_resolve", "Champion's Resolve", "Whichever path you walked, you've mastered its lesson.",
        SkillNode.Branch.ANY, 3, 1, 1, 0, 0);

    private static final Map<String, SkillNode> BY_ID = Stream.of(
            MIGHT_ROOT, GUARD_ROOT, POWER_STRIKE, BULWARK, CLEAVE, DEADEYE, PARRY, SECOND_WIND, CHAMPIONS_RESOLVE
        ).collect(Collectors.toMap(SkillNode::id, node -> node));

    private SkillTree() {}

    public static SkillNode byId(String id) {
        SkillNode node = BY_ID.get(id);
        if (node == null)
            throw new IllegalArgumentException("Unknown skill id: " + id);
        return node;
    }

    public static List<SkillNode> choicesForTier(int tier, SkillNode.Branch branch) {
        return switch (tier) {
            case 0 -> List.of(MIGHT_ROOT, GUARD_ROOT);
            case 1 -> branch == SkillNode.Branch.MIGHT ? List.of(POWER_STRIKE) : List.of(BULWARK);
            case 2 -> branch == SkillNode.Branch.MIGHT ? List.of(CLEAVE, DEADEYE) : List.of(PARRY, SECOND_WIND);
            case 3 -> List.of(CHAMPIONS_RESOLVE);
            default -> List.of();
        };
    }
}
