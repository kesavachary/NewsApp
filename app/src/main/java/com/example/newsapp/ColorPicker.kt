package com.example.newsapp

object ColorPicker {
    val colors= arrayOf("#C0C0C0","#800000","#808000","#00FF00","#008080","#000080","#FF00FF","#800080","#CD5C5C","#E9967A","#F08080")
    var colorIndex=1
    fun getColor():String{
        //for using this condition colors are appear cyclic form
        return colors[colorIndex++ % colors.size]
    }
}