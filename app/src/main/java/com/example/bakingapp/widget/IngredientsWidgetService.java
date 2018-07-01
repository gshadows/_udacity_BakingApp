package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.utils.Options;
import com.example.bakingapp.utils.Utils;

import java.util.ArrayList;


public class IngredientsWidgetService extends RemoteViewsService {
  
  public static final String EXTRA_INGREDIENTS = "ingredients";
  
  
  @Override public RemoteViewsFactory onGetViewFactory (Intent intent) {
    return new IngredientsListRVFactory(getApplicationContext(), intent);
  }
  
  
  /**
   * This factory supplies Ingredients Widget's ListView with RemoteViews.
   */
  private static class IngredientsListRVFactory implements RemoteViewsService.RemoteViewsFactory {
    
    public static final String TAG = Options.XTAG + IngredientsListRVFactory.class.getSimpleName();
    
    
    private Context mContext;
    private Recipe  mRecipe;
    
    
    public IngredientsListRVFactory (@NonNull Context context, Intent intent) {
      mContext = context;
      mRecipe = intent.getParcelableExtra(EXTRA_INGREDIENTS);
    }
    
    
    @Override
    public RemoteViews getViewAt (int position) {
      // Create RemoteViews for the grid cell layout.
      RemoteViews views = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
      
      // Get ingredients ling.
      if ((position >= 0) && (position < mRecipe.getIngredients().size())) {
        views.setTextViewText(android.R.id.text1, Utils.getIngredientLine(mContext, mRecipe.getIngredients().get(position)));
      }
      
      return views;
    }
  
  
    @Override
    public int getCount() {
      if (mRecipe == null) return 0;
      ArrayList<Ingredient> ingredients = mRecipe.getIngredients();
      return (ingredients == null) ? 0 : ingredients.size();
    }
  
  
    @Override public void         onDataSetChanged() {}
    @Override public void         onCreate() {}
    @Override public void         onDestroy() {}
    @Override public RemoteViews  getLoadingView()          { return null; }
    @Override public int          getViewTypeCount()        { return 1; }
    @Override public long         getItemId (int position)  { return 0; }
    @Override public boolean      hasStableIds()            { return false; }
    
  }
}