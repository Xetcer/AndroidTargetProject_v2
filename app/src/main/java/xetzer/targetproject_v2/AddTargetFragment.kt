package xetzer.targetproject_v2

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import xetzer.targetproject_v2.viewModel.SharedViewModel
import java.io.File

const val TYPED_TEXT_TAG = "TypedText"
const val REQUEST_PHOTO = 2

class AddTargetFragment : Fragment() {
    private lateinit var addTargetEditText: EditText
    private lateinit var deleteTargetsButton: Button
    private lateinit var targetImageView: ImageView
    private lateinit var takePhotoButton: Button
    private lateinit var takeImageButton: Button
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri
    private lateinit var target: TargetClass
    private var isTargetCreated : Boolean = false
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_target, container, false)
        addTargetEditText = view.findViewById(R.id.addTarget_editText)
        addTargetEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        deleteTargetsButton = view.findViewById(R.id.delete_all_targets_button)
        deleteTargetsButton.setOnClickListener {
            sharedViewModel.deleteBD()
        }
        targetImageView = view.findViewById(R.id.main_image_imageView)
        takePhotoButton = view.findViewById(R.id.take_photo_button)
        takeImageButton = view.findViewById(R.id.take_image_button)

        takePhotoButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val capturePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(capturePhoto, PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null && isTargetCreated) {
                isEnabled = false
            }

            setOnClickListener {
                if (isTargetCreated) {
                    capturePhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                    val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(
                        capturePhoto, PackageManager.MATCH_DEFAULT_ONLY
                    )

                    for (cameraActivity in cameraActivities) {
                        requireActivity().grantUriPermission(
                            cameraActivity.activityInfo.packageName,
                            photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                    startActivityForResult(capturePhoto, REQUEST_PHOTO)
                }
            }
        }



        if (savedInstanceState != null) {
            addTargetEditText.setText(savedInstanceState.getString(TYPED_TEXT_TAG))
        }
        addTargetEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val dateTime = CmnFuncClass()
                // скрыть клавиатуру после
                val inputMethodManager =
                    context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                var rptCounter = 0
                sharedViewModel.targetList.observe(viewLifecycleOwner) { targets ->
                    targets?.let {
                        for (item in it) {
                            if (item.target == addTargetEditText.text.toString()) {
                                target = item
                                rptCounter++
                                break
                            }
                        }
                    }
                }
                if (rptCounter == 0) {
                    target = TargetClass(
                        addTargetEditText.text.toString(),
                        dateTime.getDayTime()
                    )
                    sharedViewModel.addTarget(target)
                    Toast.makeText(
                        context,
                        getString(R.string.new_target_is_add),
                        Toast.LENGTH_SHORT
                    ).show()
                    startNewTargetObserver();
                } else {
                    Toast.makeText(context, getString(R.string.target_is_rpt), Toast.LENGTH_SHORT)
                        .show()
                    isTargetCreated = true
                }
                addTargetEditText.text.clear()
                true
            } else {
                false
            }
        }
        return view
    }

    private fun startNewTargetObserver(){
        sharedViewModel.targetList.observe(viewLifecycleOwner){
                targets ->
            targets?.let {
                if (targets.contains(target)){
                    photoFile = sharedViewModel.getPhotoFile(target)
                    photoUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.xetzer.targetproject_v2.android.camera.fileprovider",
                        photoFile
                    )
                    isTargetCreated = true
                }
            }
        }
    }

    private fun updatePhotoView(){
        if (photoFile.exists()){
            var pictureUtils = PictureUtils()
            val bitmap = pictureUtils.getScaledBitmap(photoFile.path, requireActivity())
            targetImageView.setImageBitmap(bitmap)
        }else{
            targetImageView.setImageDrawable(null)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TYPED_TEXT_TAG, addTargetEditText.text.toString())
    }

    override fun onDetach() {
        super.onDetach()
        // Отозвать разрешение на доступ к файлу фотографии по завершении фрагмента
        if (isTargetCreated) {
            requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PHOTO){
            // отозвать разрешение на доступ к файлу фотографии после завершения работы камеры.
            requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            updatePhotoView()
        }
    }
}