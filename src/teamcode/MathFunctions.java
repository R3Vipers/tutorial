package teamcode;

import org.opencv.core.Point;

import java.util.ArrayList;

import static java.lang.Math.*;

public class MathFunctions {

    public static double AngleWrap(double angle) {
        while (angle < -Math.PI) {
            angle += 2*Math.PI;
        }
        while (angle > Math.PI) {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    public static ArrayList <Point> lineCircleIntersection (Point circleCenter, double radius,
                                                            Point linePoint1, Point linePoint2) {
        if(Math.abs(linePoint1.y - linePoint2.y) < 0.003) {
            linePoint1.y = linePoint2.y + 0.003;
        }
        if(Math.abs(linePoint1.x - linePoint2.x) < 0.003) {
            linePoint1.x = linePoint2.x + 0.003;
        }

        double m1 = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);

        double quadraticA = 1.0 + pow(m1, 2);

        double x1 = linePoint1.x - circleCenter.x;
        double y1 = linePoint1.y - circleCenter.y;

        double quadraticB = (2.0 * m1 * y1) - (2.0 * pow(m1, 2) * x1);

        double quadraticC = ((pow(m1, 2) * pow(x1,2))) - (2.0*y1*m1*x1) + pow(y1, 2) - pow(radius, 2);

        ArrayList<Point> AllPoints = new ArrayList<>();

        try {
            double xRoot1 = (-quadraticB + sqrt(pow(quadraticB,2) - (4.0 * quadraticA * quadraticC))) / (2.0*quadraticA);

            double yRoot1 = m1 * (xRoot1 - x1) + y1;

            //put back the offset
            xRoot1 += circleCenter.x;
            yRoot1 += circleCenter.y;

            double minX = linePoint1.x < linePoint2.x ? linePoint1.x : linePoint2.x;
            double maxX = linePoint1.x > linePoint2.x ? linePoint1.x : linePoint2.x;
            double minY = linePoint1.y < linePoint2.y ? linePoint1.y : linePoint2.y;
            double maxY = linePoint1.y > linePoint2.y ? linePoint1.y : linePoint2.y;

            if ((xRoot1 > minX && xRoot1 < maxX) && (yRoot1 > minY && yRoot1 < maxY)) {
                AllPoints.add(new Point (xRoot1, yRoot1));
            }

            double xRoot2 = (-quadraticB - sqrt(pow(quadraticB,2) - (4.0 * quadraticA * quadraticC))) / (2.0*quadraticA);
            double yRoot2 = m1 * (xRoot2 - x1) + y1;

            xRoot2 += circleCenter.x;
            xRoot2 += circleCenter.y;

            if ((xRoot2 > minX && xRoot2 < maxX) && (yRoot2 > minY && yRoot2 < maxY)) {
                AllPoints.add(new Point (xRoot2, yRoot2));
            }
        } catch (Exception e) {
            AllPoints.add(new Point(linePoint2.x, linePoint2.y));
        }
        return AllPoints;
    }

    public static ArrayList <CurvePoint> extendFinalPoint (ArrayList <CurvePoint> allPoints) {
        CurvePoint secondToLastPoint = new CurvePoint(allPoints.get(allPoints.size()-2));
        CurvePoint lastPoint = new CurvePoint(allPoints.get(allPoints.size()-1));
        CurvePoint firstPoint = new CurvePoint(allPoints.get(0));

        double angle = atan2((lastPoint.y - secondToLastPoint.y), (lastPoint.x - secondToLastPoint.x));

        Point end = new Point((cos(angle) * firstPoint.followDistance), (cos(angle) * firstPoint.followDistance));

        CurvePoint endPoint = new CurvePoint(allPoints.get(allPoints.size()-1));

        endPoint.setPoint(end);

        allPoints.add(endPoint);

        return allPoints;
    }
}
