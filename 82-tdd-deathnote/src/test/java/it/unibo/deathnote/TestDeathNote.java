package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private static final int BIG_INDEX = 30;
    private static final int TEST_TIME_DETAILS = 6100;
    private static final String PERSON1 = "Mario";
    private static final String PERSON2 = "Angelo";
    private static final String CAUSE1 = "kart accident";
    private static final String CAUSE2 = "the person was in a supermarket";
    private final DeathNoteImpl deathnote = new DeathNoteImpl();

    /**
     * Rule number 0 and negative rules do not exist in the DeathNote rules.
     */
    @Test
    void testGetRule() {
        assertNotNull(deathnote);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.getRule(0);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.getRule(BIG_INDEX);
            }
        });
    }

    /**
     * No rule is empty or null in the DeathNote rules.
     */
    @Test
    void testRules() {
        final Iterator<String> it = DeathNoteImpl.RULES.iterator();
        while (it.hasNext()) {
            final String currentRule = it.next();
            if (currentRule == null || "".equals(currentRule)) {
                throw new IllegalArgumentException("One rule is null or blank");
            }
        }
    }

    /**
     * The human whose name is written in the DeathNote will eventually die.
     */
    @Test
    void testWriteName() {
        assertFalse(deathnote.isNameWritten(PERSON1));
        deathnote.writeName(PERSON1);
        assertTrue(deathnote.isNameWritten(PERSON1));
        assertFalse(deathnote.isNameWritten(PERSON2));
        assertFalse(deathnote.isNameWritten(""));
    }

    /**
     * If the cause of death is written within the next 40 milliseconds of writing the person's name, it will happen.
     * If the cause of death is not specified, the person will simply die of a heart attack.
     */
    @Test
    void testWriteDeathCause() {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.writeDeathCause("knife in the stomach");
            } 
        }, "The cause or the deathnote are null");
        deathnote.writeName(PERSON2);
        assertEquals("heart attack", deathnote.getDeathCause(PERSON2));
        deathnote.writeName(PERSON1);
        assertTrue(deathnote.writeDeathCause(CAUSE1));
        assertEquals(CAUSE1, deathnote.getDeathCause(PERSON1));
        try {
         Thread.sleep(100);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        deathnote.writeDeathCause("murder");
        assertEquals(CAUSE1, deathnote.getDeathCause(PERSON1));
    }

    @Test
    void testWriteDetails() {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.writeDetails(CAUSE2);
            }
        });
        deathnote.writeName(PERSON1);
        assertEquals("", deathnote.getDeathDetails(PERSON1));
        assertTrue(deathnote.writeDetails("ran for too long"));
        deathnote.writeName(PERSON2);
        try {
         Thread.sleep(TEST_TIME_DETAILS);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertFalse(deathnote.writeDetails(CAUSE2));
    }
}

