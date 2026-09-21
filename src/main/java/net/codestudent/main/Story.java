package net.codestudent.main;

public class Story {

    public static final StoryBeat INTRO = new StoryBeat("Prologue", new String[] {
        "The road out of the lowlands ends where the marsh begins - a country of black water, drowned trees, and villages that have learned to bar their doors before dark.",
        "You have walked a long way to reach it, carrying little but a blade, a name half-forgotten by the people you left behind, and the sense that something in these fens is waiting for you specifically.",
        "Millhaven's palisade rises out of the fog ahead, torchlight flickering behind its gate."
    });

    public static final StoryBeat REGION_INTRO = new StoryBeat("The Greywood Marches", new String[] {
        "Millhaven takes you in without much ceremony - a bowl of stew, a straw mattress, and Elder Maren's promise that you'll earn your keep soon enough.",
        "Word around the village is grim: wolves bolder than any should be, strangers gone missing on the north road, and something pale seen moving through the reeds at the Splintered Peaks after moonrise.",
        "Whatever waits out there, it isn't waiting quietly."
    });

    public static final StoryBeat CLIMAX_INTRO = new StoryBeat("The Splintered Peaks", new String[] {
        "The marsh thins into broken stone as the ground begins to climb. Old banners, rotted past any house's recognizing, hang from dead trees like warnings.",
        "At the top of the ridge, where the fog finally breaks, something is waiting in a hollow of shattered rock - armor black with rot, a blade that has not dulled in however long it has rusted here.",
        "The Drowned Knight turns to face you before you've made a sound."
    });

    public static final StoryBeat ENDING = new StoryBeat("End of Chapter One", new String[] {
        "The Drowned Knight falls, and for a long moment the only sound is wind through the broken stones.",
        "Whatever bound it to this hollow breaks with it - the fog over the Greywood Marches lifts a little further each day since, though Elder Maren says it will be a season yet before the wolves stop testing the palisade.",
        "You've earned a warm bed and a full purse in Millhaven for now. But the road north, past the Splintered Peaks, has only just come into view - and something tells you the Hollow Crown itself still lies unclaimed out there.",
        "To be continued..."
    });

    public static final StoryBeat DEATH = new StoryBeat("You Have Fallen", new String[] {
        "The marsh takes another traveler tonight. Millhaven will hear of it eventually, if anyone does."
    });

    private Story() {}

    public static void play(Console console, StoryBeat beat) {
        console.clear();
        console.separator(40);
        console.print(beat.title());
        console.separator(40);
        for (String paragraph : beat.paragraphs()) {
            console.print(paragraph);
            console.print("");
        }
        console.waitForContinue();
    }
}
