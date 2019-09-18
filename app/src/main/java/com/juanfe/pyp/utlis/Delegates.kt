package com.juanfe.pyp.utlis

import kotlinx.coroutines.*

/**
 * this is an aproach to be able to call a lazy object from a courutine so we dont initialize an object that is not needed yet
 */
fun<T> lazyDeferred(block : suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}