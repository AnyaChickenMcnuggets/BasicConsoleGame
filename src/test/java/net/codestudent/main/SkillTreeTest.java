package net.codestudent.main;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkillTreeTest {

    @Test
    void tierZeroOffersBothRoots() {
        List<SkillNode> options = SkillTree.choicesForTier(0, null);
        assertEquals(List.of(SkillTree.MIGHT_ROOT, SkillTree.GUARD_ROOT), options);
    }

    @Test
    void tierOneIsAutomaticPerBranch() {
        assertEquals(List.of(SkillTree.POWER_STRIKE), SkillTree.choicesForTier(1, SkillNode.Branch.MIGHT));
        assertEquals(List.of(SkillTree.BULWARK), SkillTree.choicesForTier(1, SkillNode.Branch.GUARD));
    }

    @Test
    void tierTwoOffersARealChoicePerBranch() {
        List<SkillNode> might = SkillTree.choicesForTier(2, SkillNode.Branch.MIGHT);
        assertTrue(might.contains(SkillTree.CLEAVE) && might.contains(SkillTree.DEADEYE));

        List<SkillNode> guard = SkillTree.choicesForTier(2, SkillNode.Branch.GUARD);
        assertTrue(guard.contains(SkillTree.PARRY) && guard.contains(SkillTree.SECOND_WIND));
    }

    @Test
    void capstoneIsReachableFromEitherBranch() {
        assertEquals(List.of(SkillTree.CHAMPIONS_RESOLVE), SkillTree.choicesForTier(3, SkillNode.Branch.MIGHT));
        assertEquals(List.of(SkillTree.CHAMPIONS_RESOLVE), SkillTree.choicesForTier(3, SkillNode.Branch.GUARD));
    }

    @Test
    void byIdRoundTripsAllNodes() {
        for (SkillNode node : List.of(SkillTree.MIGHT_ROOT, SkillTree.GUARD_ROOT, SkillTree.POWER_STRIKE,
                SkillTree.BULWARK, SkillTree.CLEAVE, SkillTree.DEADEYE, SkillTree.PARRY,
                SkillTree.SECOND_WIND, SkillTree.CHAMPIONS_RESOLVE)) {
            assertEquals(node, SkillTree.byId(node.id()));
        }
    }
}
