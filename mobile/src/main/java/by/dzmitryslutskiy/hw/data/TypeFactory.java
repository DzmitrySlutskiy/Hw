package by.dzmitryslutskiy.hw.data;

/**
 * Classname
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TypeFactory {

    /*  private fields  */

    /*  public constructors */

    private TypeFactory() {/*   code    */}

    public static TypeA getNewItem(int iconId, String text1, String text2, int iconId2) {

        int id = (int) (Math.random() * 4);
        switch (id) {
            case 0:
                return new TypeA("TypeA" + " " + text2);
            case 1:
                return new TypeB(iconId, "TypeB", text2);
            case 2:
                return new TypeC(iconId, "TypeC", text2);
            default:
            case 3:
                return new TypeD(iconId, "TypeD", text2, iconId2);
        }
    }

}
