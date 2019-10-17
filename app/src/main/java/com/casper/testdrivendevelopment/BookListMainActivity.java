package com.casper.testdrivendevelopment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.casper.testdrivendevelopment.data.BookSaver;
import com.casper.testdrivendevelopment.data.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_ADDNEW = CONTEXT_MENU_DELETE+1;
    public static final int CONTEXT_MENU_UPDATA = CONTEXT_MENU_ADDNEW+1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATA+1;
    public static final int REQUEST_CODE_NEW_BOOK = 901;
    private static final int REQUEST_CODE_UPDATA_BOOK = 902;
    private ListView listViewBooks;
    private List<Book> listBooks;
    private BookSaver bookSaver;
    BookAdapter bookAdapter;

    public List<Book> getListBooks(){
        return listBooks;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookSaver.save();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        bookSaver=new BookSaver(this);
        listBooks=bookSaver.load();
        if (listBooks.size()==0)
        init();
        listViewBooks= (ListView) this.findViewById(R.id.list_view_books);

        bookAdapter=new BookAdapter(this,R.layout.list_view_item_book,listBooks);
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v==listViewBooks) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(listBooks.get(info.position).getTitle());
            //设置内容，参数1为分组，参数2对应条目的id，参数3是指排列顺序
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_ADDNEW, 0, "添加");
            menu.add(0, CONTEXT_MENU_UPDATA, 0, "修改");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于。。。");

        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_NEW_BOOK:
                if (resultCode==RESULT_OK){
                    String title=data.getStringExtra("title");
                    double price=data.getDoubleExtra("price",0);
                    int insertPosition=data.getIntExtra("insert_position",0);

                    listBooks.add(insertPosition,new Book(title,price,R.drawable.book_no_name));
                    bookAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_UPDATA_BOOK:
                if (resultCode==RESULT_OK){
                    String title=data.getStringExtra("title");
                    double price=data.getDoubleExtra("price",0);
                    int insertPosition=data.getIntExtra("insert_position",0);
                    Book boookAtPositio=getListBooks().get(insertPosition);
                    boookAtPositio.setTitle(title);
                    boookAtPositio.setPrice(price);

                    bookAdapter.notifyDataSetChanged();
                }
                break;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case CONTEXT_MENU_DELETE:
                //AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final int removePosition=((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Dialog dialog =new AlertDialog.Builder(this)
                        .setTitle("删除图书吗？")
                        .setMessage("您确定要删除这条图书吗？")
                        .setPositiveButton("删除",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        listBooks.remove(removePosition);
                        bookAdapter.notifyDataSetChanged();
                        Toast.makeText(BookListMainActivity.this,"删除成功",Toast.LENGTH_LONG);
                    }
            })
                        .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                    }
                }).create();
                dialog.show();
                break;

            case CONTEXT_MENU_ADDNEW: {
                Intent intent = new Intent(this, NewBookActivity.class);
                intent.putExtra("title", "无名书籍");
                intent.putExtra("price", 1);
                intent.putExtra("insert_position",((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                startActivityForResult(intent, REQUEST_CODE_NEW_BOOK);
                break;
            }
            case CONTEXT_MENU_UPDATA: {
                int position=((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent2 = new Intent(this, NewBookActivity.class);
                intent2.putExtra("title", listBooks.get(position).getTitle());
                intent2.putExtra("price", listBooks.get(position).getPrice());
                intent2.putExtra("insert_position",position);

                startActivityForResult(intent2, REQUEST_CODE_UPDATA_BOOK);
                break;
            }
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(BookListMainActivity.this,"图书列表v1.0,coded by casper",Toast.LENGTH_LONG);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void init() {
        listBooks=new ArrayList<Book>();
        listBooks.add(new Book("软件项目管理案例教程（第4版）",1,R.drawable.book_2));
        listBooks.add(new Book("创新工程实践",2,R.drawable.book_no_name));
        listBooks.add(new Book("信息安全数学基础（第2版）",3,R.drawable.book_1));
    }


    class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle());
            return view;

             */

            LayoutInflater mInflater= LayoutInflater.from(this.getContext());
            View item = mInflater.inflate(this.resourceId,null);

            ImageView img = (ImageView)item.findViewById(R.id.image_view_book_cover);
            TextView name = (TextView)item.findViewById(R.id.text_view_book_title);
            TextView price = (TextView)item.findViewById(R.id.text_view_book_price);

            Book book_item= this.getItem(position);
            img.setImageResource(book_item.getPictureId());
            name.setText("商品："+book_item.getTitle());
            price.setText("价格："+book_item.getPrice()+"元");

            return item;
        }
    }
}
