package ca.mcgill.ecse321.repairshop.utility;

import ca.mcgill.ecse321.repairshop.dto.*;
import ca.mcgill.ecse321.repairshop.model.*;

import java.util.ArrayList;
import java.util.List;

public class RepairShopUtil {

    public static Person currentUser;

    /**
     * returns user
     *
     * @return Person
     */
    public static Person getCurrentUser() {
        return currentUser;
    }

    /**
     * sets user
     *
     * @param currentUser Person
     */
    public static void setCurrentUser(Person currentUser) {
        RepairShopUtil.currentUser = currentUser;
    }

    /**
     * Converts an iterable data type to a list
     * 
     * @param iterable
     * @param <T>
     * @return list<T>
     */
    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    /**
     * covert appointnment to transfer object
     *
     * @param appointment appointment
     * @return appointment dto
     */
    public static AppointmentDto convertToDto(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("There is no such Appointment!");
        }
        // service, customer, timeslot, bill
        AppointmentDto appointmentDto = new AppointmentDto(convertToDto(appointment.getServices()),
                convertToDto(appointment.getCustomer()), convertToDto(appointment.getTimeslot()),
                convertToDto(appointment.getBill()), appointment.getId());
        return appointmentDto;
    }

    /**
     * convert timeslot to dto
     *
     * @param timeSlot timeslot
     * @return timeslot dto
     */
    public static TimeSlotDto convertToDto(TimeSlot timeSlot) {
        if (timeSlot == null) {
            throw new IllegalArgumentException("There is no such TimeSlot!");
        }
        // service, customer, timeslot bill
        TimeSlotDto timeSlotDto = new TimeSlotDto(timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime(),
                timeSlot.getId());
        return timeSlotDto;
    }

    /**
     * convert service to dto
     *
     * @param service service
     * @return service dto
     */
    public static BookableServiceDto convertToDto(BookableService service) {
        if (service == null) {
            throw new IllegalArgumentException("There is no such Service!");
        }
        // service, customer, timeslot bill
        BookableServiceDto serviceDto = new BookableServiceDto(service.getName(), service.getCost(),
                service.getDuration());
        serviceDto.setId(service.getId());
        return serviceDto;
    }

    /**
     * convert list of services to list of dtos
     *
     * @param services list of services
     * @return list of dtos
     */
    public static List<BookableServiceDto> convertToDto(List<BookableService> services) {
        List<BookableServiceDto> serviceDtos = new ArrayList<>();
        if (services == null) {
            throw new IllegalArgumentException("There is no such list of ervices!");
        }
        for (BookableService s : services) {
            serviceDtos.add(convertToDto(s));
        }
        return serviceDtos;
    }

    /**
     * convert customer to dto
     *
     * @param customer customer
     * @return dto
     */
    public static CustomerDto convertToDto(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("There is no such Customer!");
        }
        CustomerDto customerDto = new CustomerDto(customer.getEmail(), customer.getUsername(), customer.getPassword(),
                customer.getCardNumber(), customer.getCvv(), customer.getExpiry());
        customerDto.setId(customer.getId());
        customerDto.setPersonType(customer.getPersonType());
        return customerDto;
    }

    /**
     * convert bill to dto
     *
     * @param bill bill
     * @return dto
     */
    public static BillDto convertToDto(Bill bill) {
        if (bill == null) {
            //throw new IllegalArgumentException("There is no such Bill!");
            return new BillDto();       //TODO: needs to be fix
        }
        BillDto billDto = new BillDto(bill.getDate(), bill.getTotalCost(), bill.getId());
        return billDto;
    }

    /**
     * convert business to dto
     *
     * @param business business
     * @return business
     */
    public static BusinessDto convertToDto(Business business) {
        if (business == null) {
            throw new IllegalArgumentException("There is no such Bill!");
        }
        BusinessDto businessDto = new BusinessDto(business.getName(), business.getAddress(), business.getPhoneNumber(),
                business.getEmail(), business.getId());
        return businessDto;
    }

    /**
     * convert list of bills to list of dtos
     *
     * @param bills list of bills
     * @return list of dtos
     */
    public static List<BillDto> convertBillToDto(List<Bill> bills) {
        List<BillDto> billDtoList = new ArrayList<BillDto>();
        for (Bill b : bills) {
            billDtoList.add(convertToDto(b));
        }
        return billDtoList;
    }

    /**
     * converts list of appointments to list of dtos
     *
     * @param appointments list of appointments
     * @returnlist of dtos
     */
    public static List<AppointmentDto> convertAppointmentsToDto(List<Appointment> appointments) {
        List<AppointmentDto> appointmentDtoList = new ArrayList<AppointmentDto>();
        for (Appointment app : appointments) {
            appointmentDtoList.add(convertToDto(app));
        }
        return appointmentDtoList;
    }

    /**
     * returns total cost of an appointment
     *
     * @param appointment appointment
     * @return cost
     */
    public static float getTotalCostOfAppointment(Appointment appointment) {
        float sum = 0L;
        for (BookableService bookableService : appointment.getServices()) {
            sum += bookableService.getCost();
        }
        return sum;
    }

    /**
     * convert technician to dto
     *
     * @param technician technician
     * @return dto
     */
    public static TechnicianDto convertToDto(Technician technician) {
        TechnicianDto technicianDto = new TechnicianDto();
        technicianDto.setEmail(technician.getEmail());
        technicianDto.setPassword(technician.getPassword());
        technicianDto.setUsername(technician.getUsername());
        technicianDto.setEmail(technician.getEmail());
        technicianDto.setId(technician.getId());
        technicianDto.setPersonType(technician.getPersonType());
        return technicianDto;
    }

    /**
     * convert admin to dto
     *
     * @param administrator admin
     * @return dot
     */
    public static AdministratorDto convertToDto(Administrator administrator) {
        AdministratorDto administratorDto = new AdministratorDto();
        administratorDto.setEmail(administrator.getEmail());
        administratorDto.setPassword(administrator.getPassword());
        administratorDto.setUsername(administrator.getUsername());
        administratorDto.setEmail(administrator.getEmail());
        administratorDto.setId(administrator.getId());
        administratorDto.setPersonType(administrator.getPersonType());
        return administratorDto;
    }

    /**
     * convert dto to appt
     *
     * @param appointmentDto dto
     * @return appointment
     */
    public static Appointment convertToEntity(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setServices(convertToListOfEntity(appointmentDto.getServices()));
        appointment.setId(appointmentDto.getId());
        appointment.setTimeslot(convertToEntity(appointmentDto.getTimeSlot()));
        appointment.setCustomer(convertToEntity(appointmentDto.getCustomer()));
        return appointment;
    }

    /**
     * convert dto to service
     *
     * @param bookableServiceDto dto
     * @return sevice
     */
    public static BookableService convertToEntity(BookableServiceDto bookableServiceDto) {
        BookableService bookableService = new BookableService();
        bookableService.setName(bookableServiceDto.getName());
        bookableService.setCost(bookableServiceDto.getCost());
        bookableService.setName(bookableServiceDto.getName());
        bookableService.setDuration(bookableServiceDto.getDuration());
        return bookableService;
    }

    /**
     * convert list of dtos to list of services
     *
     * @param bookableServiceDtos dtos
     * @return services
     */
    public static List<BookableService> convertToListOfEntity(List<BookableServiceDto> bookableServiceDtos) {
        List<BookableService> bookableServices = new ArrayList<>();
        for (BookableServiceDto bookableServiceDto : bookableServiceDtos) {
            bookableServices.add(convertToEntity(bookableServiceDto));
        }
        return bookableServices;
    }

    /**
     * convert dto to timeslot
     *
     * @param timeSlotDto dto
     * @return timeslot
     */
    public static TimeSlot convertToEntity(TimeSlotDto timeSlotDto) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setDate(timeSlotDto.getDate());
        timeSlot.setEndTime(timeSlotDto.getEndTime());
        timeSlot.setStartTime(timeSlotDto.getStartTime());
        timeSlot.setId(timeSlotDto.getId());
        return timeSlot;
    }

    /**
     * convert dto to customer
     *
     * @param customerDto dto
     * @return customer
     */
    public static Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setNoShow(customerDto.getNoShow());
        customer.setCardNumber(customerDto.getCardNumber());
        customer.setEmail(customerDto.getEmail());
        customer.setEmail(customerDto.getEmail());
        customer.setCvv(customerDto.getCvv());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setPersonType(customerDto.getPersonType());
        return customer;
    }

}
