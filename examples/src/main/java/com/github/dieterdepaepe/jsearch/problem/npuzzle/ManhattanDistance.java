package com.github.dieterdepaepe.jsearch.problem.npuzzle;

import com.github.dieterdepaepe.jsearch.search.constructive.Heuristic;
import com.github.dieterdepaepe.jsearch.search.constructive.cost.IntegerCost;

/**
 * Manhattan distance heuristic for N-Puzzle. It estimates the remaining cost as the total distance that each field
 * in the puzzle still has to move, ignoring the limitation that fields may only move by switching with the empty field.
 * @author Dieter De Paepe
 */
public class ManhattanDistance implements Heuristic<PuzzleSearchNode, PuzzleEnvironment> {
    @Override
    public IntegerCost estimateRemainingCost(PuzzleSearchNode node, PuzzleEnvironment environment) {
        SlidingPuzzle puzzle = environment.getPuzzle();
        PuzzleFields targetState = environment.getTargetState();
        PuzzleFields currentState = node.getPuzzleFields();
        int estimate = 0;

        for (int row = 0; row < puzzle.getDimension(); row++)
            for (int column = 0; column < puzzle.getDimension(); column++) {
                int fieldValue = puzzle.getValue(currentState, row, column);
                if (fieldValue == puzzle.getEmptyFieldValue())
                    continue;
                PuzzleIndex targetIndex = puzzle.findValue(targetState, fieldValue);
                estimate += Math.abs(targetIndex.row - row) + Math.abs(targetIndex.column - column);
            }

        return IntegerCost.valueOf(estimate);
    }
}
