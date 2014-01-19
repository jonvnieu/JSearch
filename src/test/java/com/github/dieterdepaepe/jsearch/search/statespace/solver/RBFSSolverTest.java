package com.github.dieterdepaepe.jsearch.search.statespace.solver;

import com.github.dieterdepaepe.jsearch.problem.dummy.DummyGenerator;
import com.github.dieterdepaepe.jsearch.problem.dummy.DummyHeuristic;
import com.github.dieterdepaepe.jsearch.problem.dummy.DummySearchNode;
import com.github.dieterdepaepe.jsearch.search.statespace.InformedSearchNode;
import com.github.dieterdepaepe.jsearch.search.statespace.dev.LoggingGenerator;
import com.github.dieterdepaepe.jsearch.search.statespace.util.BasicManager;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Testing class for {@link com.github.dieterdepaepe.jsearch.search.statespace.solver.RBFSSolver}.
 * @author Dieter De Paepe
 */
public class RBFSSolverTest {
    @Test
    public void testNodeExpansionOrder() {
        // Search space for this test, nodes are ordered from cheap to expensive, goals nodes are written in capitals.
        //  ----a----
        //  |   |   b
        //  c   |   |
        // / \  d   |
        // e |  |   |
        //   |  F   |
        //   g      |
        //   |      H
        //   I

        DummySearchNode a = new DummySearchNode(0, 0, false);
        DummySearchNode b = new DummySearchNode(1, 0, false);
        DummySearchNode c = new DummySearchNode(0.5, 1.5, false);
        DummySearchNode d = new DummySearchNode(2, 1, false);
        DummySearchNode e = new DummySearchNode(0, 4, false);
        DummySearchNode f = new DummySearchNode(5, 0, true);
        DummySearchNode g = new DummySearchNode(4, 2, false);
        DummySearchNode h = new DummySearchNode(7, 0, true);
        DummySearchNode i = new DummySearchNode(8, 0, true);

        ListMultimap<DummySearchNode, DummySearchNode> successors = ArrayListMultimap.create();
        successors.put(a, c);
        successors.put(a, d);
        successors.put(a, b);
        successors.put(c, e);
        successors.put(c, g);
        successors.put(g, i);
        successors.put(d, f);
        successors.put(b, h);

        LoggingGenerator<DummySearchNode, Object> generator = new LoggingGenerator<>(new DummyGenerator(successors));
        DummyHeuristic heuristic = new DummyHeuristic();
        BasicManager<DummySearchNode> manager = new BasicManager<>();
        RBFSSolver solver = new RBFSSolver();

        solver.solve(new InformedSearchNode<>(a, 0), null, heuristic, generator, manager);

        assertEquals(generator.getExpandedNodes(), Arrays.asList(a, b, c, d, c, e, d));
        assertEquals(manager.getSolution().getNode(), f);
        assertTrue(manager.getSolution().isOptimal());
    }

    @Test
    public void testSolvesNPuzzle() {


        //assertEquals(manager.getSolution().getNode().getMovesPerformed(), 80);
    }
}