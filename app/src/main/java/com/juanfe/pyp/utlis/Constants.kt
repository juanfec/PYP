package com.juanfe.pyp.utlis

object Constants {
    /**
     * the index is the day of the week and the values are the ones that have restriction
     */
    val PICOYPLACADIA = arrayOf(
        intArrayOf(1,2),
        intArrayOf(3,4),
        intArrayOf(5,6),
        intArrayOf(7,8),
        intArrayOf(9,0),
        intArrayOf(),
        intArrayOf())
    /**
     * this is the range that have restriction is in military hour so is easier to work with
     */
    val PICOYPLACAHORA = arrayOf(
        700,
        930,
        1600,
        1930
    )
}