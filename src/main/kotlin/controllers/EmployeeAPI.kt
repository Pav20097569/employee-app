package ie.setu.controllers

import ie.setu.models.Employee

var lastId = 0

internal fun getId(): Int {
    return lastId++
}

class EmployeeAPI {

    private val employees = ArrayList<Employee>()

    fun findAll(): List<Employee> {
        return employees
    }

    fun findOne(id: Int): Employee? {
        return employees.find { p -> p.employeeID == id }
    }

    fun create(employee: Employee) {
        employee.employeeID = getId()
        employees.add(employee)
    }
    fun remove(id: Int) {
        employees.removeIf { it.employeeID == id } //removes employee if id matches the id input by the user

    }
    fun update(employee: Employee) {
        for (i in employees.indices) {  // loop through the list of employees
            if (employees[i].employeeID == employee.employeeID) { // check if the current employee in the list has the same id as the employee passed as a parameter
                employees[i] = employee  // update the current employee in the list with the updated employee data
                break // break out of the loop as the employee has been found and updated
            }
        }
    }
}


