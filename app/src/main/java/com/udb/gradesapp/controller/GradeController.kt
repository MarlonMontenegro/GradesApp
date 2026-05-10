package com.udb.gradesapp.controller

import com.udb.gradesapp.model.Student

class GradeController {

    fun calculateAverage(student: Student): Double {
        if (student.grades.isEmpty()){
            return 0.0
        }

        return student.grades.average();
    }

    fun getResult(average:Double) : String {

        return if (average >= 6.0){
            "Aprobado"
        } else {
            "Reprobado"
        }
    }

    
