public class Interview {

    public static void main(String[] args) {
        double a = 0.0;
        double b = a;
        Double x = a;
        Double y = b;
        y = 2.0;

        System.out.println(a == b);
        System.out.println(x.equals(y));
        

    }
}
