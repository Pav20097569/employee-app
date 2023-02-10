package ie.setu

import mu.KotlinLogging

import kotlin.math.round


var employees = EmployeeAPI() // instantiate an instance of the EmployeeAPI class

val logger = KotlinLogging.logger {}  // logger to log the progress of the app

fun main(args: Array<String>){
    logger.info { "Launching Employee App" } // log that the employee app is launching



    start() // call the start function to run the app
}

fun menu() : Int { // function to display the menu to the user
    logger.info { "Printing Menu" }  // log that the menu is being displayed
    print(""" 
         |Employee Menu
         |   1. Add Employee
         |   2. List All Employees
         |   3. Search Employees 
         |   4. Print Payslip for Employee
         |   5. Remove Employee
         |   6. Update Employee Details
         |   7. Calculate Net Salary
         |  -1. Exit
         |       
         |Enter Option : """.trimMargin()) // display the menu to the user
    return readLine()!!.toInt() // return the option selected by the user as an integer
}

fun start() {
    var input: Int // variable to store the user's selected option

    do {
        input = menu() // call the menu function and store the selected option in the input variable
        when (input) { // evaluate the selected option
            1 -> add() // if the selected option is 1, call the add function
            2 -> list() // if the selected option is 2, call the list function
            3 -> search()
            4 -> paySlip()
            5 -> removeEmployee()
            6 -> updateEmployeeDetails()
            -99 -> dummyData()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")  // if the selected option is invalid, display an error message
        }
        println() // print a blank line for readability
    } while (input != -1)  // continue to loop until the user selects -1 to exit the app
}



fun add(){
    logger.info { "Enter Employee Details" } // log that the user is entering employee details
    print("Enter first name: ")  // prompt the user to enter the first name of the employee
    val firstName = readLine().toString()
    print("Enter surname: ")
    val surname = readLine().toString()
    print("Enter gender (m/f): ")
    val gender = readLine()!!.toCharArray()[0]
    print("Enter gross salary: ")
    val grossSalary = readLine()!!.toDouble()
    print("Enter PAYE %: ")
    val payePercentage = readLine()!!.toDouble()
    print("Enter PRSI %: ")
    val prsiPercentage = readLine()!!.toDouble()
    print("Enter Annual Bonus: ")
    val annualBonus= readLine()!!.toDouble()
    print("Enter Cycle to Work Deduction: ")
    val cycleToWorkMonthlyDeduction= readLine()!!.toDouble()

    employees.create(Employee(firstName, surname, gender, 0, grossSalary, payePercentage, prsiPercentage, annualBonus, cycleToWorkMonthlyDeduction)) //creates new instance of employee
}

fun list(){  //function used to list all the employees
    employees.findAll()
        .forEach{ println(it) }
}

fun search() { //search for employees using employeeId
    val employee = getEmployeeById()  // getEmployeeById is called to retrieve the employee with the matching ID
    if (employee == null)
        println("No employee found")
    else
        println(employee)
}
fun removeEmployee() {  //removes employee from list using ID
    val employee = getEmployeeById()  // getEmployeeById is called to retrieve the employee with the matching ID
    if (employee != null) {
        employees.remove(employee.employeeID)
        println("Employee with id ${employee.employeeID} was successfully removed")  // If an employee with the matching ID is found, it is removed from the employee list and a message is printed indicating the removal was successful
    } else {
        println("No employee found")  // If an employee with the matching ID is not found, a message indicating this is printed
    }
}

fun paySlip(){
    val employee = getEmployeeById()
    if (employee != null)
        println(employee.getPayslip())
}

fun updateEmployeeDetails() {
    val employee = getEmployeeById() //get employee id
    if (employee != null) { //check if employee with this id exists
        print("Enter new first name: ")
        val firstName = readLine().toString()
        print("Enter new surname: ")
        val surname = readLine().toString()
        print("Enter new gender (m/f): ")
        val gender = readLine()!!.toCharArray()[0]
        print("Enter new gross salary: ")
        val grossSalary = readLine()!!.toDouble()
        print("Enter new PAYE %: ")
        val payePercentage = readLine()!!.toDouble()
        print("Enter new PRSI %: ")
        val prsiPercentage = readLine()!!.toDouble()
        print("Enter new Annual Bonus: ")
        val annualBonus= readLine()!!.toDouble()
        print("Enter new Cycle to Work Deduction: ")
        val cycleToWorkMonthlyDeduction= readLine()!!.toDouble()


        //updates the employee details
        employee.firstName = firstName
        employee.surname = surname
        employee.gender = gender
        employee.grossSalary = grossSalary
        employee.payePercentage = payePercentage
        employee.prsiPercentage = prsiPercentage
        employee.annualBonus = annualBonus
        employee.cycleToWorkMonthlyDeduction = cycleToWorkMonthlyDeduction

        employees.update(employee)
        println("Employee with id ${employee.employeeID} was successfully updated")
    } else {
        println("No employee found")
    }
}





internal fun getEmployeeById(): Employee? {
    print("Enter the employee id to search by: ")
    val employeeID = readLine()!!.toInt()
    return employees.findOne(employeeID)
}

internal fun dummyData() {
    employees.create(Employee("Joe", "Soap", 'm', 0, 35655.43, 31.0, 7.5, 2000.0, 25.6))
    employees.create(Employee("Joan", "Murphy", 'f', 0, 54255.13, 32.5, 7.0, 1500.0, 55.3))
    employees.create(Employee("Mary", "Quinn", 'f', 0, 75685.41, 40.0, 8.5, 4500.0, 0.0))
}

//https://discuss.kotlinlang.org/t/how-do-you-round-a-number-to-n-decimal-places/8843
//There are several options...try each of them out
fun roundTwoDecimals(number: Double) = round(number * 100) / 100
//fun roundTwoDecimals(number: Double) = "%.2f".format(number).toDouble()
