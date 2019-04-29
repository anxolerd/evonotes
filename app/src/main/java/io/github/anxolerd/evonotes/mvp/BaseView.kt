package io.github.anxolerd.evonotes.mvp

public interface BaseView<T: BasePresenter> {
    fun setPresenter(presenter: T)
}