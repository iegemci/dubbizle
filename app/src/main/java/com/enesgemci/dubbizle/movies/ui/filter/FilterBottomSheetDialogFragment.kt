package com.enesgemci.dubbizle.movies.ui.filter

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.base.BaseBottomSheetDialogFragment
import com.enesgemci.dubbizle.core.dagger.component.DialogFragmentComponent
import com.enesgemci.dubbizle.core.navigation.AutoLayoutNavigationBuilder
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder
import kotlinx.android.synthetic.main.fragment_filter_movies.*

class FilterBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FilterView, FilterPresenter>(), FilterView {

    private var filterListener: FilterListener? = null
    private var minYear: String? = null
    private var maxYear: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonFilter.setOnClickListener {
            filterListener?.onFilterReady(
                editTextMinYear.text.toString(),
                editTextMaxYear.text.toString()
            )
            dismissDialogFragment()
        }

        arguments?.let {
            minYear = it.getString(MIN_YEAR)
            maxYear = it.getString(MAX_YEAR)
        }

        minYear?.let { editTextMinYear.setText(it) }
        maxYear?.let { editTextMaxYear.setText(it) }
    }

    override fun assignListener() {
        filterListener = findListener(FilterListener::class.java)
    }

    override fun injectFragment(component: DialogFragmentComponent) {
        component.inject(this)
    }

    override fun buildNavigation(): NavigationBuilder<*> {
        return AutoLayoutNavigationBuilder.navigation(R.layout.fragment_filter_movies)
    }

    override fun getTheme(): Int {
        return R.style.Theme_Design_Light_BottomSheetDialog
    }

    companion object {

        const val TAG = "FilterBottomSheetDialogFragment"

        private const val MIN_YEAR = "MIN_YEAR"
        private const val MAX_YEAR = "MAX_YEAR"

        fun newInstance(minYear: String?, maxYear: String?) =
            FilterBottomSheetDialogFragment().apply {
                arguments = bundleOf(
                    MIN_YEAR to minYear,
                    MAX_YEAR to maxYear
                )
            }
    }
}