//literally just stores a single variable, what the last heading was during teleop
@Disabled
public class HeadingHolder extends LinearOpMode {
    //set a static heading variable
    private static double lastHeading = 0;
    //set method
    public static void setLastHeading(int heading) {
        this.lastHeading = heading;
    }
    public static int getLastHeading() {
        return this.lastHeading;
    }
}