package xetzer.targetproject_v2

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import xetzer.targetproject_v2.viewModel.AddTargetFragmentViewModel
import xetzer.targetproject_v2.viewModel.SharedViewModel

const val TYPED_TEXT_TAG = "TypedText"

class AddTargetFragment : Fragment() {
    private lateinit var addTargetEditText: EditText
    private lateinit var deleteTargetsButton: Button
    private lateinit var targetImageView: ImageView
    private lateinit var takePhotoButton: Button
    private lateinit var takeImageButton: Button
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val addTargetViewModel: AddTargetFragmentViewModel by activityViewModels()

   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_target, container, false)
        addTargetEditText = view.findViewById(R.id.addTarget_editText)
        addTargetEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        targetImageView = view.findViewById(R.id.main_image_imageView)
        takePhotoButton = view.findViewById(R.id.take_photo_button)
        takeImageButton = view.findViewById(R.id.take_image_button)

        takePhotoButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager
            val capturePhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(capturePhoto, PackageManager.MATCH_DEFAULT_ONLY)
            if (resolvedActivity == null && addTargetViewModel.isTargetCreated) {
                isEnabled = false
            }

            setOnClickListener {
                if (addTargetViewModel.isTargetCreated) {
                    capturePhoto.putExtra(MediaStore.EXTRA_OUTPUT, addTargetViewModel.photoUri)

                    val cameraActivities: List<ResolveInfo> = packageManager.queryIntentActivities(
                        capturePhoto, PackageManager.MATCH_DEFAULT_ONLY
                    )

                    for (cameraActivity in cameraActivities) {
                        requireActivity().grantUriPermission(
                            cameraActivity.activityInfo.packageName,
                            addTargetViewModel.photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                    }
                    takePhoto.launch(addTargetViewModel.photoUri)
                }
            }
        }

        if (savedInstanceState != null) {
            addTargetEditText.setText(savedInstanceState.getString(TYPED_TEXT_TAG))
        }
        updatePhotoView()

        addTargetEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val dateTime = CmnFuncClass()
                // скрыть клавиатуру после
                val inputMethodManager =
                    context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                var rptCounter = 0
                val newTargetText = addTargetEditText.text.toString()
                if (newTargetText.isNotEmpty()) {
                    sharedViewModel.targetList.observe(viewLifecycleOwner) { targets ->
                        targets?.let {
                            for (item in it) {
                                if (item.text == newTargetText) {
                                    addTargetViewModel.target = item
                                    rptCounter++
                                    break
                                }
                            }
                        }
                    }
                    if (rptCounter == 0) {
                        addTargetViewModel.target = TargetClass(
                            newTargetText,
                            dateTime.getDayTime()
                        )
                        sharedViewModel.addTarget(addTargetViewModel.target)
                        Toast.makeText(
                            context,
                            getString(R.string.new_target_is_add),
                            Toast.LENGTH_SHORT
                        ).show()
                        startNewTargetObserver()
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.target_is_rpt),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        addTargetViewModel.isTargetCreated = true
                    }
                } else {
                    Toast.makeText(context, getString(R.string.target_is_empty), Toast.LENGTH_SHORT)
                        .show()
                }
                addTargetEditText.text.clear()
                true
            } else {
                false
            }
        }
        return view
    }

    private fun startNewTargetObserver() {
        sharedViewModel.targetList.observe(viewLifecycleOwner) { targets ->
            targets?.let {
                if (targets.contains(addTargetViewModel.target)) {
                    addTargetViewModel.photoFile =
                        sharedViewModel.getPhotoFile(addTargetViewModel.target)
                    addTargetViewModel.photoUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.xetzer.targetproject_v2.android.camera.fileprovider",
                        addTargetViewModel.photoFile
                    )
                    addTargetViewModel.isTargetCreated = true
                }
            }
        }
    }

    private fun updatePhotoView() {
        if (addTargetViewModel.isTargetCreated) {
            if (addTargetViewModel.photoFile.exists()) {
                val pictureUtils = PictureUtils()
                val bitmap = pictureUtils.getScaledBitmap(
                    addTargetViewModel.photoFile.path,
                    requireActivity()
                )
                targetImageView.setImageBitmap(bitmap)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TYPED_TEXT_TAG, addTargetEditText.text.toString())
    }

    override fun onDetach() {
        super.onDetach()
        // Отозвать разрешение на доступ к файлу фотографии по завершении фрагмента
        if (addTargetViewModel.isTargetCreated) {
            requireActivity().revokeUriPermission(
                addTargetViewModel.photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }
    }

    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()){
            success: Boolean ->
        if (success){
            requireActivity().revokeUriPermission(
                addTargetViewModel.photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            updatePhotoView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addTargetViewModel.isTargetCreated = false
    }
}