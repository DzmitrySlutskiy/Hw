package by.dzmitryslutskiy.hw.data;

/**
 * Classname
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TypeB extends TypeA {

    private int mImageId;
    private String mText2;

    public TypeB(String text2) {
        super("TypeB");
        this.mText2 = text2;
    }

    public TypeB(String text1, String text2) {
        super(text1);
        this.mText2 = text2;
    }

    public TypeB(int mImageId, String text1, String text2) {
        super(text1);
        this.mText2 = text2;
        this.mImageId = mImageId;
    }

    public int getmImageId() {
        return mImageId;
    }

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}
