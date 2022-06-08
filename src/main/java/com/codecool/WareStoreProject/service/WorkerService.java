package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WorkdayJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WorkerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WorkerService {
    private WorkerJPARepository workerRepository;
    private WorkdayJPARepository workdayJPARepository;
    private WarehouseJPARepository warehouseJPARepository;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public WorkerService(WorkerJPARepository workerRepository, WorkdayJPARepository workdayJPARepository,
                         WarehouseJPARepository warehouseJPARepository) {
        this.workerRepository = workerRepository;
        this.workdayJPARepository = workdayJPARepository;
        this.warehouseJPARepository = warehouseJPARepository;
    }

    public Worker addWorker(WorkerDTO workerDTO) {
        return workerRepository.save(new Worker(workerDTO));
    }

    public List<Worker> listAllWorkers() {
        return workerRepository.findAll(Sort.by("id"));
    }

    public Worker getWorkerById(long id) {
        return workerRepository.findById(id).get();
    }

    public List<Worker> listWorkersInGivenPosition(String position) {
        return workerRepository.findByPosition(WorkPosition.valueOf(position.toUpperCase()));
    }

    public Double getWorkersSalaryInMonth(long id, String month) {
        return workerRepository.getSalaryBetweenDates(id, getTimestamp(getStartOfMonthDate(month)), getTimestamp(getEndOfMonthDate(month)));
    }

    public Double getWorkersSalaryBetweenDates(long id, String start, String end) {
        final Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}", Pattern.CASE_INSENSITIVE);
        final Matcher matcher1 = pattern.matcher(start);
        final Matcher matcher2 = pattern.matcher(end);

        if(matcher1.matches() && matcher2.matches()){
            String date_start = start + " 00:00:00";
            String date_end = end + " 23:59:59";

            return workerRepository.getSalaryBetweenDates(id, getTimestamp(date_start), getTimestamp(date_end));
        }
        System.err.println("INVALID DATE FORMAT");
        return null;
    }

    public void addWorkToWorker(long workerId, long warehouseId, double hoursWorked) {
        Workday workday = new Workday();
        workday.setWorker(getWorkerById(workerId));
        workday.setWarehouse(warehouseJPARepository.findById(warehouseId).get());
        workday.setHoursWorked(hoursWorked);
        workday.setDate(getTimestampNow());
        workdayJPARepository.save(workday);
    }

    public List<Workday> listAllWorkdaysForWorker(long workerId) {
        return workerRepository.listWorkdaysForWorker(getWorkerById(workerId));
    }

    public void updateWorkerById(long id, WorkerDTO workerDTO) {
        workerRepository.updateWorker(workerDTO.getName(), workerDTO.getPosition(), workerDTO.getSalary(), id);
    }

    public void deleteWorkerById(long id) {
        workerRepository.deleteById(id);
    }

    private String getNumberOfDaysInMonth(String month) {
        YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), Integer.parseInt(month));
        return String.valueOf(yearMonthObject.lengthOfMonth());
    }

    private Timestamp getTimestamp(String dateTime){
        try{
            return new Timestamp(format.parse(dateTime).getTime());
        } catch (Exception e){
            throw new RuntimeException("Cant convert to timestamp");
        }
    }

    private Timestamp getTimestampNow(){
        try{
            return new Timestamp(format.parse(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                            .toString().replace('T', ' '))
                    .getTime());
        } catch (Exception e){
            throw new RuntimeException("Cant get current time: " + e);
        }
    }

    private String getEndOfMonthDate(String month) {
        return LocalDate.now().getYear() + "-" + month + "-" + getNumberOfDaysInMonth(month)  + " 23:59:59";
    }

    private String getStartOfMonthDate(String month) {
        return LocalDate.now().getYear() + "-" + month + "-01" + " 00:00:00";
    }
}
