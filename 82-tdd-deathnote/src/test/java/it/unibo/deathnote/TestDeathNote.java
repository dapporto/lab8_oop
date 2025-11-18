package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNoteImpl deathnote = new DeathNoteImpl();

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
        }, "Index < 0 or to high");
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.getRule(30);
            }
        }, "Index < 0 or to high");
    }

    /**
     * No rule is empty or null in the DeathNote rules.
     */
    @Test
    void testRules() {
        Iterator<String> it = DeathNoteImpl.RULES.iterator();
        String currentRule;
        while (it.hasNext()) {
            currentRule = it.next();
            if (currentRule == null || currentRule == new String("")) {
                throw new IllegalArgumentException("One rule is null or blank");
            }
        }
    }

    /**
     * The human whose name is written in the DeathNote will eventually die.
     */
    @Test
    void testWriteName() {
        assertEquals(false, deathnote.isNameWritten("Mario"));
        deathnote.writeName("Mario");
        assertEquals(true, deathnote.isNameWritten("Mario"));
        assertEquals(false, deathnote.isNameWritten("Antonio"));
        assertEquals(false, deathnote.isNameWritten(""));    
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
        deathnote.writeName("Angelo");
        assertEquals("heart attack", deathnote.getDeathCause("Angelo"));
        deathnote.writeName("Antonella");
        assertEquals(true, deathnote.writeDeathCause("karting accident"));
        assertEquals("karting accident", deathnote.getDeathCause("Antonella"));
        try {
         Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        deathnote.writeDeathCause("murder");
        assertEquals("karting accident", deathnote.getDeathCause("Antonella"));
    }

    @Test
    void testWriteDetails() {
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                deathnote.writeDetails("the person was in a supermarket");
            }
        });
        deathnote.writeName("Davide");
        assertEquals("", deathnote.getDeathDetails("Davide"));
        assertEquals(true, deathnote.writeDetails("ran for too long"));
        deathnote.writeName("Luca");
        try {
         Thread.sleep(6100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertEquals(false, deathnote.writeDetails("he was in a supermarket"));
    }
}

