package seg3x02.employeeGql.resolvers

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver (val mongoOperations: MongoOperations,
                         private val EmployeesRepository: EmployeesRepository) {


    @QueryMapping
    fun allEmployees(@Argument bookNumber: Int): List<Employee> {
        return EmployeesRepository.findAll()
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput) : Employee {
        val number: Float = 0.0f
        val employee = Employee(input.name.orEmpty(), 
                                input.dateOfBirth.orEmpty(), 
                                input.city.orEmpty(), 
                                input.salary ?: number,
                                input.gender.orEmpty(), 
                                input.email.orEmpty())

        employee.id = UUID.randomUUID().toString()
        EmployeesRepository.save(employee)
        return employee
    }


}
