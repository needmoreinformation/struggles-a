import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
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
        return calculateXGradient(x, y) + calculateYGradient(x, y);
    }

    private int smallestOfTriples(double a, double b, double c) {
        if (a <= b && a <= c) {
            return 0;
        }

        if (b <= a && b <= c) {
            return 1;
        }

        if (c <= a && c <= b) {
            return 2;
        }

        /* All equal. */
        return 0;
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

                int minChoice = smallestOfTriples(choice1, choice2, choice3);
                double theMinChoice = 0;
                if (minChoice == 0) {
                    theMinChoice = choice1;
                    parentPointers[y][x] = x - 1;
                } else if (minChoice == 1) {
                    theMinChoice = choice2;
                    parentPointers[y][x] = x;
                } else {
                    theMinChoice = choice3;
                    parentPointers[y][x] = x + 1;
                }
                M[y][x] = energy(x, y) + theMinChoice;
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