package day18;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private List<List<Boolean>> grid;

    public Grid(int size) {
        grid = new ArrayList<>();
        for (int y = 0; y <= size; y++) {
            List<Boolean> row = new ArrayList<>();
            for (int x = 0; x <= size; x++) {
                row.add(false);
            }
            grid.add(row);
        }
    }

    public boolean isInBoundaries(int x, int y) {
        return x >= 0 && x < grid.size() && y >= 0 && y < grid.size();
    }

    public void turnOn(int x, int y) {
        grid.get(y).set(x, true);
    }

    public void turnOff(int x, int y) {
        grid.get(y).set(x, false);
    }

    public boolean isOn(int x, int y) {
        return grid.get(y).get(x);
    }
}
