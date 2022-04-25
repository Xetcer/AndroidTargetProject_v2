package xetzer.targetproject_v2

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import androidx.compose.ui.semantics.SemanticsActions.Dismiss
import androidx.fragment.app.DialogFragment
import java.io.File

private const val ARG_IMAGE = "image"
class TargetDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return  activity?.let {            // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(it)

        //get the layout inflater
        val inflater = requireActivity().layoutInflater

        //get a dialog picture view reference
        // Pass null as the parent view because its going in the dialog layout
        val view = inflater.inflate(R.layout.fragment_picture_dialog, null)

        // Inflate and set the layout for the dialog
        builder.setView(view)

        //get reference to crimePicture image view
        val targetImage = view.findViewById(R.id.full_target_image_view) as ImageView

        //get the image file path argument
        val photoFile = arguments?.getSerializable(ARG_IMAGE) as File

        //get the scaled image
        val pictureUtils: PictureUtils = PictureUtils()
        val bitmap =  pictureUtils.getScaledBitmap(photoFile.path, requireActivity())

        //set the picture in the crimePicture view
        targetImage.setImageBitmap(bitmap)

        //set the dialog characteristics
        builder.setTitle(R.string.cur_target)
            .setNegativeButton(R.string.Dismiss, DialogInterface.OnClickListener{ _, _ -> dialog?.cancel() } )


        // Create the AlertDialog object and return it
        builder.create()

    } ?: throw IllegalStateException("Activity cannot be null")

    }
    companion object {
        fun newInstance(photoFile: File): TargetDialogFragment {
            val args = Bundle().apply { putSerializable(ARG_IMAGE, photoFile) }
            return TargetDialogFragment().apply { arguments = args }
        }
    }
}