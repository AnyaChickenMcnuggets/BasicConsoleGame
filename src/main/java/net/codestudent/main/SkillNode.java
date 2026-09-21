package net.codestudent.main;

public record SkillNode(
    String id,
    String name,
    String description,
    Branch branch,
    int tier,
    int attackBonusDelta,
    int armorClassDelta,
    int maxHpDelta,
    int bonusDamageDelta
) {
    public enum Branch { MIGHT, GUARD, ANY }
}
