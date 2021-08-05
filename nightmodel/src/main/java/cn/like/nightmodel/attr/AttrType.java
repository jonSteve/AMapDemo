package cn.like.nightmodel.attr;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

/**
 * Created by like on 16/7/20.
 */
public abstract class AttrType {
    protected static final String DEFTYPE_DRAWABLE = "drawable";
    protected static final String DEFTYPE_COLOR = "color";
    protected static final String DEFTYPE_STYLE = "style";
    protected static final String DEFTYPE_MIPMAP = "mipmap";

    String attrType;

    public AttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrType() {
        return attrType;
    }

    public abstract void apply(View view, String resName);

    public abstract String getResourceName(String attrValue, Resources resources);

    protected String getIntResourceName(String attrValue, Resources resources) {
        int id = Integer.parseInt(attrValue.substring(1));
        if (id == 0) return null;
        return resources.getResourceEntryName(id);
    }

    protected Drawable getDrawable(Context context, String resName) {
        Drawable drawable = null;
        Resources resources = context.getResources();
        int id = resources.getIdentifier(resName, DEFTYPE_DRAWABLE, context.getPackageName());
        if (id == 0) {
            id = resources.getIdentifier(resName, DEFTYPE_COLOR, context.getPackageName());
        }
        if (id == 0) {
            id = resources.getIdentifier(resName, DEFTYPE_MIPMAP, context.getPackageName());
        }
        if (id == 0) {
            id = resources.getIdentifier(resName, DEFTYPE_STYLE, context.getPackageName());
        }
        try {
            drawable = context.getDrawable(id);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        Log.d("获取资源", resName + " = " + id + "..." + drawable);
        return drawable;
    }
}
