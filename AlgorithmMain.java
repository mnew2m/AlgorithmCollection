import java.util.*;
import java.time.*;
import java.time.format.*;

public class AlgorithmMain {
    public static void main(String[] args) {
        // getCurDay(""); // 현재 일자 (Ex "_" -> 2022_04_14)
        // getCurTime("", ""); // 현재일자, 시간(시, 분) (Ex "_", ":" -> 2022_04_14 11:50)
        // getCurTime2("", ""); // 현재일자, 시간(시, 분) (Ex "_", ":" -> 2022_04_14 11:50:38)
        // vhLineAlgorithm(); // 대각선을 기준점을 중심으로 사이즈, 각도만큼 돌린다.
        // sizeAlgorithm(); // 해당 길이의 선을 sizeInfoList에 있는 사이즈로 잘라서 리턴시킨다.

    }

    /** 현재일자 */
    public static String getCurDay(String separation) {
        if (separation == null || separation.equals(""))
            separation = "_";
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy" + separation + "MM" + separation + "dd");
        String formattedNow = now.format(formatter);
        return formattedNow;
    }

    /** 현재일자, 시간(시, 분) */
    public static String getCurTime(String dateSeparation, String timeSeparation) {
        if ((dateSeparation == null || dateSeparation.equals(""))
                && (timeSeparation == null || timeSeparation.equals("")))
            dateSeparation = "_";
        timeSeparation = ":";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy" + dateSeparation + "MM" + dateSeparation + "dd" + " " + "HH" + timeSeparation + "mm");
        String formattedNow = now.format(formatter);
        return formattedNow;
    }

