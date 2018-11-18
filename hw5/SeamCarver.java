import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Point2D;

import java.awt.*;

public class SeamCarver {
    Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }                      // current picture

    public int width() {
        return picture.width();
    }                        // width of current picture

    public int height() {
        return picture.height();
    }         // height of current picture

    private double calculateYGradient(int x, int y) {
        int newY = (y - 1) < 0 ? picture.height() - 1 : y - 1;
        Color up = picture.get(x, newY);

        newY = (y + 1) >= picture.height() ? 0 : (y + 1);
        Color down = picture.get(x, newY);

        double r = Math.abs(up.getRed() - down.getRed());
        double g = Math.abs(up.getGreen() - down.getGreen());
        double b = Math.abs(up.getBlue() - down.getBlue());

        return r * r + g * g + b * b;
    }

    private double calculateXGradient(int x, int y) {
        int newX = (x - 1) < 0 ? picture.width() - 1 : x - 1;
        Color left = picture.get(newX, y);

        newX = (x + 1) >= picture.width() ? 0 : (x + 1);
        Color right = picture.get(newX, y);

        double r = Math.abs(left.getRed() - right.getRed());
        double g = Math.abs(left.getGreen() - right.getGreen());
        double b = Math.abs(left.getBlue() - right.getBlue());

        return r * r + g * g + b * b;
    }

    public double energy(int x, int y) {
        Color centre = picture.get(x, y);
        // TODO: refactor.
        double energy = calculateXGradient(x, y) + calculateYGradient(x, y);
        return energy;
    }         // energy of pixel at column x and row y

    private double smallestOfTriples(double a, double b, double c) {
        if (a < b && a < c) {
            return a;
        }

        if (b < a && b < c) {
            return b;
        }

        if (c < a && c < b) {
            return c;
        }

        /* All equal now. */
        return a;
    }

    private void transposePicture() {
        Picture transposed = new Picture(height(), width());

        for (int y = 0; y < height(); y += 1) {
            for (int x = 0; x < width(); x += 1) {
                transposed.set(y, x, picture.get(x, y));
            }
        }

        picture = transposed;
    }

    public int[] findHorizontalSeam() {
        transposePicture(); /* Compute transpose. */
        int[] horizontalSeam = findVerticalSeam();
        transposePicture(); /* Transpose back to normal. */
        return horizontalSeam;
    }

    public int[] findVerticalSeam() {
        double M[][] = new double[height()][width()];
        int parentPointers[][] = new int[height()][width()];
        /* Base cases. */
        for (int i = 0; i < width(); i += 1) {
            M[0][i] = energy(i, 0);
        }

        for (int y = 1; y < height(); y += 1) {
            for (int x = 0; x < width(); x += 1) {
                double choice1 = (x - 1) < 0 ? Double.MAX_VALUE : M[y - 1][x - 1];
                double choice2 = M[y - 1][x];
                double choice3 = (x + 1) >= width() ? Double.MAX_VALUE : M[y - 1][x + 1];

                double minChoice = smallestOfTriples(choice1, choice2, choice3);
                if (minChoice == choice1) {
                    parentPointers[y][x] = x - 1;
                } else if (minChoice == choice2) {
                    parentPointers[y][x] = x;
                } else {
                    parentPointers[y][x] = x + 1;
                }
                M[y][x] = energy(x, y) + minChoice;
            }
        }

        int minX = 0;
        double minEnergy = Integer.MAX_VALUE;
        for (int x = 0; x < width(); x += 1) {
            if (M[height() - 1][x] < minEnergy) {
                minEnergy = M[height() - 1][x];
                minX = x;
            }
        }

        int[] result = new int[height()];
        int fillIndex = height() - 1;
        result[fillIndex] = minX;
        fillIndex -= 1;
        int y = height() - 1;
        while (fillIndex >= 0) {
            minX = parentPointers[y][minX];
            result[fillIndex] = minX;
            fillIndex -= 1;
            y -= 1;
        }

        return result;
    }      // sequence of indices for vertical seam

    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture(), seam);
    }  // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture(), seam);
    } // remove vertical seam from picture
}