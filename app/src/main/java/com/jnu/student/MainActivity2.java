package com.jnu.student;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.data.Book;
import com.jnu.student.data.BookItemDetailsActivity;
import com.jnu.student.data.DataBank;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ArrayList<Book> books=new ArrayList<>();
    private BookItemAdapter bookItemAdapter;
    private Context MainActivity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RecyclerView mainrecyclerView=findViewById(R.id.recycle_view_books);
        mainrecyclerView.setLayoutManager(new LinearLayoutManager(this));   //设置布局管理器

        //ArrayList<Book> books= new ArrayList<>();

        books= new DataBank().loadShopItems(this.getApplicationContext());
        if(0==books.size()) {
            books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        }

        bookItemAdapter= new BookItemAdapter(books);
        mainrecyclerView.setAdapter(bookItemAdapter);


        registerForContextMenu(mainrecyclerView);         //注册ContextMenu事件菜单


        updataItemLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int position=data.getIntExtra("position",-1);
                            String name=data.getStringExtra("name");

                            Book book=books.get(position);
                            book.setTitle(name);

                           //刷新
                           bookItemAdapter.notifyItemChanged(position);
                           new DataBank().SavebookItems(MainActivity2.this, books);
                        }
                    }
                });

        addItemLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name=data.getStringExtra("name");
                            books.add(new Book(name,R.drawable.book_no_name));
                            //刷新
                            bookItemAdapter.notifyItemInserted(books.size());
                            new DataBank().SavebookItems(this.getApplicationContext(), books);
                        }
                    }
                });

    }

    ActivityResultLauncher<Intent> addItemLauncher;
    ActivityResultLauncher<Intent> updataItemLauncher;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:

                Intent intent = new Intent(MainActivity2.this, BookItemDetailsActivity.class);
                addItemLauncher.launch(intent);

                break;
            case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确认删除");
                builder.setMessage("确认删除数据吗？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击确认按钮，执行删除数据的操作
                        books.remove(item.getOrder());
                        bookItemAdapter.notifyItemRemoved(item.getOrder());
                        new DataBank().SavebookItems(MainActivity2.this, books);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击取消按钮，不执行删除数据的操作
                        // 可以执行其他适当的操作或返回先前的界面
                
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            // 可以继续添加其他的菜单项处理逻辑

            case 2:
                Intent intentUpdata = new Intent(MainActivity2.this, BookItemDetailsActivity.class);
                Book book=books.get(item.getOrder());
                intentUpdata.putExtra("name",book.getTitle());
                intentUpdata.putExtra("position",item.getOrder());

                updataItemLauncher.launch(intentUpdata);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

public static class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.ViewHolder> {

        private final ArrayList<Book> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final TextView textViewName;            //书名
            private final ImageView imageViewItem;          //图片

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                textViewName = view.findViewById(R.id.book_title);
                imageViewItem= view.findViewById(R.id.image_view_book_cover);
                view.setOnCreateContextMenuListener(this);
            }


            @Override
            //创建一个显示的菜单
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, this.getAdapterPosition(), "添加"+this.getAdapterPosition());
                menu.add(0, 1, this.getAdapterPosition(), "删除"+this.getAdapterPosition());
                menu.add(0, 2, this.getAdapterPosition(), "修改"+this.getAdapterPosition());
            }

            public TextView getTextViewName() {
                return textViewName;
            }
            public ImageView getImageViewItem() {
                return imageViewItem;
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public BookItemAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.book_item_row, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        //绑定数据和View
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextViewName().setText(localDataSet.get(position).getTitle());
            viewHolder.getImageViewItem().setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}