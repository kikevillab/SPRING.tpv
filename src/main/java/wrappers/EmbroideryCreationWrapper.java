package wrappers;

public class EmbroideryCreationWrapper extends ProductNoIdWrapper {
    private int stitches;

    private int colors;

    private int squareMillimeters;

    public EmbroideryCreationWrapper() {
        super();
    }

    public int getStitches() {
        return stitches;
    }

    public void setStitches(int stitches) {
        this.stitches = stitches;
    }

    public int getColors() {
        return colors;
    }

    public void setColors(int colors) {
        this.colors = colors;
    }

    public int getSquareMillimeters() {
        return squareMillimeters;
    }

    public void setSquareMillimeters(int squareMillimeters) {
        this.squareMillimeters = squareMillimeters;
    }

    @Override
    public String toString() {
        return "EmbroideryWrapper [stitches=" + stitches + ", colors=" + colors + ", squareMillimeters=" + squareMillimeters + "]";
    }
}
