package com.burakovv.cgof;

import org.junit.Test;

import static com.burakovv.cgof.GameOfLife.*;
import static org.junit.Assert.*;

public class GameOfLifeTest {
    @Test
    public void testIsAliveOnNextStep() {
        assertFalse(isAliveOnNextStep(false, 0));
        assertFalse(isAliveOnNextStep(false, 1));
        assertFalse(isAliveOnNextStep(false, 2));
        assertTrue(isAliveOnNextStep(false, 3));
        assertFalse(isAliveOnNextStep(false, 4));
        assertFalse(isAliveOnNextStep(false, 5));
        assertFalse(isAliveOnNextStep(false, 6));
        assertFalse(isAliveOnNextStep(false, 7));
        assertFalse(isAliveOnNextStep(false, 8));
        assertFalse(isAliveOnNextStep(true, 0));
        assertFalse(isAliveOnNextStep(true, 1));
        assertTrue(isAliveOnNextStep(true, 2));
        assertTrue(isAliveOnNextStep(true, 3));
        assertFalse(isAliveOnNextStep(true, 4));
        assertFalse(isAliveOnNextStep(true, 5));
        assertFalse(isAliveOnNextStep(true, 6));
        assertFalse(isAliveOnNextStep(true, 7));
        assertFalse(isAliveOnNextStep(true, 8));
    }
}
