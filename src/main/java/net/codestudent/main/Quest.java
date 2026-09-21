package net.codestudent.main;

public record Quest(
    String id,
    String title,
    String description,
    String targetEnemyId,
    int requiredCount,
    int rewardGold,
    int rewardXp,
    String rewardItemId
) {}
