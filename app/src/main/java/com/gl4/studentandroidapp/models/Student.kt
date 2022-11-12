package com.gl4.studentandroidapp.models

import com.gl4.studentandroidapp.enums.GenderEnum
import com.gl4.studentandroidapp.enums.StatusEnum

data class Student(
    val firstName: String,
    val lastName: String,
    var gender: GenderEnum,
    var status: StatusEnum
)
