package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import it.unibo.deathnote.api.DeathNote;

/** 
 * This class implements {@link DeathNote} intarface.
 */
public class DeathNoteImpl implements DeathNote {
    private static final long MAX_TIME_CAUSE = 40L;
    private static final long MAX_TIME_DETAILS = 6040L;

    private final Map<String, List<String>> deathnote = new HashMap<>();
    private long startTime;
    private String currantName;

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Index < 0 or to high");
        }
        return RULES.get(ruleNumber);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException("The name passed is null"); // NOPMD suppressed as it is a false positive
        } else {
            deathnote.put(name, new LinkedList<>());
            this.startTime = System.currentTimeMillis();
            this.currantName = name;
        }
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        final long endTime = System.currentTimeMillis();
        if (cause == null || deathnote == null || currantName == null) {
            throw new IllegalStateException("The cause or the deathnote are null");
        } else if ((endTime - this.startTime) < MAX_TIME_CAUSE) {
            this.startTime = endTime;
            deathnote.get(this.currantName).addFirst(cause);
            return true;
        }
        return false;
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public boolean writeDetails(final String details) {
        final long endTime = System.currentTimeMillis();
        if (details == null || deathnote == null || currantName == null) {
            throw new IllegalStateException("The details or the deathnote are null");
        } else if ((endTime - this.startTime) < MAX_TIME_DETAILS) {
            deathnote.get(this.currantName).addLast(details);
            return true;
        }
        return false;

    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public String getDeathCause(final String name) {
        for (final Map.Entry<String, List<String>> entry : deathnote.entrySet()) {
            if (entry.getKey().equals(name)) {
                final String cause;
                try {
                    cause = entry.getValue().getFirst();
                } catch (final NoSuchElementException e) {
                    return "heart attack";
                }
                return cause;
            }
        }
        throw new IllegalArgumentException(name + " is not written in this DeathNote");
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public String getDeathDetails(final String name) {
        for (final Map.Entry<String, List<String>> entry : deathnote.entrySet()) {
            if (entry.getKey().equals(name)) {
                final String details;
                try {
                    details = entry.getValue().getLast();
                } catch (final NoSuchElementException e) {
                    return "";
                }
                return details;
            }
        }
        throw new IllegalArgumentException(name + " is not written in this DeathNote");
    }

    /** 
     * {@inheritDoc}.
     */
    @Override
    public boolean isNameWritten(final String name) {
        for (final String s : deathnote.keySet()) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
