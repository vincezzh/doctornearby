package com.akhaltech.dao.impl;

import com.akhaltech.model.Medicine;
import com.akhaltech.util.QueryUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by vince on 2016-03-03.
 */
public class UserDAOSchedulerImpl {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteMedicine(Medicine medicine) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        final String sql = QueryUtil.getQuery("user", "deleteMedicine");
        jdbcTemplate.update(sql, new Object[]{Long.parseLong(medicine.get_id().get$oid())});
    }

    public List<Medicine> getMedicines() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        final String sql = QueryUtil.getQuery("user", "getAllMedicines");
        List<Medicine> medicineList = jdbcTemplate.query(sql, new MedicineRowMapper());

        return medicineList;
    }
}
