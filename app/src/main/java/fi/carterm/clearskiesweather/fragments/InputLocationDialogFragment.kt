package fi.carterm.clearskiesweather.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputLayout
import fi.carterm.clearskiesweather.R
import fi.carterm.clearskiesweather.utilities.WeatherApplication
import fi.carterm.clearskiesweather.viewmodels.WeatherViewModel

/**
 *
 * Dialog Fragment - display an input view to change current location to a city location.
 *
 * @author Michael Carter
 * @version 1
 *
 */

class InputLocationDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var app: WeatherApplication
    private lateinit var btnAccept: Button
    private lateinit var btnCancel: Button
    private lateinit var btnReset: Button
    lateinit var edit: TextInputLayout
    private var inputText: String? = null


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        /**
         * When the text changes this will check if the max character limit has been reached and set
         * an error if true to the text input. Then it will format the entered character string to
         * ensure only one space is used at a time with no leading or trailing whitespace, set to lowercase.
         */
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            edit.error = if (count > 19) {
                getString(R.string.error_max_character_reached)
            } else {
                ""
            }
            inputText = s.toString().replace(Regex("\\s{2,}"), " ").trim().lowercase()
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_gradient)
        val viewModel: WeatherViewModel by activityViewModels()
        weatherViewModel = viewModel
        return inflater.inflate(R.layout.dialog_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        dialog!!.window?.setLayout(width, height)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = activity?.application as WeatherApplication
        btnAccept = view.findViewById(R.id.btn_accept)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnReset = view.findViewById(R.id.btn_reset)
        edit = view.findViewById(R.id.tf_edit_location)

        edit.editText?.addTextChangedListener(textWatcher)

        btnAccept.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        btnReset.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                // Sets loading and passes text to view model to get location. Closes dialog.
                R.id.btn_accept -> {
                    weatherViewModel.isLoading.value = true
                    inputText?.let { weatherViewModel.getLocationFromName(it) }
                    weatherViewModel.useCurrentLocation = false
                    inputText = ""
                    dismiss()
                }
                // Clear input and close dialog
                R.id.btn_cancel -> {
                    inputText = ""
                    dismiss()
                }
                // Reset location to current location and closes dialog.
                R.id.btn_reset -> {
                    weatherViewModel.isLoading.value = true
                    weatherViewModel.useCurrentLocation = true
                    dismiss()
                }

            }
        }
    }
}