package edu.fsu.cs.mobile.safereader;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


// tutorial followed: https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> BookList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView author, publishDate, title;
        public ImageView image;
        Button readButton, commentButton;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.coverImage);
            author = (TextView) view.findViewById(R.id.author);
            publishDate = (TextView) view.findViewById(R.id.published);
            title = (TextView) view.findViewById(R.id.title);

            this.readButton = (Button) view.findViewById(R.id.read_button);
            this.commentButton = (Button) view.findViewById(R.id.comment_button);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_home, parent, false);

        return new MyViewHolder(itemView);
    }

    public RVAdapter(Context mContext, List<Book> bookList) {
        this.mContext = mContext;
        this.BookList = bookList;
    }

    @Override
    public int getItemCount() {
        return BookList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder theHolder, int position) {
        Book book = BookList.get(position);
        theHolder.author.setText(book.getAuthor());
        theHolder.publishDate.setText(book.getPublishDate());
        theHolder.title.setText(book.getTitle());

        //I DON'T KNOW HOW TO DO IMAGES

        theHolder.readButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// PUT IN WHAT YOU WANT IT TO DO WHEN YOU PRESS READ HERE
            }
        });

        theHolder.commentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// PUT IN WHAT YOU WANT IT TO DO WHEN YOU PRESS READ HERE
            }
        });
    }
}

//other resources:
//https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465
//
