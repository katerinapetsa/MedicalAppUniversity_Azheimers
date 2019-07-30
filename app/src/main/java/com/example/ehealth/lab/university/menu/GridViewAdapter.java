package com.example.ehealth.lab.university.menu;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehealth.lab.university.personal_report.CalendarPR;
import com.example.ehealth.lab.university.disease.description.DiseaseMain;
import com.example.ehealth.lab.university.doctors.DoctorsMain;
import com.example.ehealth.lab.university.main.MainActivity;
import com.example.ehealth.lab.university.R;
import com.example.ehealth.lab.university.medications.AllMeds;
import com.example.ehealth.lab.university.profile.information.ProfileMain;
import com.example.ehealth.lab.university.reporting.ReportMain;


import static java.lang.String.*;

/**
 * The main page of application.
 *
 * @author Stelios Maimaris
 * @author Stavroula Kousparou
 *
 */
public class GridViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private final List<CategoryItem> categoryItems = new ArrayList<>();

    public GridViewAdapter(Context context) {

        mContext = context;
        categoryItems.add(new CategoryItem(mContext.getString(R.string.personalData), R.mipmap.personaldata));
        categoryItems.add(new CategoryItem(mContext.getString(R.string.information), R.mipmap.info));
        categoryItems.add(new CategoryItem(mContext.getString(R.string.diary), R.mipmap.calendar));
        categoryItems.add(new CategoryItem(mContext.getString(R.string.medications), R.mipmap.medications));
        categoryItems.add(new CategoryItem(mContext.getString(R.string.report), R.mipmap.report));
        categoryItems.add(new CategoryItem(mContext.getString(R.string.mydoctor), R.mipmap.doctor));
    }

    @Override
    public int getCount() {
        return categoryItems.size();
    }

    @Override
    public CategoryItem getItem(int position) {
        return categoryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categoryItems.get(position).categoryIcon;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final ImageView categoryIcon;
        final TextView categoryName;

        if (convertView == null) {

            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.category_gridview_items, null);
            convertView.setTag(R.id.categoryImage, convertView.findViewById(R.id.categoryImage));
            convertView.setTag(R.id.categoryName,
                    convertView.findViewById(R.id.categoryName));
        }

        categoryName = (TextView) convertView.getTag(R.id.categoryName);
        categoryIcon = (ImageView) convertView.getTag(R.id.categoryImage);

        CategoryItem item = getItem(position);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), item.categoryIcon);
        bitmap = ApplyVintageBitmapEffect.applyVintegge(bitmap, "profile");

        categoryIcon.setOnClickListener(this);
        categoryIcon.setImageBitmap(bitmap);
        categoryIcon.setTag(valueOf(position));

        categoryName.setOnClickListener(this);
        categoryName.setText(item.categoryName);
        categoryName.setTag(valueOf(position));

        return convertView;
    }

    @Override
    public void onClick(View view) {

        MainActivity.vibrate();

        int position = Integer.parseInt(view.getTag().toString());
        CategoryEnum categoryEnum = CategoryEnum.values()[position];

        switch (categoryEnum) {

            case PROFILE:
                callNextActivity(ProfileMain.class);
                break;

            case DISEASE:
                callNextActivity(DiseaseMain.class);
                break;

            case DIARY:
                callNextActivity(CalendarPR.class);
                break;

           case MEDICATIONS:
               callNextActivity(AllMeds.class);
             break;

            case REPORTING:
                callNextActivity(ReportMain.class);
                break;

            case DOCTORS:
                callNextActivity(DoctorsMain.class);
                break;



        }
    }

    private void callNextActivity(Class nextActivity) {

        Intent mainAct = new Intent(mContext, nextActivity);
        mContext.startActivity(mainAct);
    }

    private static class CategoryItem {

        public final String categoryName;
        final int categoryIcon;

        CategoryItem(String name, int icon) {

            this.categoryName = name;
            this.categoryIcon = icon;
        }
    }

    private enum CategoryEnum {
        PROFILE,
        DISEASE,
//      DIETARY,
//      PHYSICAL_EXERCISE,
        DIARY,
        MEDICATIONS,
        REPORTING,
        DOCTORS,
    }
}


