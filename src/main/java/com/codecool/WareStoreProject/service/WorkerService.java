package com.codecool.WareStoreProject.service;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.model.enums.WorkPosition;
import com.codecool.WareStoreProject.repositry.jpa.WarehouseJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WorkdayJPARepository;
import com.codecool.WareStoreProject.repositry.jpa.WorkerJPARepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WorkerService {
    private WorkerJPARepository workerRepository;
    private WorkdayJPARepository workdayJPARepository;
    private WarehouseJPARepository warehouseJPARepository;
    private final SimpleDateFormat formatForString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter formatForNow = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final Pattern monthPattern = Pattern.compile("[0-9]{2}", Pattern.CASE_INSENSITIVE);
    private final Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}", Pattern.CASE_INSENSITIVE);
    private final Logger logger = LogManager.getLogger(WorkerService.class);

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
        if (workerRepository.findById(id).isPresent()) {
            return workerRepository.findById(id).get();
        } else {
            logger.error("WORKER NOT FOUND");
            return null;
        }
    }

    public List<Worker> listWorkersInGivenPosition(String position) {
        return workerRepository.findByPosition(WorkPosition.valueOf(position.toUpperCase()));
    }

    public Double getWorkersSalaryInMonth(long id, String month) {
        Matcher matcher = monthPattern.matcher(month);
        if (matcher.matches()) {
            return workerRepository.getSalaryBetweenDates(id, getTimestamp(getStartOfMonthDate(month)),
                    getTimestamp(getEndOfMonthDate(month)));
        } else {
            logger.error("INVALID MONTH FORMAT");
            return null;
        }

    }

    public Double getWorkersSalaryBetweenDates(long id, String start, String end) {
        Matcher matcher1 = datePattern.matcher(start);
        Matcher matcher2 = datePattern.matcher(end);

        if (matcher1.matches() && matcher2.matches()) {
            String date_start = start + " 00:00:00";
            String date_end = end + " 23:59:59";

            return workerRepository.getSalaryBetweenDates(id, getTimestamp(date_start), getTimestamp(date_end));
        }
        System.err.println("INVALID DATE FORMAT");
        return null;
    }

    public void addWorkToWorker(long workerId, long warehouseId, double hoursWorked) {
        if (workerRepository.findById(workerId).isPresent() && warehouseJPARepository.findById(warehouseId).isPresent()) {
            Workday workday = new Workday();
            workday.setWorker(getWorkerById(workerId));
            workday.setWarehouse(warehouseJPARepository.findById(warehouseId).get());
            workday.setHoursWorked(hoursWorked);
            workday.setDate(getTimestampNow());
            workdayJPARepository.save(workday);
        }
    }

    public List<Workday> listAllWorkdaysForWorker(long workerId) {
        if (workerRepository.findById(workerId).isPresent()) {
            return workerRepository.listWorkdaysForWorker(getWorkerById(workerId));
        } else {
            System.err.println("NO WORKER FOUND FOR GIVEN ID");
            return new ArrayList<>();
        }
    }

    public void updateWorkerById(long id, WorkerDTO workerDTO) {
        if (workerRepository.findById(id).isPresent()) {
            workerRepository.updateWorker(workerDTO.getName(), workerDTO.getPosition(), workerDTO.getSalary(), id);
        }
    }

    public void deleteWorkerById(long id) {
        workerRepository.deleteById(id);
    }

    private String getNumberOfDaysInMonth(String month) {
        YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), Integer.parseInt(month));
        return String.valueOf(yearMonthObject.lengthOfMonth());
    }

    private Timestamp getTimestamp(String dateTime) {
        try {
            return new Timestamp(formatForString.parse(dateTime).getTime());
        } catch (Exception e) {
            logger.error("CANNOT CONVERT TO TIMESTAMP " + e.getMessage());
            throw new RuntimeException("Cant convert to timestamp: " + e.getMessage());
        }
    }

    private Timestamp getTimestampNow() {
        try {
            return Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(formatForNow)
                    .replace('T', ' '));
        } catch (Exception e) {
            throw new RuntimeException("Cant get current time: " + e.getMessage());
        }
    }

    private String getEndOfMonthDate(String month) {
        return LocalDate.now().getYear() + "-" + month + "-" + getNumberOfDaysInMonth(month) + " 23:59:59";
    }

    private String getStartOfMonthDate(String month) {
        return LocalDate.now().getYear() + "-" + month + "-01" + " 00:00:00";
    }
}
