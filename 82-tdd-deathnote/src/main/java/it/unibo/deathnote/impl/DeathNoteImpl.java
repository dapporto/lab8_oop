package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote{
    private static final long MAX_TIME_CAUSE = 40L;
    private static final long MAX_TIME_DETAILS = 6040L;

    private final Map<String, List<String>> deathnote = new HashMap<>();
    private long startTime = 0L;
    private long endTime = 0L;
    private String currantName = null;

    public DeathNoteImpl(){

    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("Index < 0 or to high");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        if (name == null) {
            throw new NullPointerException("The name passed is null");
        } else {
            deathnote.put(name, new LinkedList<String>());
            this.startTime = System.currentTimeMillis();
            this.currantName = name;
        }
    }

    @Override
    public boolean writeDeathCause(String cause) {
        this.endTime = System.currentTimeMillis();
        if (cause == null || deathnote == null || currantName == null) {
            throw new IllegalStateException("The cause or the deathnote are null");
        } else if ((this.endTime - this.startTime) < MAX_TIME_CAUSE) {
            this.startTime = this.endTime;
            deathnote.get(this.currantName).addFirst(cause);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
        this.endTime = System.currentTimeMillis();
        if (details == null || deathnote == null || currantName == null) {
            throw new IllegalStateException("The details or the deathnote are null");
        } else if ((this.endTime - this.startTime) < MAX_TIME_DETAILS ) {
            deathnote.get(this.currantName).addLast(details);
            return true;
        }
        return false;

    }

    @Override
    public String getDeathCause(String name) {
        for (String k : deathnote.keySet()) {
            if (k.equals(name)) {
                String cause;
                try {
                    cause = deathnote.get(k).getFirst();
                } catch (NoSuchElementException e) {
                    return new String("heart attack");
                }
                return cause;
            }
        }
        throw new IllegalArgumentException(name + " is not written in this DeathNote");
    }

    @Override
    public String getDeathDetails(String name) {
        for (String k : deathnote.keySet()) {
            if (k.equals(name)) {
                String details;
                try {
                    details = deathnote.get(k).getFirst();
                } catch (NoSuchElementException e) {
                    return new String("");
                }
                return details;
            }
        }
        throw new IllegalArgumentException(name + " is not written in this DeathNote");
    }

    @Override
    public boolean isNameWritten(String name) {
        for (String s : deathnote.keySet()) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
