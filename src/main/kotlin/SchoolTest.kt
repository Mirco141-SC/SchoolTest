class SchoolTest(studentsValue:Int, minMarkValue:Double = 1.0, maxMarkValue:Double = 10.0) {
    init {
        require(studentsValue > 0) {"The number of students must be higher than 0"}
        require(maxMarkValue > minMarkValue) {"The maximum mark value must be higher than the minimum mark's one ($minMarkValue)."}
    }

    val marks = mutableListOf<Double>()

    var students:Int = studentsValue
        set(value) {
            require(value > field) {"You can only add more students."} //Since removing them would require removing marks from the list

            field = value
        }

    var minMark:Double = minMarkValue
        set(value) {
            require(value < maxMark) {"The new value must be lower than the maximum mark's value ($maxMark)"}

            val invalidMarks = mutableListOf<Pair<Int, Double>>()
            var counter = 0

            for(mark in marks) {
                if(mark !in value..maxMark) {
                    invalidMarks.add(Pair(counter, mark))
                }

                counter++
            }

            require(invalidMarks.size == 0){"After your changes, there are some marks that will result invalid: $invalidMarks"}

            field = value
        }

    var maxMark:Double = maxMarkValue
        set(value) {
            require(value > minMark) {"The new value must be higher than the minimum mark's value ($minMark)"}

            val invalidMarks = mutableListOf<Pair<Int, Double>>()
            var counter = 0

            for(mark in marks) {
                if(mark !in minMark..value) {
                    invalidMarks.add(Pair(counter, mark))
                }

                counter++
            }

            require(invalidMarks.size == 0){"After your changes, there are some marks that will result invalid: $invalidMarks"}

            field = value
        }

    val average:Double
        get() {
            var total = 0.0

            for(mark in marks) {
                total += mark
            }

            return total/marks.size
        }

    val maxMarkOccurrence: Int
        get() {
            return occurrence(maxMark)
        }


    val minMarkOccurrence: Int
        get() {
            return occurrence(minMark)
        }

    fun addMark(value:Double) {
        require(value in minMark .. maxMark) {"The mark's value must be between $minMark and $maxMark"}

        //If the program sees that the user is trying to add more marks than the number of students, it automatically changes the number of students
        if(marks.size + 1 > students) {
            students++
        }

        marks.add(value)
    }

    fun editMark(identifier:Int, value:Double) {
        require(identifier in 0..marks.lastIndex) {"The ID value does not lead to an existing mark"}
        require(value in minMark .. maxMark) {"The mark's value must be between $minMark and $maxMark"}

        marks[identifier] = value
    }

    fun occurrence(value:Double):Int {
        //No require statement because there could be some mark that was added before changing minimum and maximum marks values
        var counter = 0

        for(mark in marks) {
            if(mark == value) {
                counter += 1
            }
        }

        return counter
    }
}
