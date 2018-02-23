package pons.carretero.myvocabulary.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pons.carretero.myvocabulary.Objects.IrregularVerb;
import pons.carretero.myvocabulary.R;

/**
 * Created by carre on 29/11/2017.
 */

public class IrregularAdapter extends ArrayAdapter<IrregularVerb> {
    private static final String LOG_TAG = IrregularAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param irregularList A List of AndroidFlavor objects to display in a list
     */
    public IrregularAdapter(Activity context, ArrayList<IrregularVerb> irregularList) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, irregularList);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate( R.layout.irregular_list, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        IrregularVerb currentIrregular = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView verbTextView = (TextView) listItemView.findViewById(R.id.verb);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        verbTextView.setText(currentIrregular.getVerb());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView psTextView = (TextView) listItemView.findViewById(R.id.ps);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        psTextView.setText(currentIrregular.getPastSimple());

        TextView ppTextView = (TextView) listItemView.findViewById(R.id.pp);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        ppTextView.setText(currentIrregular.getPastParticiple());

        //TextView espTextView = (TextView) listItemView.findViewById(R.id.esp);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        //espTextView.setText(currentIrregular.getEsp());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
