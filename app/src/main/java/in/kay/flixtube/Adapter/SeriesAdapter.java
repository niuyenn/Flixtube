package in.kay.flixtube.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import in.kay.flixtube.Model.SeriesModel;
import in.kay.flixtube.R;
import in.kay.flixtube.UI.HomeUI.DetailActivity;

public class SeriesAdapter extends FirebaseRecyclerAdapter<SeriesModel, SeriesAdapter.SeriesAdapterViewHolder> {
Context context;

    public SeriesAdapter(@NonNull FirebaseRecyclerOptions<SeriesModel> options, Context context) {
        super(options);
        this.context = context;
    }

    public SeriesAdapter(@NonNull FirebaseRecyclerOptions<SeriesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SeriesAdapterViewHolder holder, final int position, @NonNull final SeriesModel model) {
        Typeface medium = Typeface.createFromAsset(context.getAssets(), "Gilroy-Medium.ttf");
        Typeface regular = Typeface.createFromAsset(context.getAssets(), "Gilroy-Regular.ttf");
        holder.title.setText(model.getTitle());
        holder.title.setTypeface(medium);
        holder.genre.setText(model.getCategory());
        holder.genre.setTypeface(regular);
        Picasso.get()
                .load(model.getImage())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                        Picasso.get()
                                .load(model.getImage())
                                .into(holder.img);
                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("imdb", model.getImdb());
                intent.putExtra("type", "Webseries");
                intent.putExtra("trailer", model.getTrailer());
                intent.putExtra("key", getRef(position).getKey());
                intent.putExtra("movieType",model.getType());
                intent.putExtra("key", getRef(position).getKey());
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                view.getContext().startActivity(intent);
                Animatoo.animateSlideLeft(context);
            }
        });
    }

    @NonNull
    @Override
    public SeriesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_view, parent , false);
        return  new SeriesAdapterViewHolder(view);
    }

    public class  SeriesAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title,genre;
        ImageView img,vip;
        public SeriesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_movie_name);
            genre=itemView.findViewById(R.id.tv_genre);
            img=itemView.findViewById(R.id.iv_cover_img);
            vip=itemView.findViewById(R.id.iv_vip);
            vip.setVisibility(View.GONE);
        }
    }
}
