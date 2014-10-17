package by.dzmitryslutskiy.hw.data;

/**
 * Classname
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TypeD extends TypeC {

    /*  private fields  */
    private int mIconId2;
    /*  public constructors */

    public TypeD(int mImageId, String text1, String text2, int mIconId2) {
        super(mImageId, text1, text2);
        this.mIconId2 = mIconId2;
    }

    public int getmIconId2() {
        return mIconId2;
    }

    public void setmIconId2(int mIconId2) {
        this.mIconId2 = mIconId2;
    }
}
