package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissingNumbersTest {

    @Test
    public void testMissingNumbers_whenMissingAppearInTwoList() {
        List<Integer> a = List.of(203,204,205,206,207,208,203,204,205,206);
        List<Integer> b = List.of(205,203,204,204,206,207,205,208,203,206,205,206,204);
        MissingNumbers sut = new MissingNumbers();
        assertEquals(List.of(204,205,206), sut.diff(a, b));
    }

    @Test
    public void testMissingNumbers_whenMissingAppearInOneList() {
        List<Integer> a = List.of(203, 204, 205);
        int missing = 207;
        List<Integer> b = List.of(203, 204, 205, missing);
        MissingNumbers sut = new MissingNumbers();
        assertEquals(List.of(207), sut.diff(a, b));
    }
}
