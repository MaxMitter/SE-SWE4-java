package swe4.collections;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HashBagTest {

    @Test
    @DisplayName("Tests default constructor")
    void whenDefaultConstructorCalledHashbagInitializesWithDefaultParams() {
        var expected = new HashBag<Integer>(11, 0.75f);
        var result = new HashBag<Integer>();

        assertEquals(expected.getLoadFactor(), result.getLoadFactor());
        assertEquals(expected.size(), result.size());
    }

    @Test
    @DisplayName("Tests adding multiple Integer elements")
    void whenAddingMultipleValidIntegersValidElementsAreAdded() {
        var result = new HashBag<Integer>();
        result.add(100, 5);
        result.add(100, 5);

        assertEquals(10, result.count(100));
        assertEquals(0, result.count(10));
    }

    @Test
    @DisplayName("Adding more elements than Bag has capacity rehashes the bag")
    void whenAddingManyDifferentElementsBagIsRehashedWithMoreCapacity() {
        HashBag<String> bag = new HashBag<>();

        bag.add("A");
        bag.add("B");
        bag.add("C");
        bag.add("D");
        bag.add("E");
        bag.add("F");
        bag.add("G");
        bag.add("H");
        bag.add("I");

        float loadFactorBefore = bag.getLoadFactor();

        bag.add("J"); //rehashing
        bag.add("K");
        bag.add("L");
        bag.add("M");
        bag.add("N");

        assertNotEquals(loadFactorBefore, bag.getLoadFactor());
        assertEquals(14, bag.size());
    }

    @Test
    @DisplayName("Tests if adding single elements works")
    void whenAddingSingleValidIntegersValidElementsAreAdded() {
        var result = new HashBag<Integer>();
        result.add(100);
        result.add(100);
        result.add(10);
        result.add(1);
        result.add(10);

        assertEquals(2, result.count(100));
        assertEquals(2, result.count(10));
        assertEquals(1, result.count(1));
        assertEquals(0, result.count(0));
    }

    @Test
    @DisplayName("Add a few objects to a Bag and clear it again")
    void whenClearingBagIsEmptyAfter() {
        var result = new HashBag<Integer>();
        assertEquals(0, result.size());

        result.add(100);
        result.add(100);
        result.add(10);
        result.add(1);
        result.add(10);

        assertNotEquals(0, result.size());

        result.clear();
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test if a the HashBag.contains returns true if the Value is in the Bag")
    void whenBagContainsElementsContainsReturnsTrue() {
        var result = new HashBag<Integer>();
        result.add(1);
        result.add(2);
        result.add(3);

        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
    }

    @Test
    @DisplayName("Test if a the HashBag.contains returns false if the Value is not in the Bag")
    void whenBagDoesNotContainsElementsContainsReturnsFalse() {
        var result = new HashBag<Integer>();
        result.add(1);
        result.add(2);
        result.add(3);

        assertFalse(result.contains(4));
        assertFalse(result.contains(5));
        assertFalse(result.contains(6));
    }

    @Test
    @DisplayName("Tests if HashBag.count returns the right Value")
    void whenAddingElementsToBagHashBagCountReturnsAddedElement() {
        var result = new HashBag<Integer>();

        result.add(100, 10);
        result.add(100, 10);
        result.add(10, 1);
        result.add(1, 5);

        assertEquals(20, result.count(100));
        assertEquals(1, result.count(10));
        assertEquals(5, result.count(1));
        assertEquals(0, result.count(5));
    }

    @Test
    @DisplayName("Tests if the current Load Factor is calculated Correctly")
    void whenFillingBagToHalfLoadFactorReturnsZeroPointFive() {
        var result = new HashBag<Integer>(10, 1);

        assertEquals(0f, result.getLoadFactor());

        result.add(1);
        result.add(2);
        result.add(3);
        result.add(4);
        result.add(5);

        assertEquals(0.5f, result.getLoadFactor());
    }

    @Test
    @DisplayName("Checks if the Table of Objects is returned correctly")
    void whenAddingElementsTableReturnsSameElements() {
        Object[] expected = new Object[5];
        HashBag<Integer> bag = new HashBag<>();
        for (int i = 0; i < 5; i++) {
            expected[i] = i;
            bag.add(i);
        }

        var result = bag.elements();

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Tests if empty Bag returns true")
    void whenBagEmptyFunctionEmptyReturnsTrue() {
        var result = new HashBag<Integer>();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Tests if empty Bag returns false when not empty")
    void whenBagNotEmptyFunctionEmptyReturnsFalse() {
        var result = new HashBag<Integer>();

        result.add(10, 1);

        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Removing a element deletes it from the bag")
    void whenRemovingMoreFromBagThanInItElementIsRemovesCompletely() {
        HashBag<Integer> bag = new HashBag<>();

        bag.add(10, 5);
        bag.add(100);

        int ret = bag.remove(100);

        assertEquals(1, ret);
        assertEquals(0, bag.count(100));
        assertEquals(5, bag.count(10));
    }

    @Test
    @DisplayName("Removing X of a element keeps it in the bag")
    void whenRemovingLessFromBagThanInItElementStaysInBagWithRemovedCount() {
        HashBag<Integer> bag = new HashBag<>();

        bag.add(10, 5);
        bag.add(100);

        int ret = bag.remove(10, 2);

        assertEquals(5, ret);
        assertEquals(1, bag.count(100));
        assertEquals(3, bag.count(10));
    }

    @Test
    @DisplayName("Removing elements from an empty bag returns 0 and doesn't change the bag")
    void whenRemovingElementFromEmptyBagReturnZero() {
        HashBag<Integer> bag = new HashBag<>();

        int sizeBefore = bag.size();
        int ret = bag.remove(100);

        assertEquals(0, ret);
        assertEquals(sizeBefore, bag.size());
    }

    @Test
    @DisplayName("Rehashing does not change the elements")
    void whenRehashingElementsStayTheSame() {
        HashBag<Integer> bag = new HashBag<>();

        bag.add(1);
        bag.add(2);
        bag.add(3);
        bag.add(4);
        var expected = bag.elements();

        bag.rehash();
        var result = bag.elements();

        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Rehashing changes the Load Factor")
    void whenRehashingLoadFactorChanges() {
        HashBag<Integer> bag = new HashBag<>();

        bag.add(1);
        bag.add(2);
        bag.add(3);
        bag.add(4);
        var expected = bag.getLoadFactor();

        bag.rehash();
        var result = bag.getLoadFactor();

        assertNotEquals(expected, result);
    }

    @Test
    @DisplayName("toString returns a String of the correct length")
    void whenCallingToStringReturnCorrectLength() {
        HashBag<Integer> bag = new HashBag<>();
        bag.add(100);
        bag.add(10);
        bag.add(1);

        String expected = "HashBag: {List: {[100, 1][1, 1]} List: {[10, 1]} }";

        String result = bag.toString();
        assertEquals(expected.length(), result.length());
    }

    @Test
    @DisplayName("toString returns a String equal to the expected String")
    void whenCallingToStringReturnCorrectString() {
        HashBag<Integer> bag = new HashBag<>();
        bag.add(100);
        bag.add(10);
        bag.add(1);

        String expected = "HashBag: {List: {[100, 1][1, 1]} List: {[10, 1]} }";

        String result = bag.toString();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Iterator throws a InvalidIteratorException")
    void whenReachingTheEndHashBagIteratorThrowsException() {
        HashBag<Integer> bag = new HashBag<>();
        bag.add(1);
        var it = bag.iterator();
        it.next();

        assertThrows(InvalidIteratorException.class, it::next);
    }

    @Test
    @DisplayName("Iterator of empty Bag throws exception")
    void whenCallingIteratorOnEmptyBagThrowsException() {
        HashBag<Integer> bag = new HashBag<>();
        var iter = bag.iterator();

        assertThrows(InvalidIteratorException.class, iter::next);
    }
}