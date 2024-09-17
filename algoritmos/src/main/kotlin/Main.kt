package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var array = kotlin.arrayOf(5,2,4,9,3,10)
    selectionSort(array).forEach {
        println(it)
    }
}

fun selectionSort(array: Array<Int>): Array<Int>{
     for(i in 0 until array.size - 1){
        for(j in i+1 until array.size){
            if(array[j] < array[i]){
                var aux = array[i]
                array[i] = array[j]
                array[j] = aux
            }
        }
    }
    return array
}

fun selectionSortOptimized(array: Array<Int>): Array<Int>{
    for(i in 0 until array.size - 1){
        var indMenor = i
        for(j in i+1 until array.size){
            if(array[j] < array[indMenor]){
                indMenor = j
                var aux = array[i]
                array[i] = array[indMenor]
                array[indMenor] = aux
                array[i] = array[]
                array[j] = aux
            }
        }
    }
    return array
}

fun bubbleSort(array: Array<Int>){
    for(i in 0 until array.size - 1){
        for (j in 1 until array.size - i){
            if(array[i] > array[j]){
                var aux = array[i]
                array[i] = array[j]
                array[j] = array[i]
            }
        }
    }
}
