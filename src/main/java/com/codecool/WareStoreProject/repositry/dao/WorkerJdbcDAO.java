package com.codecool.WareStoreProject.repositry.dao;

import com.codecool.WareStoreProject.model.Workday;
import com.codecool.WareStoreProject.model.Worker;
import com.codecool.WareStoreProject.model.dto.WorkerDTO;
import com.codecool.WareStoreProject.model.mapper.WorkdayMapper;
import com.codecool.WareStoreProject.model.mapper.WorkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class WorkerJdbcDAO implements WorkerDAO{
    private JdbcTemplate template;
    private WorkerMapper workerMapper;
    private WorkdayMapper workdayMapper;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public WorkerJdbcDAO(JdbcTemplate template, WorkerMapper workerMapper, WorkdayMapper workdayMapper) {
        this.template = template;
        this.workerMapper = workerMapper;
        this.workdayMapper = workdayMapper;
    }

    @Override
    public Worker addWorker(WorkerDTO workerDTO) {
        final String SQL = "INSERT INTO worker(name, position, salary) VALUES(?, ?::work_positions, ?);";

        KeyHolder holder = new GeneratedKeyHolder();

        template.update(getPreparedStatementForAddingWorker(SQL, workerDTO), holder);

        return getWorkerById((int) holder.getKeys().get("id"));
    }

    private PreparedStatementCreator getPreparedStatementForAddingWorker(String sql, WorkerDTO workerDTO) {
        return conn -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, workerDTO.getName());
            statement.setString(2, workerDTO.getPosition().name().toLowerCase());
            statement.setDouble(3, workerDTO.getSalary());

            return statement;
        };
    }

    @Override
    public List<Worker> listAllWorkers() {
        final String SQL = "SELECT id, name, position, salary FROM worker ORDER BY id;";

        return template.query(SQL, workerMapper);
    }

    @Override
    public Worker getWorkerById(int id) {
        final String SQL = "SELECT id, name, position, salary FROM worker WHERE id = ?;";

        return template.queryForObject(SQL, workerMapper, id);
    }

    @Override
    public List<Worker> listWorkersInGivenPosition(String position) {
        final String SQL = "SELECT id, name, position, salary FROM worker WHERE position = ?::work_positions;";

        return template.query(SQL, workerMapper, position);
    }

    @Override
    public Double getWorkersSalaryInMonth(int id, String month) {
        final String SQL = "SELECT SUM(worker_to_workplace.hours_worked * worker.salary) " +
                "FROM worker_to_workplace JOIN worker ON worker.id = worker_to_workplace.worker_id " +
                "WHERE worker_to_workplace.worker_id = ? " +
                "AND worker_to_workplace.date >= ?::timestamp AND worker_to_workplace.date <= ?::timestamp;";


        String startDate = getStartDate(month);

        String endDate = getEndDate(month);

        return template.queryForObject(SQL, Double.class, id, startDate, endDate);
    }
    //TODO make this look prettier
    private String getEndDate(String month) {
        String endDate = month + "-" + getNumberOfDaysInMonth(month) + "-" + LocalDate.now().getYear() + " 23:59:59";
        return endDate;
    }

    private String getStartDate(String month) {
        String startDate = month + "-01-" +  LocalDate.now().getYear() + " 00:00:00";
        return startDate;
    }

    private String getNumberOfDaysInMonth(String month) {
        YearMonth yearMonthObject = YearMonth.of(LocalDate.now().getYear(), Integer.parseInt(month));
        return String.valueOf(yearMonthObject.lengthOfMonth());
    }

    @Override
    public Double getWorkersSalaryBetweenDates(int id, String start, String end) {
        final String SQL = "SELECT SUM(worker_to_workplace.hours_worked * worker.salary) " +
                "FROM worker_to_workplace JOIN worker ON worker.id = worker_to_workplace.worker_id " +
                "WHERE worker_to_workplace.worker_id = ? " +
                "AND worker_to_workplace.date >= ?::timestamp AND worker_to_workplace.date <= ?::timestamp;";

        return template.queryForObject(SQL, Double.class, id, start, end);
    }

    @Override
    public void addWorkToWorker(int workerId, int warehouseId, int hoursWorked) {
        final String SQL = "INSERT INTO worker_to_workplace(worker_id, warehouse_id, date, hours_worked) " +
                "VALUES(?, ?, ?, ?);";

        template.update(SQL, workerId, warehouseId, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), hoursWorked);
    }

    @Override
    public List<Workday> listAllWorkdaysForWorker(int workerId){
        final String SQL = "SELECT id, worker_id, warehouse_id, date, hours_worked FROM worker_to_workplace WHERE worker_id = ?;";

        return template.query(SQL, workdayMapper, workerId);
    }

    @Override
    public void updateWorkerById(int id, WorkerDTO workerDTO) {
        final String SQL = "UPDATE worker SET name = ?, position = ?::work_positions, salary = ? WHERE id = ?;";

        template.update(SQL, workerDTO.getName(), workerDTO.getPosition().name().toLowerCase(), workerDTO.getSalary(), id);
    }

    @Override
    public void deleteWorkerById(int id) {
        final String SQL = "DELETE FROM worker WHERE id = ?;";

        template.update(SQL, id);
    }
}
