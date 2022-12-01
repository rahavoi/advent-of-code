package com.cs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * State for a logical game:
 * 3 missionaries and 3 cannibals are on the west bank of a river
 * They have a conoe that holds 2 people, and they must all cross
 * to the east bank of the river.
 *
 * There can never be more cannibals than missionaries on either side of
 * the river, or cannibals will eat them.
 *
 * Canoe must have at least one person to cross the river.
 *
 * Which sequence of crossings will successfully take the entire
 * party across the river?
 */
public class MCState {
    private static final int MAX_NUM = 3;
    private final int wm; //west bank missionaries
    private final int wc; //west bank cannibals
    private final int em; //east bank missionaries
    private final int ec; //east bank cannibals

    private final boolean boatOnWestBank;

    public MCState(int missionaries, int cannibals, boolean boatOnWestBank){
        this.wm = missionaries;
        this.wc = cannibals;
        this.em = MAX_NUM - wm;
        this.ec = MAX_NUM - wc;
        this.boatOnWestBank = boatOnWestBank;
    }

    @Override
    public String toString(){
        return String.format("On the west bank are %d missionaries and %d cannibals. \n" +
                "On the east bank are %d missionaries and %d cannibals. \n" +
                "The boat is on the %s bank.",
                wm, wc, em, ec, boatOnWestBank ? "west" : "east");
    }

    public boolean goalTest(){
        return isLegal() && em == MAX_NUM && ec == MAX_NUM;
    }

    public boolean isLegal(){
        if(wm < wc && wm > 0){
            return false;
        }

        if(em < ec && em > 0){
            return false;
        }

        return true;
    }

    public static List<MCState> successors(MCState mcs){
        List<MCState> result = new ArrayList<>();

        if(mcs.boatOnWestBank){
            if(mcs.wm > 1){
                result.add(new MCState(mcs.wm - 2, mcs.wc, !mcs.boatOnWestBank));
            }

            if(mcs.wm > 0){
                result.add(new MCState(mcs.wm - 1, mcs.wc, !mcs.boatOnWestBank));
            }

            if(mcs.wc > 1){
                result.add(new MCState(mcs.wm, mcs.wc - 2, !mcs.boatOnWestBank));
            }

            if(mcs.wc > 0){
                result.add(new MCState(mcs.wm, mcs.wc - 1, !mcs.boatOnWestBank));
            }

            if(mcs.wm > 0 && mcs.wc > 0){
                result.add(new MCState(mcs.wm - 1, mcs.wc - 1, !mcs.boatOnWestBank));
            }

        } else {
            if(mcs.em > 1){
                result.add(new MCState(mcs.wm + 2, mcs.wc, !mcs.boatOnWestBank));
            }

            if(mcs.em > 0){
                result.add(new MCState(mcs.wm + 1, mcs.wc, !mcs.boatOnWestBank));
            }

            if(mcs.ec > 1){
                result.add(new MCState(mcs.wm, mcs.wc + 2, !mcs.boatOnWestBank));
            }

            if(mcs.ec > 0){
                result.add(new MCState(mcs.wm, mcs.wc + 1, !mcs.boatOnWestBank));
            }

            if(mcs.em > 0 && mcs.ec > 0){
                result.add(new MCState(mcs.wm + 1, mcs.wc + 1, !mcs.boatOnWestBank));
            }
        }

        result.removeIf(Predicate.not(MCState::isLegal));

        return result;
    }

    public static void displaySolution(List<MCState> path){
        if(path.size() == 0){
            return;
        }

        MCState oldState = path.get(0);
        System.out.println(oldState);

        for(MCState currrentState : path.subList(1, path.size())){
            if(currrentState.boatOnWestBank){
                System.out.printf("%d missionaries and %d cannibals moved from the east bank to the west bank \n",
                        oldState.em - currrentState.em,
                        oldState.ec - currrentState.ec);
            } else {
                System.out.printf("%d missionaries and %d cannibals moved from the west bank to the east bank \n",
                        oldState.wm - currrentState.wm,
                        oldState.wc - currrentState.wc);
            }
            System.out.println(currrentState);
            oldState = currrentState;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCState mcState = (MCState) o;
        return wm == mcState.wm && wc == mcState.wc && em == mcState.em && ec == mcState.ec && boatOnWestBank == mcState.boatOnWestBank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(wm, wc, em, ec, boatOnWestBank);
    }

    public static void main(String[] args){
        MCState start = new MCState(MAX_NUM, MAX_NUM, true);
        Node<MCState> solution = GenericSearch.bfs(start, MCState::goalTest, MCState::successors);

        if(solution == null){
            System.out.println("No solution found :(");
        } else {
            List<MCState> path = GenericSearch.nodeToPath(solution);
            displaySolution(path);
        }
    }
}
