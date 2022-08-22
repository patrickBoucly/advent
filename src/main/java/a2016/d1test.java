package a2016;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class d1test {

    enum Direction {
        NORTH(0,-1), EAST(1,0), SOUTH(0,1), WEST(-1,0);

        int dx, dy;
        Direction left, right;

        static {
            NORTH.left = WEST;
            NORTH.right = EAST;
            EAST.left = NORTH;
            EAST.right = SOUTH;
            SOUTH.left = EAST;
            SOUTH.right = WEST;
            WEST.left = SOUTH;
            WEST.right = NORTH;
        }
        Direction (int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }



    public static void main(String[] args) {

        Set<Point> visited = new HashSet<>();
        int x = 0;
        int y = 0;
        visited.add(new Point(x, y));
        Direction current = Direction.NORTH;
        List<String> input = Arrays.asList(getInput(false).trim().split(",")).stream().map(s -> s.trim())
				.collect(Collectors.toList());

        for (String each : input) {
            String temp = each.trim();
            Boolean right = temp.startsWith("R");
            int dist = Integer.parseInt(temp.substring(1));

            if (right) {
                current = current.right;
            } else {
                current = current.left;
            }

            for (int i = 0; i < dist; i++) {
                x += current.dx;
                y += current.dy;
                Point p = new Point(x, y);
                if (visited.contains(p)) {
                    System.out.println("Visited: " + manhattanDistance(0,0, x, y));
                } else {
                    visited.add(p);
                }
            }
        }

        System.out.println("Final destination distance: " + manhattanDistance(0, 0, x, y));
    }
    
    public static String getInput(boolean donnesComplete ) {
		return read(donnesComplete,1);
	}
	
	public static String read(boolean donnesComplete, int day) {
		String filePath = "src/main/resources/a2016/input" + day;
		String suffixe = (donnesComplete ? "" : "_s");
		String content = "";
		Path path = Paths.get(filePath + suffixe);
		try {
			content = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;

	}
}