    /** 현재일자, 시간(시, 분, 초) */
    public static String getCurTime2(String dateSeparation, String timeSeparation) {
        if ((dateSeparation == null || dateSeparation.equals(""))
                && (timeSeparation == null || timeSeparation.equals("")))
            dateSeparation = "_";
        timeSeparation = ":";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy" + dateSeparation + "MM" + dateSeparation + "dd" + " " + "HH" + timeSeparation + "mm"
                        + timeSeparation + "ss");
        String formattedNow = now.format(formatter);
        return formattedNow;
    }

    public static void vhLineAlgorithm() {
        // 대각선 포인터 정보
        Point p1 = new Point(110, 10, 0);
        Point p2 = new Point(100, 15, 0);

        // 대각선의 길이
        double dist = getDistanceBetweenTwoPoints(p1, p2);
        if (dist > 1000) {
            System.out.println("gg");
        }

        // 대각선의 각도
        double angle = getCalAngle(p1, p2);
        System.out.println("각도 = [" + angle + "]");

        Map<String, Point> map1 = null;
        Map<String, Point> map2 = null;

        // 대각선의 포인터를 하나씩 회전이동시킴
        if (p1.getY() < p2.getY()) {
            map1 = getCoorDinate(p1, angle, 20);
            map2 = getCoorDinate(p2, angle, 20);
        } else if (p1.getY() > p2.getY()) {
            map1 = getCoorDinate(p1, -angle, 20);
            map2 = getCoorDinate(p2, -angle, 20);
        } else {
            System.out.println("대각선이 아님!! y값이 똑같음");
        }

        if (map1 != null && map2 != null) {
            System.out.println("map1 = " +
                    map1.get("left").getX() + " / " + map1.get("left").getY() + " ////// " +
                    map1.get("right").getX() + " / " + map1.get("right").getY());
            System.out.println("map2 = " +
                    map2.get("left").getX() + " / " + map2.get("left").getY() + " ////// " +
                    map2.get("right").getX() + " / " + map2.get("right").getY());
        }
    }

    /** Point1과 Point2가 이루는 선의 길이를 구함 */
    public static double getDistanceBetweenTwoPoints(Point p1, Point p2) {
        double dist = 0.0;

        // 수직, 수평, 대각선
        if (p1.getX() == p2.getX() && p1.getY() != p2.getY())
            dist = Math.abs(p1.getY() - p2.getY());
        else if (p1.getX() != p2.getX() && p1.getY() == p2.getY())
            dist = Math.abs(p1.getX() - p2.getX());
        else if (p1.getX() != p2.getX() && p1.getY() != p2.getY())
            dist = (double) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));

        return dist;
    }

    /** Point1과 Point2가 이루는 대각선의 각도를 구함 */
    public static double getCalAngle(Point p1, Point p2) {
        double dy = p2.getY() - p1.getY();
        double dx = p2.getX() - p1.getX();

        double angle = Math.atan(dy / dx) * (180.0 / Math.PI);

        if (dx < 0.0) {
            angle += 180.0;
        } else {
            if (dy < 0.0)
                angle += 360.0;
        }

        return angle;
    }

    /**
     * p -> 포인터를 하나씩 회전이동 시키기 때문에 하나씩 넣어줘야함
     * angle -> p가 포함된 원래 대각선의 각도 (회전이동 시킬때 각도만큼 이동시켜야해서 필요함)
     * distance -> 대각선을 이동시킬 거리
     * 
     * 포인터를 중심으로 distance만큼 원을 그려서
     * 각도만큼 회전이동 시킨다. (반시계방향:양수, 시계방향:음수)
     */
    public static Map<String, Point> getCoorDinate(Point p, double angle, double distance) {

        // 포인터 p의 원래 좌표
        double org_x = p.getX();
        double org_y = p.getY();

        // 포인터를 중심으로 원을 그렸을때 y좌표를 수직이동시켜서 닿는 부분
        Point leftP = new Point(org_x, org_y + distance, 0); // ↖ ↙ 방향으로 이동시킬때
        Point rightP = new Point(org_x, org_y - distance, 0); // ↘ ↗ 방향으로 이동시킬때

        double dSetDegree = Math.toRadians(angle);
        double cos_q = Math.cos(dSetDegree);
        double sin_q = Math.sin(dSetDegree);

        double sxLeft = leftP.getX() - p.getX();
        double syLeft = leftP.getY() - p.getY();
        double sxRight = rightP.getX() - p.getX();
        double syRight = rightP.getY() - p.getY();

        int rxLeft = (int) Math.round((sxLeft * cos_q - syLeft * sin_q) + p.getX());
        int ryLeft = (int) Math.round((sxLeft * sin_q + syLeft * cos_q) + p.getY());
        int rxRight = (int) Math.round((sxRight * cos_q - syRight * sin_q) + p.getX());
        int ryRight = (int) Math.round((sxRight * sin_q + syRight * cos_q) + p.getY());

        Map<String, Point> map = new HashMap<String, Point>();
        map.put("left", new Point(rxLeft, ryLeft, 0));
        map.put("right", new Point(rxRight, ryRight, 0));

        return map;

    }

    public static void sizeAlgorithm() {
        SizeTest sizeTest = new SizeTest();
        List<Integer> sizeList = sizeTest.lineDivide(3841.0);
        System.out.println("총 갯수 : " + sizeList.size());

        int loop = 1;
        for (Integer size : sizeList) {
            System.out.println(loop + " -> [ " + size + " ]");
            loop++;
        }
    }
}

class SizeTest {
    static List<Double> sizeInfoList = Arrays.asList(1200.0, 1100.0, 1000.0, 900.0, 800.0, 700.0, 600.0, 500.0, 400.0,
            300.0);

    public List<Integer> lineDivide(double size) {

        int rec = (int) (size % 100);
        if (0 < rec && rec < 100) {
            rec = 100 - rec;
            size += rec;
        }

        List<Integer> sizeList = new ArrayList<Integer>();
        if (size <= 0)
            return sizeList;

        for (double d : sizeInfoList) {
            int re1 = (int) (size / d);
            int re2 = (int) (size % d);
            int loop = re2 == 0 || re2 >= 300 ? re1 : re1 - 1;

            for (int i = 0; i < loop; i++) {
                size -= d;
                sizeList.add((int) d);
            }

            if (re2 == 0)
                break;
        }

        if (0 < size && size < 300) {
            sizeList.add(300);
        }

        return sizeList;
    }
}