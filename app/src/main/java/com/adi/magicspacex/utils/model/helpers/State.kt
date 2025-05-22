package com.adi.magicspacex.utils.model.helpers

/**
 * State holder acquiring states Idle, Loading or Error.
 *
 * This sealed class represents the different states an operation or data fetching process can be in.
 * It extends `DataState<Nothing>` as it doesn't directly hold data itself, but rather indicates
 * the overall status.
 */
sealed class State : DataState<Nothing>() {

    /**
     * Represents an idle state where no operation is in progress.
     */
    data object Idle : State()

    /**
     * Represents a loading state where an operation is currently in progress.
     */
    data object Loading : State()

    /**
     *  Signifies that an error has occurred during the operation, and it contains the
     *  [Throwable] that caused the error.
     */
    data class Error(val error: Throwable) : State()
}

/**
 * Represents the state of data, which can be either loaded, loading, idle, or an error.
 * This sealed class is designed to be used in conjunction with [State] to provide a comprehensive
 * representation of data states in an application.
 */
sealed class DataState<out T> {

    /**
     * Represents a state where data has been successfully loaded.
     *
     * This data class holds the actual data of type [T].
     *
     * @param data The successfully loaded data.
     * @param T The type of the loaded data.
     */
    data class Loaded<out T>(val data: T) : DataState<T>()
}
