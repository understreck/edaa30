package main.java.edaa30.bst;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BSTTest {
    class TestInt {
        int i;
    }

    BinarySearchTree<Integer> integerBST;
    BinarySearchTree<TestInt> testIntBST;

    @BeforeEach
    void setUp() {
        integerBST = new BinarySearchTree<>();
    }

    @AfterEach
    void tearDown() {
        integerBST = null;
        testIntBST = null;
    }

    @Test
    void test_add() {
        integerBST.add(2);
        integerBST.add(1);
        integerBST.add(3);
        integerBST.add(4);

        assertEquals(integerBST.size(), 4, "Elements where not added properly");
        assertEquals(integerBST.height(), 3, "Height was not properly calculated");
        assertEquals(integerBST.toString(), "[1, 2, 3, 4]", "");

        assertEquals(integerBST.add(4), false, "Trying to add the same value twice should return false");
        assertEquals(integerBST.size(), 4, "Trying to add the same value twice should keep the size twice");
    }

    @Test
    void test_size() {
        assertEquals(integerBST.size(), 0, "Size was not 0 when newly constructed");

        final var n = 100;
        for (int i = 0; i < n; ++i) {
            integerBST.add(i);
        }

        assertEquals(integerBST.size(), n, "Size was not kept track of when adding");

        integerBST.add(50);
        integerBST.add(51);

        assertEquals(integerBST.size(), n, "Adding the same value twice should not be possible");
    }


    @Test
    void test_clear() {
        integerBST.clear();
        assertEquals(integerBST.size(), 0, "Clearing an empty BST did not result in an empty BST");
        assertEquals(integerBST.m_root, null, "Clearing an empty BST did not result in a null root node");

        final var n = 100;
        for (int i = 0; i < n; ++i) {
            integerBST.add(i);
        }

        integerBST.clear();
        assertEquals(integerBST.size(), 0, "Clearing a BST did not result in an empty BST");
        assertEquals(integerBST.m_root, null, "Clearing a BST did not result in a null root node");
    }

    @Test
    void test_height() {
        final var n = 9;
        for (int i = 0; i < n; ++i) {
            integerBST.add(i);
        }

        assertEquals(integerBST.height(), n, "Height was not calculated correctly");
    }

    @Test
    void test_toString() {
        var set = new TreeSet<Integer>();

        final var n = 10;
        for (int i = 0; i < n; ++i) {
            integerBST.add(i * (i % 3 == 1 ? 1 : -1));
            set.add(i * (i % 3 == 1 ? 1 : -1));
        }

        assertEquals(integerBST.toString(), set.toString(), "toString() did not follow expected format");
    }
}
