import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Verfspetters: find the smallest spot of connected x's within a 12x12 grid
 */
public class Verfspetters {

    public static Set<Integer> visitedPoints = new HashSet<>();
    public static List<Integer> drips = new ArrayList<>();

    public static void main(final String[] args) {

        List<List<Integer>> spots = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0])))
        {
            String sCurrentLine;

            int index = 0;

            while ((sCurrentLine = br.readLine()) != null) {
                //write the X's to the map with there 1-based index
                for(String s : sCurrentLine.split(",")) {
                    index++;
                    if("X".equalsIgnoreCase(s)) {
                        drips.add(index);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //all the drips are in the drips hashset now
        //we iterate over the set, this will be a random sequence but we don't care about the sequence
        for (Integer drip : drips) {
            if (!visitedPoints.contains(drip)) {
                //this drip is not "visited" yet determine the spot
                List<Integer> spot = new ArrayList<>();
                spots.add(getConnectedDrips(drip, spot));
            }
        }

        //show the spots found and get the best result
        int smallestSize = 145;
        List<Integer> smallestSpot = null;

        for(List<Integer> spot : spots) {
            if(spot.size() < smallestSize) {
                smallestSize = spot.size();
                smallestSpot = spot;
            }

            StringBuilder sb = new StringBuilder("Spot with size ");
            sb.append(spot.size());
            sb.append(" => ");
            boolean first = true;
            for(Integer d : spot) {
                if(!first) {
                    sb.append(", ");
                }
                else {
                    first = false;
                }
                sb.append(showCoords(d));
            }
            System.out.println(sb);

        }

        if(smallestSize == 145) {
            System.out.println("No spots found");
        }
        else {
            StringBuilder sb = new StringBuilder("Smallest, first visited, spot: ");
            sb.append(smallestSpot.size());
            sb.append(" => ");
            boolean first = true;
            for(Integer d : smallestSpot) {
                if(!first) {
                    sb.append(", ");
                }
                else {
                    first = false;
                }
                sb.append(showCoords(d));
            }
            System.out.println(sb);

        }

    }

    private static String showCoords(Integer d) {
        return "(" + (d -1) % 12 + ", " + (d -1) / 12 + ")";
    }

    private static List<Integer> getConnectedDrips(Integer drip, List<Integer> spot) {
        //add the drip to the spot and the visitedPoints
        visitedPoints.add(drip);
        spot.add(drip);

        //get the neighbours and do the same for the none-visited points
        for(Integer d : getNeighbours(drip)) {
            if(!visitedPoints.contains(d)) {
                getConnectedDrips(d, spot);
            }
        }

        return spot;
    }

    private static List<Integer> getNeighbours(Integer drip) {
        //get the neighbours of this drip
        List<Integer> result = new ArrayList<>();

        //nw
        if(notWestColumn(drip) && notTopRow(drip) && drips.contains(drip - 13)) {
            result.add(drip - 13);
        }
        //n
        if(notTopRow(drip) && drips.contains(drip - 12)) {
            result.add(drip - 12);
        }
        //ne
        if(notEastColumn(drip) && notTopRow(drip) && drips.contains(drip - 11)) {
            result.add(drip - 11);
        }
        //e
        if(notEastColumn(drip) && drips.contains(drip + 1)) {
            result.add(drip + 1);
        }
        //se
        if(notEastColumn(drip) && notBottomRow(drip) && drips.contains(drip + 13)) {
            result.add(drip + 13);
        }
        //s
        if(notBottomRow(drip) && drips.contains(drip + 12)) {
            result.add(drip + 12);
        }
        //sw
        if(notWestColumn(drip) && notBottomRow(drip) && drips.contains(drip + 11)) {
            result.add(drip+ 11);
        }
        //w
        if(notWestColumn(drip) && drips.contains(drip - 1)) {
            result.add(drip - 1);
        }

        return result;
    }

    private static boolean notTopRow(Integer drip) {
        //the indices [1,12] make up the top row
        return drip > 12;
    }

    private static boolean notEastColumn(Integer drip) {
        //the indices 12, 24, 36 etc make up the east column
        return drip % 12 != 0;
    }

    private static boolean notBottomRow(Integer drip) {
        //the indices [133,144] make up the bottom row
        return drip < 133;
    }

    private static boolean notWestColumn(Integer drip) {
        //the indices 1,13,25 etc make up the west column
        return drip % 12 != 1;
    }

}
