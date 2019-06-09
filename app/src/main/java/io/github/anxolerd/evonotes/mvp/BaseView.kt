package io.github.anxolerd.evonotes.mvp

interface BaseView<V: BaseView<V, P>, P: BasePresenter<P, V>> {
    fun setPresenter(presenter: P)
}