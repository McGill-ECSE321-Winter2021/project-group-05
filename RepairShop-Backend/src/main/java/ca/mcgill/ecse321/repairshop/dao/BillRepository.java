package ca.mcgill.ecse321.repairshop.dao;

import ca.mcgill.ecse321.repairshop.model.Bill;

import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {
    /**
     * finds a bill by bill id
     *
     * @param id bill id
     * @return bill
     */
    Bill findBillById(Long id);
}
