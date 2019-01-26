package com.enesgemci.dubbizle.movies.ui

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enesgemci.dubbizle.R
import com.enesgemci.dubbizle.core.base.BaseFragment
import com.enesgemci.dubbizle.core.dagger.component.FragmentComponent
import com.enesgemci.dubbizle.core.extension.onClick
import com.enesgemci.dubbizle.core.navigation.AutoLayoutNavigationBuilder.navigation
import com.enesgemci.dubbizle.core.navigation.NavigationBuilder
import com.enesgemci.dubbizle.core.navigation.menu.MenuAction
import com.enesgemci.dubbizle.core.navigation.menu.MenuActions
import com.enesgemci.dubbizle.movies.ui.adapter.MoviesAdapter
import com.enesgemci.dubbizle.movies.ui.event.OpenDetailsEvent
import com.enesgemci.dubbizle.movies.ui.filter.FilterBottomSheetDialogFragment
import com.enesgemci.dubbizle.movies.ui.filter.FilterListener
import com.enesgemci.dubbizle.movies.ui.model.ui.MoviesUIModel
import com.enesgemci.dubbizle.view.GridItemEqualOffsetDecoration
import com.github.ybq.endless.Endless
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlin.properties.Delegates


class MoviesFragment : BaseFragment<MoviesUIView, MoviesUIPresenter>(), MoviesUIView, FilterListener, MenuAction {

    private var currentPage: Int = 1
    private var adapter: MoviesAdapter? = null

    private var endless: Endless by Delegates.notNull()

    private var minYear: String? = null
    private var maxYear: String? = null

    override fun onFragmentStarted() {

        fab.onClick {
            val filterFragment = FilterBottomSheetDialogFragment.newInstance(minYear, maxYear)
            filterFragment.show(childFragmentManager, FilterBottomSheetDialogFragment.TAG)
        }

        swipeRefreshLayout.setOnRefreshListener {
            fetchData(1, false, minYear, maxYear)
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL

        recyclerViewMovies.layoutManager = gridLayoutManager
        recyclerViewMovies.addItemDecoration(
            GridItemEqualOffsetDecoration(
                resources.getDimensionPixelOffset(com.enesgemci.dubbizle.R.dimen.grid_view_item_space)
            )
        )

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter?.itemCount) {
                    gridLayoutManager.spanCount
                } else {
                    1
                }
            }
        }

        adapter = MoviesAdapter(requireContext()) { sharedView, movie ->
            presenter.onMovieClicked(OpenDetailsEvent(sharedView, movie))
        }

        fetchData(1, false)
    }

    private fun initInfiniteScroll() {
        endless = Endless.applyTo(recyclerViewMovies, getLoadingView())
        endless.setAdapter(adapter)
        endless.setLoadMoreListener { page -> fetchData(page, false, minYear, maxYear) }
    }

    private fun fetchData(page: Int, showLoading: Boolean, minYear: String? = null, maxYear: String? = null) {
        currentPage = page

        if (!showLoading && page == 1) {
            swipeRefreshLayout.isRefreshing = true
        }

        presenter.getMovies(page, showLoading, minYear, maxYear)
    }

    override fun injectFragment(component: FragmentComponent) {
        component.inject(this)
    }

    override fun buildNavigation(): NavigationBuilder<*> {
        return navigation(R.layout.fragment_movies)
            .includeToolbar()
            .setToolbarTitleRes(R.string.app_name)
            .addMenuRes(R.menu.menu_main, MenuActions.MenuActionItem.item(R.id.action_clear, this))
    }

    override fun setMovies(moviesUIModel: MoviesUIModel) {
        swipeRefreshLayout.isRefreshing = false
        adapter?.updateMovies(moviesUIModel.movies, currentPage)

        if (currentPage != 1) {
            endless.loadMoreComplete()
        } else {
            initInfiniteScroll()
        }

        if (moviesUIModel.movies.isNullOrEmpty()) {
            recyclerViewMovies.isVisible = false
            textViewEmpty.isVisible = true
        } else {
            textViewEmpty.isVisible = false
            recyclerViewMovies.isVisible = true
        }
    }

    private fun getLoadingView() =
        View.inflate(requireContext(), R.layout.layout_loading, null).apply {
            layoutParams =
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

    override fun onFilterReady(minYear: String?, maxYear: String?) {
        this.minYear = minYear
        this.maxYear = maxYear

        Endless.remove(recyclerViewMovies)
        fetchData(1, true, minYear, maxYear)
    }

    override fun execute() {
        if (minYear != null || maxYear != null) {
            onFilterReady(null, null)
        }
    }

    companion object {

        fun newInstance() = MoviesFragment()
    }
}