package teamcode;

import org.opencv.core.Point;

import java.util.ArrayList;
import static java.lang.Math.*;
import static teamcode.RobotMovement.followCurve;

public class MyOpMode extends OpMode{
    public ArrayList<CurvePoint> allPoints = new ArrayList<>();
    @Override
    public void init() {

        allPoints.add(new CurvePoint(0,0,0.25,0.5, 10, Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(100,100,1.0,0.5, 60, Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(220,60,1.0,0.5, 60, Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(270,120,1.0,0.5, 60, Math.toRadians(50), 1.0));
        allPoints.add(new CurvePoint(320,180,0.5,0.5, 60, Math.toRadians(50), 1.0));

        //allPoints = extendFinalPoint (allPoints);
        CurvePoint lastPoint = new CurvePoint(allPoints.get(allPoints.size()-1));
        CurvePoint firstPoint = new CurvePoint(allPoints.get(0));

        double angle = atan2((180 - 120), (320 - 270));

        Point end = new Point((cos(angle) * firstPoint.followDistance), (sin(angle) * firstPoint.followDistance));


        CurvePoint endPoint = new CurvePoint(end.x, end.y, lastPoint.moveSpeed, lastPoint.turnSpeed, lastPoint.followDistance, Math.toRadians(50), 1.0);

        allPoints.add(endPoint);
    }

    @Override
    public void loop() {


        followCurve(allPoints, Math.toRadians(90));

    }
}
