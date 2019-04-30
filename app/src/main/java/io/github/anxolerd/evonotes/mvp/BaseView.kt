package io.github.anxolerd.evonotes.mvp

interface BaseView<T : BasePresenter> {
    fun setPresenter(presenter: T)
}