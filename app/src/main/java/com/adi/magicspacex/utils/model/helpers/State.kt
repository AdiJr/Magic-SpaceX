package com.adi.magicspacex.utils.model.helpers

/**
 * State holder acquiring states Idle, Loading or Error.
 */
sealed class State : DataState<Nothing>() {

    /**
     * Nothing to do.
     */
    object Idle : State()

    /**
     * Data is loading.
     */
    object Loading : State()

    /**
     * Some error has happened.
     */
    data class Error(val error: Throwable) : State()
}

/**
 * State data holder which includes also [State] states.
 */
sealed class DataState<out T> {

    /**
     * Some data are loaded.
     */
    data class Loaded<out T>(val data: T) : DataState<T>()
}