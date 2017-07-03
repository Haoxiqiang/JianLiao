package com.zoulf.jianliao.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.zoulf.common.app.ToolbarActivity;
import com.zoulf.jianliao.R;

public class SearchActivity extends ToolbarActivity {

  private static final String EXTRA_TYPE = "EXTRA_TYPE";
  public static final int TYPE_USER = 1; // 搜索人
  public static final int TYPE_GROUP = 1; // 搜索群

  // 具体需要显示的类型
  private int type;

  /**
   * 显示搜索界面
   *
   * @param context 上下文
   * @param type 显示的类型，用户还是群
   */
  public static void show(Context context, int type) {
    Intent intent = new Intent(context, SearchActivity.class);
    intent.putExtra(EXTRA_TYPE, type);
    context.startActivity(intent);
  }

  @Override
  protected boolean initArgs(Bundle bundle) {
    type = bundle.getInt(EXTRA_TYPE);
    // 是搜索人或者是搜索群中的一种
    return type == TYPE_USER || type == TYPE_GROUP;
  }

  @Override
  protected int getContentLayoutId() {
    return R.layout.activity_search;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // 初始化菜单
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search, menu);

    // 找到搜索菜单
    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) searchItem.getActionView();
    if (searchView != null) {
      // 拿到一个搜索管理器
      SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
      searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

      // 添加搜索监听
      searchView.setOnQueryTextListener(new OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
          // 当点击了提交按钮的时候
          search(query);
          return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
          // 当文字改变的时候，不会及时搜索，只会在null的情况下进行搜索
          if (TextUtils.isEmpty(newText)) {
            search("");
            return true;
          }
          return false;
        }
      });
    }

    return super.onCreateOptionsMenu(menu);
  }

  /**
   * 搜索的发起点
   *
   * @param query 搜索的文字
   */
  private void search(String query) {

  }
}
