package casual.canvas.bl;

import casual.canvas.entity.Line;
import casual.canvas.entity.Shape;
import casual.canvas.util.LoggerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.*;

/**
 * @author miaomuzhi
 * @since 2018/9/17
 */
class Recognizer {

    private static final int MAT_LENGTH = 300;

    List<Shape> recognizeShapes(List<Shape> shapes) {
        return null;
    }

    Class recognizeShape(Shape shape) {
        int[][] image = getImageFromShape(shape);

        return null;
    }

    /**
     * get image as a MAT_LENGTH x MAT_LENGTH bit matrix from a shape
     * @param shape shape instance
     * @return image as a MAT_LENGTH x MAT_LENGTH bit matrix
     */
    int[][] getImageFromShape(Shape shape){
        List<Line> lines = normalizeLines(shape);
        return linesToImage(lines);
    }

    /**
     * X' = (X - smallestX)/(largestX - smallestX), Y' = (Y- smallestY)/(largestY - smallestY)
     * @param shape shape containing lines, whose point's position range from 0 to height/width
     * @return lines normalized, whose points' positions range from 0 to 1
     */
    private List<Line> normalizeLines(Shape shape){
        List<Line> prevLines = shape.getLines();
        if (prevLines == null || prevLines.isEmpty()){//no need to normalize
            return new ArrayList<>();
        }

        List<Line> result = new ArrayList<>(prevLines.size());

        double smallestX = Integer.MAX_VALUE;
        double smallestY = Integer.MAX_VALUE;
        double largestX = 0;
        double largestY = 0;
        for (Line prevLine : prevLines) {
            smallestX = min(smallestX, min(prevLine.getStartX(), prevLine.getEndX()));
            largestX = max(largestX, max(prevLine.getStartX(), prevLine.getEndX()));
            smallestY = min(smallestY, min(prevLine.getStartY(), prevLine.getEndY()));
            largestY = max(largestY, max(prevLine.getStartY(), prevLine.getEndY()));
        }

        double xDiff = largestX - smallestX;
        double yDiff = largestY - smallestY;
        for (Line prevLine : prevLines) {
            Line line = new Line((prevLine.getStartX()-smallestX)/xDiff, (prevLine.getStartY()-smallestY)/yDiff,
                    (prevLine.getEndX()-smallestX)/xDiff, (prevLine.getEndY()-smallestY)/yDiff);
            result.add(line);
        }
        return result;
    }

    /**
     * convert the lines to matrix of image
     * @param lines normalized lines
     * @return image with length MAT_LENGTH x MAT_LENGTH
     */
    private int[][] linesToImage(List<Line> lines){
        int[][] image = new int[MAT_LENGTH][MAT_LENGTH];
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                image[i][j] = 0;
            }
        }

        for (Line line : lines) {
            int startX = (int)((MAT_LENGTH-1) * line.getStartX());
            int endX = (int)((MAT_LENGTH-1) * line.getEndX());
            int startY = (int)((MAT_LENGTH-1) * line.getStartY());
            int endY = (int)((MAT_LENGTH-1) * line.getEndY());

            List<Point> points = getPointsInPath(startX, endX, startY, endY);
            for (Point point : points) {
                image[point.x][point.y] = 1;
            }
        }
        return image;
    }

    /**
     * get points in the segment
     * @param startX x of start point
     * @param endX x of end point
     * @param startY y of start point
     * @param endY y of end point
     * @return points in the segment, including begin & end points
     */
    private List<Point> getPointsInPath(int startX, int endX, int startY, int endY){
        if (startX < 0 || endX < 0 || startY < 0 || endY < 0
                || startX >= MAT_LENGTH || endX >= MAT_LENGTH || startY >= MAT_LENGTH || endY >= MAT_LENGTH){//preconditions
            LoggerUtil.getLogger().warning(new Exception("points out of range"));
            return new ArrayList<>();
        }

        //special cases
        if (startX == endX && startY == endY){//if there is only one point in the line
            return new ArrayList<>(Collections.singleton(new Point(startX,  startY)));
        }
        if (abs(startX - endX) <= 1 || abs(startY - endY) <= 1){//only two points
            return new ArrayList<>(Arrays.asList(new Point(startX, startY), new Point(endX, endY)));
        }
        if (startX == endX){//vertical line
            List<Point> points = new ArrayList<>(abs(startY-endY) + 1);
            for (int i = min(startY, endY); i <= max(startY, endY); i++) {
                points.add(new Point(startX, i));
            }
            if (startY > endY){//although the order is useless now, but it's still sorted for potential changes
                Collections.reverse(points);
            }
            return points;
        }


        List<Point> points = new ArrayList<>(Collections.singleton(new Point(startX, startY)));
        double slope = (double)(endY - startY) / (endX - startX);
        int direct = (startX < endX ? 1 : -1);//direct means increase or decrease in x axis from start to end point

        int lastY = startY;
        for (int i = 1; i < abs(endX - startX); i++) {//end point is not included
            int x = startX + i * direct;
            int y = (int)(slope * direct + startY);
            addPointsWithX(points, x, y, x - direct, lastY);
            lastY = y;
        }
        points.add(new Point(endX, endY));
        return points;
    }

    /**
     * get points of segment under restriction that x = lastX + 1 or x = lastX - 1
     * @param points list of points
     * @param x x calculated
     * @param y y calculated
     * @param lastX last point's x
     * @param lastY last point's y
     */
    private void addPointsWithX(List<Point> points, int x, int y, int lastX, int lastY){
        if (abs(lastY - y) <= 1){//when abs(slope) is smaller than 1
            points.add(new Point(x, y));
            return;
        }

        int direct = (lastY < y ? 1 : -1);
        for (int i = 1; i <= abs(lastY - y); i++) {
            if (i < abs(lastY - y) / 2){
                points.add(new Point(lastX, lastY + i*direct));
            } else {
                points.add(new Point(x, lastY + i*direct));
            }
        }
    }

    /**
     * struct to store point
     */
    private class Point{
        int x;
        int y;

        Point(int x, int y){
            if (x < 0 || x >= MAT_LENGTH || y < 0 || y >= MAT_LENGTH){
                LoggerUtil.getLogger().warning(new Exception("Point out of range"));
                this.x = 0;
                this.y = 0;
            }

            this.x = x;
            this.y = y;
        }
    }
}
