package io.github.anxolerd.evonotes.mvp

interface BasePresenter<P: BasePresenter<P, V>, V: BaseView<V, P>> {
    fun setView(view: V)
}