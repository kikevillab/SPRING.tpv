package wrappers;

public class EmbroideryWrapper extends ProductWrapper {

    private int stitches;

    private int colors;

    private int squareMillimeters;

    public EmbroideryWrapper() {
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
